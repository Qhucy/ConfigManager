package com.qhucy.configmanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.qhucy.configmanager.file.ConfigSource;
import com.qhucy.configmanager.value.ConfigValue;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.Nullable;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.tomlj.Toml;
import org.tomlj.TomlParseResult;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that parses and loads configuration (config) values into objects in a config field and
 * value map. When trying to access missing fields, it will be logged to console.
 *
 * @see ConfigValue
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@Setter
@Getter
public class ConfigManager
{

    // The config field and value map.
    @NonNull
    private Map< String, ConfigValue > values   = new HashMap<>();
    // The config field and comment lsit map.
    @NonNull
    private Map< String, String[] >    comments = new HashMap<>();

    // The source of the config field and value map.
    @NonNull
    private ConfigSource configSource;
    // The accessing plugin's logger that is used to log missing, invalid, and unknown config
    // values.
    @NonNull
    private Logger       logger;

    // Whether there are missing values in the loaded config field and value map.
    private boolean missingValues = false;

    /**
     * Instantiates a ConfigManager from a config field and value map.
     *
     * @param values       The config field and value map.
     * @param configSource The source of the config field and value map.
     * @param logger       The accessing plugin's logger that is used to log missing and invalid
     *                     config values.
     */
    public ConfigManager( @Nullable final HashMap< String, ConfigValue > values,
                          @NonNull final ConfigSource configSource, @NonNull final Logger logger )
    {
        setValues( ( values == null ) ? new HashMap<>() : values );

        setConfigSource( configSource );
        setLogger( logger );
    }

    /**
     * Instantiates a ConfigManager from a config field and value map and a config default field and
     * value map.
     *
     * @param values        The config field and value map.
     * @param defaultValues The default config field and value map.
     * @param configSource  The source of the config field and value map.
     * @param logger        The accessing plugin's logger that is used to log missing and invalid
     *                      config values.
     */
    public ConfigManager( @Nullable final HashMap< String, Object > values,
                          @Nullable final HashMap< String, Object > defaultValues,
                          @NonNull final ConfigSource configSource, @NonNull final Logger logger )
    {
        transferValues( values );
        transferDefaultValues( defaultValues );

        setConfigSource( configSource );
        setLogger( logger );
    }

    /**
     * Instantiates a ConfigManager from a list of fields, values, and default values.
     *
     * @param fields        The list of fields for the config field and value map.
     * @param values        The list of values for the config field and value map.
     * @param defaultValues The list of default values for the config field and value map.
     * @param configSource  The source of the config field and value map.
     * @param logger        The accessing plugin's logger that is used to log missing and invalid
     *                      config values.
     */
    public ConfigManager( @NonNull final List< String > fields,
                          @Nullable final List< Object > values,
                          @Nullable final List< Object > defaultValues,
                          @NonNull final ConfigSource configSource, @NonNull final Logger logger )
    {
        for ( int i = 0; i < fields.size(); ++i )
        {
            final Object value =
                    ( values == null ) ? null : ( ( values.size() > i ) ? values.get( i ) : null );
            final Object defaultValue = ( defaultValues == null ) ? null
                                                                  : ( ( defaultValues.size() > i )
                                                                      ? defaultValues.get( i )
                                                                      : null );

            setConfigValue( fields.get( i ), new ConfigValue( value, defaultValue ) );
        }

        setConfigSource( configSource );
        setLogger( logger );
    }

    /**
     * Instantiates a ConfigManager from a list of fields and values.
     *
     * @param fields       The list of fields for the config field and value map.
     * @param values       The list of values for the config field and value map.
     * @param configSource The source of the config field and value map.
     * @param logger       The accessing plugin's logger that is used to log missing and invalid
     *                     config values.
     */
    public ConfigManager( @NonNull final List< String > fields,
                          @Nullable final List< Object > values,
                          @NonNull final ConfigSource configSource, @NonNull final Logger logger )
    {
        for ( int i = 0; i < fields.size(); ++i )
        {
            final Object value =
                    ( values == null ) ? null : ( ( values.size() > i ) ? values.get( i ) : null );

            setConfigValue( fields.get( i ), new ConfigValue( value, null ) );
        }

        setConfigSource( configSource );
        setLogger( logger );
    }

    /**
     * Instantiates a ConfigManager from an inputted array of config fields, values, and default
     * values.
     *
     * @param configSource           The source of the config field and value map.
     * @param logger                 The accessing plugin's logger that is used to log missing and
     *                               invalid config values.
     * @param fieldValueDefaultValue Alternating String fields and its respective value and default
     *                               value to build a new config field and value map.
     */
    public ConfigManager( @NonNull final ConfigSource configSource, @NonNull final Logger logger,
                          @Nullable final Object... fieldValueDefaultValue )
    {
        setConfigSource( configSource );
        setLogger( logger );

        setValues( ConfigBuilder.buildFromObjects( fieldValueDefaultValue ) );
    }

