package com.qhucy.configmanager.source;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

/**
 * Object that stores information about where a config field and value map was loaded from.
 *
 * @see com.qhucy.configmanager.ConfigManager
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public class ConfigSource
{

    /**
     * Returns a new ConfigSource object from a given file.
     *
     * @param source The text path to create the ConfigSource object from.
     * @return A new ConfigSource object from a given file.
     */
    @NotNull
    public static ConfigSource fromString( @NotNull final String source )
    {
        Validate.notNull( source, "Parameter source cannot be null." );

        return new ConfigSource( source );
    }

    // The text path that directs you to the source of where the config field and value map was loaded from.
    private String source;

    /**
     * Instantiates a ConfigSource with a given text source.
     *
     * @param source The text path that directs you to the source of the config field and value map.
     */
    public ConfigSource( @NotNull final String source )
    {
        Validate.notNull( source, "Parameter source cannot be null." );

        setSource( source );
    }

    /**
     * Returns the text path of the source of the config field and value map.
     *
     * @return The text path of the source of the config field and value map.
     */
    @NotNull
    public String getSource()
    {
        return source;
    }

    /**
     * Sets the text path of the source of the config field and value map.
     *
     * @param source The text path of the source of the config field and value map.
     */
    public void setSource( @NotNull final String source )
    {
        Validate.notNull( source, "Parameter source cannot be null." );

        this.source = source;
    }


}