package com.qhucy.configmanager;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.logging.Logger;

/**
 * Description.
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public final class ConfigManagerSpigot
{

    private final FileConfiguration configuration;

    public ConfigManagerSpigot( @NotNull final File configFile, @Nullable final File defaultConfigFile,
                                @NotNull final Logger logger )
    {
        this.configuration = YamlConfiguration.loadConfiguration( configFile );

        if ( defaultConfigFile != null )
        {

        }
    }

}