    /**
     * Instantiates a ConfigManager from a stored config value.
     *
     * @param configFile The config file.
     * @param logger     The accessing plugin's logger that is used to log missing and invalid
     *                   config values.
     *
     * @throws IOException    If unable to load config values from the config file.
     * @throws ParseException If unable to load config values from the config file.
     */
    public ConfigManager( @NonNull final File configFile, @NonNull final Logger logger )
            throws IOException, ParseException
    {
        final ConfigManager configManager = ConfigManager.loadFromFile( configFile, logger );

        setConfigSource( configManager.getConfigSource() );
        setLogger( configManager.getLogger() );

        setValues( configManager.getValues() );
    }

    /**
     * Returns the list of nested fields under a given field in the config key and value map.
     *
     * @param parentField The parent field in the config key and value map.
     * @param fullFields  If the field contains their full location or just their relative nested
     *                    location with respect to the parent field.
     * @param deepFields  If fields include all nested fields under child fields or just the child
     *                    fields of the parent.
     *
     * @return The list of nested fields under a given field in the config key and value map.
     */
    public final List< String > getConfigSectionFields( @NonNull final String parentField,
                                                        final boolean fullFields,
                                                        final boolean deepFields )
    {
        Validate.notNull( parentField, "Parameter parentField cannot be null." );

        final ArrayList< String > fields = new ArrayList<>();

        for ( final String field : getValues().keySet() )
        {
            if ( field.startsWith( parentField ) && !field.equalsIgnoreCase( parentField ) )
            {
                final int parentNestedDepth = parentField.split( "\\." ).length;
                final int fieldNestedDepth  = field.split( "\\." ).length;

                if ( !deepFields && ( fieldNestedDepth - parentNestedDepth ) > 1 )
                {
                    continue;
                }

                fields.add( ( fullFields ) ? field : field.substring( parentField.length() + 1 ) );
            }
        }

        return fields;
    }

    /**
     * Returns the ConfigValue at the given field in the config field and value map.
     *
     * @param field The field for the ConfigValue.
     *
     * @return The ConfigValue at the given field in the config field and value map.
     *
     * @see ConfigValue
     */
    @Nullable
    public final ConfigValue getConfigValue( @NonNull final String field )
    {
        if ( getValues().containsKey( field ) )
        {
            return getValues().get( field );
        }
        else
        {
            logMissingValue( field );

            return null;
        }
    }

    /**
     * Sets a given field in the config field and value map to a given value.
     *
     * @param field       The field to set in the config field and value map.
     * @param configValue The value to attach to the field in the config field and value map.
     */
    public final void setConfigValue( @NonNull final String field,
                                      @Nullable final ConfigValue configValue )
    {
        getValues().put( field, Objects.requireNonNullElseGet( configValue,
                                                               () -> new ConfigValue( null,
                                                                                      null ) ) );
    }

    /**
     * Sets the config field and value map.
     *
     * @param values The config field and value map.
     */
    public final void setValues( @Nullable final Map< String, ConfigValue > values )
    {
        if ( values == null )
        {
            this.values.clear();
        }
        else
        {
            this.values = values;
        }
    }

    /**
     * Returns the value at a given field in the config field and value map. Logs if values are
     * missing.
     *
     * @param field The field in the config field and value map.
     *
     * @return The value at a given field in the config field and value map.
     */
    @Nullable
    public final Object getValue( @NonNull final String field )
    {
        if ( getValues().containsKey( field ) )
        {
            final ConfigValue configValue = getValues().get( field );

            if ( configValue.hasValue() )
            {
                return configValue.getValue();
            }
            else if ( configValue.hasDefaultValue() )
            {
                logMissingValueWithReplacement( field,
                                                String.valueOf( configValue.getDefaultValue() ) );

                return configValue.getDefaultValue();
            }
        }

        logMissingValue( field );

        return null;
    }

    /**
     * Sets a specific value for a field in the config field and value map. The default value is
     * missing by default.
     *
     * @param field  The field in the config field and value map.
     * @param object The new value for the field in the config field and value map.
     */
    public final void setValue( @NonNull final String field, @Nullable final Object object )
    {
        if ( getValues().containsKey( field ) )
        {
            getValues().get( field ).setValue( object );
        }
        else
        {
            getValues().put( field, new ConfigValue( object, null ) );
        }
    }

