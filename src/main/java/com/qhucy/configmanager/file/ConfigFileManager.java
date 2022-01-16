package com.qhucy.configmanager.file;

import com.qhucy.configmanager.ConfigManager;
import lombok.NonNull;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Description.
 *
 * @see ConfigManager
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
public class ConfigFileManager
        extends ConfigManager
{

    // The list of supported config file extensions.
    private final static List< String > VALID_EXTENSIONS = Arrays.asList( "yml", "yaml", "toml", "json" );

    /**
     * Returns true if the given file is a valid configuration file.
     *
     * @param configFile The config file.
     * @return True if the given file is a valid configuration file.
     */
    public static boolean isValidConfigFile( @NonNull final File configFile )
    {
        if ( !configFile.exists() || !configFile.isFile() )
        {
            return false;
        }

        for ( final String extension : VALID_EXTENSIONS )
        {
            if ( configFile.getPath().endsWith( "." + extension ) )
            {
                return true;
            }
        }

        return false;
    }

    // The file that the config field and value map is loaded from.
    private File configFile;

    public ConfigFileManager( @NotNull final File configFile, @NotNull final Logger logger,
                              @Nullable final Object... fieldValueDefaultValue )
    {
        super( ConfigSource.fromFile( configFile ), logger, fieldValueDefaultValue );

        setConfigFile( configFile );
    }

    /**
     * Returns the configFile class attribute.
     *
     * @return The configFile class attribute.
     */
    public final File getConfigFile()
    {
        return configFile;
    }

    /**
     * Sets the configFile class attribute to another value.
     *
     * @param configFile The new configFile class attribute.
     */
    private void setConfigFile( @NotNull final File configFile )
    {
        Validate.notNull( configFile, "Parameter configFile cannot be null." );

        this.configFile = configFile;
    }

    public void saveToFilee( @NotNull final File configFile )
    {

    }

    public void saveToFile()
    {
        saveToFile( getConfigFile() );
    }

    public void loadFromFile( @NotNull final File configFile )
    {

    }

    public void loadFromFile()
    {
        loadFromFile( getConfigFile() );
    }

}