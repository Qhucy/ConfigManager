package com.qhucy.configmanager.file;

import com.qhucy.configmanager.ConfigManager;
import com.qhucy.configmanager.source.FileSource;
import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.logging.Logger;

/**
 * Description.
 *
 * @see ConfigManager
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public class ConfigFileManager
        extends ConfigManager
{

    /**
     * Asserts that the given file is a valid configuration file.
     *
     * @param configFile The config file.
     * @throws InvalidPathException If the given config file is invalid.
     */
    public static void assertValidConfigFile( @NotNull final File configFile ) throws
                                                                               InvalidPathException
    {
        Validate.notNull( configFile, "Parameter configFile cannot be null." );

        final String path = configFile.getPath();

        if ( !configFile.exists() )
        {
            throw new InvalidPathException( path, "Path to the config file points to nothing." );
        }
        else if ( !configFile.isFile() )
        {
            throw new InvalidPathException( path, "Path to the config file does not point to a " + "file." );
        }
        else if ( !path.endsWith( ".yml" ) && !path.endsWith( ".toml" ) )
        {
            throw new InvalidPathException( path, "Path does not point to a valid configuration " + "file." );
        }
    }

    // The file that the config field and value map is loaded from.
    private File configFile;

    public ConfigFileManager( @NotNull final File configFile, @NotNull final Logger logger,
                              @Nullable final Object... fieldValueDefaultValue )
    {
        super( FileSource.fromFile( configFile ), logger, fieldValueDefaultValue );

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