package com.qhucy.configmanager;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that parses and loads configuration (config) values into objects in a config field and
 * value map. When trying to access missing fields, it will be logged to console.
 *
 * @see ConfigValue
 *
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public class ConfigManager
{

    // The config field and value map.
    private Map< String, ConfigValue > values = new HashMap<>();

    // The text path that leads to the source of the config field and value map (usually a file
    // path).
    private String configSource;
    // The accessing plugin's logger that is used to log missing, invalid, and unknown config
    // values.
    private Logger logger;

    // Whether or not there are missing values in the loaded config field and value map.
    private boolean missingValues = false;

    /**
     * Instantiates a ConfigManager from a config field and value map.
     *
     * @param values       The config field and value map.
     * @param configSource The text path that leads to the source of the config field and value map
     *                     (usually a file path).
     * @param logger       The accessing plugin's logger that is used to log missing and invalid
     *                     config values.
     */
    public ConfigManager( @Nullable final HashMap< String, ConfigValue > values,
                          @NotNull final String configSource, @NotNull final Logger logger )
    {
        setConfigValues( values );

        setConfigSource( configSource );
        setLogger( logger );
    }

    /**
     * Instantiates a ConfigManager from a config field and value map and a config default field and
     * value map.
     *
     * @param values        The config field and value map.
     * @param defaultValues The default config field and value map.
     * @param configSource  The text path that leads to the source of the config field and value map
     *                      (usually a file path).
     * @param logger        The accessing plugin's logger that is used to log missing and invalid
     *                      config values.
     */
    public ConfigManager( @Nullable final HashMap< String, Object > values,
                          @Nullable final HashMap< String, Object > defaultValues,
                          @NotNull final String configSource, @NotNull final Logger logger )
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
     * @param configSource  The text path that leads to the source of the config field and value map
     *                      (usually a file path).
     * @param logger        The accessing plugin's logger that is used to log missing and invalid
     *                      config values.
     */
    public ConfigManager( @NotNull final List< String > fields,
                          @Nullable final List< Object > values,
                          @Nullable final List< Object > defaultValues,
                          @NotNull final String configSource, @NotNull final Logger logger )
    {
        Validate.notNull( fields, "Parameter fields cannot be null." );

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
     * @param configSource The text path that leads to the source of the config field and value map
     *                     (usually a file path).
     * @param logger       The accessing plugin's logger that is used to log missing and invalid
     *                     config values.
     */
    public ConfigManager( @NotNull final List< String > fields,
                          @Nullable final List< Object > values,
                          @NotNull final String configSource, @NotNull final Logger logger )
    {
        Validate.notNull( fields, "Parameter fields cannot be null." );

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
     * @param configSource           The text path that leads to the source of the config field and
     *                               value map (usually a file path).
     * @param logger                 The accessing plugin's logger that is used to log missing and
     *                               invalid config values.
     * @param fieldValueDefaultValue Alternating String fields and its respective value and default
     *                               value to build a new config field and value map.
     */
    public ConfigManager( @NotNull final String configSource, @NotNull final Logger logger,
                          @Nullable final Object... fieldValueDefaultValue )
    {
        setConfigSource( configSource );
        setLogger( logger );

        setConfigValues( ConfigBuilder.buildConfigValueMapFromObjects( fieldValueDefaultValue ) );
    }

    /**
     * Returns the values class attribute.
     *
     * @return The values class attribute.
     */
    private Map< String, ConfigValue > getValues()
    {
        return values;
    }

    /**
     * Sets the values class attribute to another value.
     *
     * @param values The new values class attribute value.
     */
    public final void setConfigValues( @Nullable final HashMap< String, ConfigValue > values )
    {
        this.values = ( values == null ) ? new HashMap<>() : values;
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
    public final List< String > getConfigSectionFields( @NotNull final String parentField,
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
    public final ConfigValue getConfigValue( @NotNull final String field )
    {
        Validate.notNull( field, "Parameter field cannot be null." );

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
    public final void setConfigValue( @NotNull final String field,
                                      @Nullable final ConfigValue configValue )
    {
        Validate.notNull( field, "Parameter field cannot be null." );

        getValues().put( field, Objects
                .requireNonNullElseGet( configValue, () -> new ConfigValue( null, null ) ) );
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
    public final Object getValue( @NotNull final String field )
    {
        Validate.notNull( field, "Parameter field cannot be null." );

        if ( getValues().containsKey( field ) )
        {
            final ConfigValue configValue = getValues().get( field );

            if ( configValue.hasValue() )
            {
                return configValue.getValue();
            }
            else if ( configValue.hasDefaultValue() )
            {
                logMissingValueWithReplacement( field, String
                        .valueOf( configValue.getDefaultValue() ) );

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
    public final void setValue( @NotNull final String field, @Nullable final Object object )
    {
        Validate.notNull( field, "Parameter field cannot be null." );

        if ( getValues().containsKey( field ) )
        {
            getValues().get( field ).setValue( object );
        }
        else
        {
            getValues().put( field, new ConfigValue( object, ErrorValue.MISSING ) );
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
                    getValues()
                            .put( entry.getKey(), new ConfigValue( entry.getValue(),
                                                                   ErrorValue.MISSING ) );
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
    public final Object getDefaultValue( @NotNull final String field )
    {
        Validate.notNull( field, "Parameter field cannot be null." );

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
    public final void setDefaultValue( @NotNull final String field, @Nullable final Object object )
    {
        Validate.notNull( field, "Parameter field cannot be null." );

        if ( getValues().containsKey( field ) )
        {
            getValues().get( field ).setDefaultValue( object );
        }
        else
        {
            getValues().put( field, new ConfigValue( ErrorValue.MISSING, object ) );
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
                    getValues().put( entry.getKey(), new ConfigValue( ErrorValue.MISSING, entry
                            .getValue() ) );
                }
            }
        }
    }

    /**
     * Returns the configSource class attribute.
     *
     * @return the configSource class attribute.
     */
    public final String getConfigSource()
    {
        return configSource;
    }

    /**
     * Sets the configSource class attribute to another value.
     *
     * @param configSource The new configSource class attribute.
     */
    public final void setConfigSource( @NotNull final String configSource )
    {
        Validate.notNull( configSource, "Parameter configSource cannot be null." );

        this.configSource = configSource;
    }

    /**
     * Returns the logger class attribute.
     *
     * @return The logger class attribute.
     */
    public final Logger getLogger()
    {
        return logger;
    }

    /**
     * Sets the logger class attribute to another value.
     *
     * @param logger The new logger class attribute.
     */
    public final void setLogger( @NotNull final Logger logger )
    {
        Validate.notNull( logger, "Parameter logger cannot be null." );

        this.logger = logger;
    }

    /**
     * Returns the missingValues class attribute.
     *
     * @return the missingValues class attribute.
     */
    public final boolean isMissingValues()
    {
        return missingValues;
    }

    /**
     * Sets the missingValues class attribute to another value.
     *
     * @param missingValues The new missingValues class attribute.
     */
    public final void setMissingValues( final boolean missingValues )
    {
        this.missingValues = missingValues;
    }

    /**
     * Logs a list of messages separately with a given log level.
     *
     * @param level    The log level of the log: INFO, WARNING, SEVERE.
     * @param messages The list of messages to log separately.
     */
    public void logMessage( @NotNull final Level level, @NotNull final String... messages )
    {
        Validate.notNull( level, "Parameter level cannot be null." );
        Validate.notNull( messages, "Parameter messages cannot be null." );

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
    public void logMissingValueWithReplacement( @NotNull final String field,
                                                @NotNull final String replacement,
                                                @NotNull final String... extraMessages )
    {
        Validate.notNull( field, "Parameter field cannot be null." );
        Validate.notNull( replacement, "Parameter replacement cannot be null." );
        Validate.notNull( extraMessages, "Parameter extraMessages cannot be null." );

        logMessage( Level.WARNING, String
                .format( "Field '%s' does not exist in the config field and value map " + "from " + "'%s'. " + "Using replacement value %s " + "from " + "the " + "default config " + "field" + " " + "and value map" + ".", field, getConfigSource(), replacement ) );

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
    public void logMissingValue( @NotNull final String field,
                                 @NotNull final String... extraMessages )
    {
        Validate.notNull( field, "Parameter field cannot be null." );
        Validate.notNull( extraMessages, "Parameter extraMessages cannot be null." );

        logMessage( Level.SEVERE, String
                .format( "Field '%s' does not exist in the config field and value map " + "from " + "'%s'. " + "No replacements were found " + "and " + "the plugin " + "now emits " + "undefined behavior which is" + " very " + "dangerous!", field, getConfigSource() ) );

        if ( extraMessages.length > 0 )
        {
            logMessage( Level.SEVERE, extraMessages );
        }

        setMissingValues( true );
    }

    public final void saveToFile( @NotNull final File configFile )
    {

    }

}