    /**
     * Transfers mappings from a given config field and value map to the current one. If inputting
     * null, all values will become null.
     *
     * @param values The config field and value map to transfer.
     */
    public final void transferValues( @Nullable final HashMap< String, Object > values )
    {
        if ( values == null )
        {
            for ( final ConfigValue configValue : getValues().values() )
            {
                configValue.setValue( null );
            }
        }
        else
        {
            for ( final Map.Entry< String, Object > entry : values.entrySet() )
            {
                if ( getValues().containsKey( entry.getKey() ) )
                {
                    getValues().get( entry.getKey() ).setValue( entry.getValue() );
                }
                else
                {
                    getValues().put( entry.getKey(), new ConfigValue( entry.getValue(), null ) );
                }
            }
        }
    }

    /**
     * Returns the default value at a given field in the config field and value map.
     *
     * @param field The field in the config field and value map.
     *
     * @return The default value at a given field in the config field and value map.
     */
    @Nullable
    public final Object getDefaultValue( @NonNull final String field )
    {
        if ( getValues().containsKey( field ) )
        {
            final ConfigValue configValue = getValues().get( field );

            if ( configValue.hasDefaultValue() )
            {
                return configValue.getDefaultValue();
            }
        }

        return null;
    }

    /**
     * Sets a specific default value for a field in the config field and value map. The normal value
     * is missing by default.
     *
     * @param field  The field in the config field and value map.
     * @param object The new default value for the field in the config field and value map.
     */
    public final void setDefaultValue( @NonNull final String field, @Nullable final Object object )
    {
        if ( getValues().containsKey( field ) )
        {
            getValues().get( field ).setDefaultValue( object );
        }
        else
        {
            getValues().put( field, new ConfigValue( null, object ) );
        }
    }

    /**
     * Transfers mapping from a given config field and default value map to the current one. If
     * inputting null, all default values will become null.
     *
     * @param defaultValues The config field and value map to transfer for default values.
     */
    public final void transferDefaultValues( @Nullable final HashMap< String, Object > defaultValues )
    {
        if ( defaultValues == null )
        {
            for ( final ConfigValue configValue : getValues().values() )
            {
                configValue.setDefaultValue( null );
            }
        }
        else
        {
            for ( final Map.Entry< String, Object > entry : defaultValues.entrySet() )
            {
                if ( getValues().containsKey( entry.getKey() ) )
                {
                    getValues().get( entry.getKey() ).setDefaultValue( entry.getValue() );
                }
                else
                {
                    getValues().put( entry.getKey(), new ConfigValue( null, entry.getValue() ) );
                }
            }
        }
    }

    /**
     * Logs a list of messages separately with a given log level.
     *
     * @param level    The log level of the log: INFO, WARNING, SEVERE.
     * @param messages The list of messages to log separately.
     */
    public void logMessage( @NonNull final Level level, @NonNull final String... messages )
    {
        if ( messages.length > 0 )
        {
            for ( final String message : messages )
            {
                getLogger().log( level, message );
            }
        }
        else
        {
            throw new IllegalArgumentException( "Parameter messages cannot be empty" );
        }
    }

    /**
     * Logs to the console that a field in the config field and value map is missing. As a result,
     * it was replaced with a value from the default config field and value map.
     *
     * @param field         The missing field in the config field and value map.
     * @param replacement   The replacement value from the default config field and value map.
     * @param extraMessages Extra messages to separately log afterwards.
     */
    public void logMissingValueWithReplacement( @NonNull final String field,
                                                @NonNull final String replacement,
                                                @NonNull final String... extraMessages )
    {
        logMessage( Level.WARNING, String.format( "Field '%s' does not exist in the config field " +
                                                          "and value map " + "from " + "'%s'. " + "Using replacement value %s " + "from " + "the " + "default config " + "field" + " " + "and value map" + ".", field, getConfigSource(), replacement ) );

        if ( extraMessages.length > 0 )
        {
            logMessage( Level.WARNING, extraMessages );
        }

        setMissingValues( true );
    }

    /**
     * Logs to the console that a field is missing a value and default value from the config field
     * and value map.
     *
     * @param field         The missing field in the config field and value map.
     * @param extraMessages Extra messages to separately log afterwards.
     */
    public void logMissingValue( @NonNull final String field,
                                 @NonNull final String... extraMessages )
    {
        logMessage( Level.SEVERE, String.format( "Field '%s' does not exist in the config field " +
                                                         "and value map " + "from " + "'%s'. " +
                                                         "No replacements were found " + "and " + "the plugin " + "now emits " + "undefined behavior which is" + " very " + "dangerous!", field, getConfigSource() ) );

        if ( extraMessages.length > 0 )
        {
            logMessage( Level.SEVERE, extraMessages );
        }

        setMissingValues( true );
    }

    /**
     * Saves the ConfigManager data to a config file.
     *
     * @param configFile The config file that will be written to.
     */
    public final void saveToFile( @NonNull final File configFile )
            throws IOException
    {
        final FileWriter fileWriter = new FileWriter( configFile );

        // Load all values into a map.
        final Map< String, Object > saveValues = new HashMap<>();

        for ( Map.Entry< String, ConfigValue > entry : getValues().entrySet() )
        {
            saveValues.put( entry.getKey(), entry.getValue().getValue() );
        }

        // Saving hashmap data into the file.
        if ( configFile.getPath().endsWith( ".yml" ) || configFile.getPath().endsWith( ".yaml" ) )
        {

        }
        else if ( configFile.getPath().endsWith( ".toml" ) )
        {

        }
        else if ( configFile.getPath().endsWith( ".json" ) )
        {
            final Gson   gson       = new GsonBuilder().create();
            final String jsonString = gson.toJson( saveValues );

            fileWriter.write( jsonString );
        }
        else
        {
            fileWriter.close();

            throw new IllegalArgumentException( "Unable to load configFile at '" + configFile.getAbsolutePath() + "', this API only supports .yml .yaml .toml and .json files." );
        }

        // Writing comments to the file.


        fileWriter.close();
    }

    /**
     * Loads a ConfigManager from an existing file.
     *
     * @param configFile The config source file.
     * @param logger     The logger for the plugin.
     *
     * @return A ConfigManager from an existing file.
     *
     * @throws FileNotFoundException If the given file does not exist.
     */
    public static ConfigManager loadFromFile( @NonNull final File configFile,
                                              @NonNull final Logger logger )
            throws IOException, ParseException
    {
        final ConfigManager configManager =
                new ConfigManager( null, new ConfigSource( configFile ), logger );

        // Reset all values in the config field and value map.
        configManager.getValues().clear();

        // Load all config values from the file.
        if ( configFile.getPath().endsWith( ".yml" ) || configFile.getPath().endsWith( ".yaml" ) )
        {
            final InputStream           inputStream = new FileInputStream( configFile );
            final Yaml                  yaml        = new Yaml();
            final Map< String, Object > valueMap    = yaml.load( inputStream );

            for ( final String key : valueMap.keySet() )
            {
                configManager.setValue( key, valueMap.get( key ) );
            }
        }
        else if ( configFile.getPath().endsWith( ".toml" ) )
        {
            final TomlParseResult tomlParseResult = Toml.parse( configFile.getPath() );

            configManager.setMissingValues( tomlParseResult.hasErrors() );

            for ( final String key : tomlParseResult.dottedKeySet( true ) )
            {
                configManager.setValue( key, tomlParseResult.get( key ) );
            }
        }
        else if ( configFile.getPath().endsWith( ".json" ) )
        {
            final JSONParser jsonParser = new JSONParser();
            final JsonObject jsonObject =
                    ( JsonObject ) jsonParser.parse( new FileReader( configFile ) );

            for ( final Map.Entry< String, JsonElement > entry : jsonObject.entrySet() )
            {
                configManager.setValue( entry.getKey(), entry.getValue() );
            }
        }
        else
        {
            throw new IllegalArgumentException( "Unable to load configFile at '" + configFile.getAbsolutePath() + "', this API only supports .yml .yaml .toml and .json files." );
        }

        // Load all comments from the file.
        final FileReader     fileReader     = new FileReader( configFile );
        final BufferedReader bufferedReader = new BufferedReader( fileReader );

        final Map< String, String[] > comments = new HashMap<>();
        String                        line;

        final ArrayList< String > savedComments = new ArrayList<>();
        String                    savedValue    = "";

        while ( ( line = bufferedReader.readLine() ) != null )
        {
            if ( line.isEmpty() || line.startsWith( "#" ) )
            {
                if ( savedComments.isEmpty() && !line.startsWith( " " ) )
                {
                    savedValue = "";
                }

                savedComments.add( line.strip() );
            }
            else if ( line.contains( ":" ) )
            {
                line = line.substring( line.indexOf( ":" ) );

                savedValue += "." + line;

                if ( savedComments.size() > 0 )
                {
                    comments.put( savedValue, savedComments.toArray( new String[ 0 ] ) );

                    savedComments.clear();
                }
            }
        }

        configManager.setComments( comments );

        bufferedReader.close();
        fileReader.close();

        configManager.setConfigSource( new ConfigSource( configFile ) );
        configManager.setLogger( logger );

        return configManager;
    }

}