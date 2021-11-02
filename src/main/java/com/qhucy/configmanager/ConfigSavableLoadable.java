package com.qhucy.configmanager;

import com.qhucy.configmanager.config.file.ConfigFileManager;
import com.qhucy.configmanager.config.ConfigManagerBungee;
import com.qhucy.configmanager.config.ConfigManagerSpigot;
import org.jetbrains.annotations.NotNull;

/**
 * Interface that allows objects to save and load their contents to and from a config file
 * respectively.
 *
 * @see ConfigManager
 * @see ConfigFileManager
 * @see ConfigManagerSpigot
 * @see ConfigManagerBungee
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public interface ConfigSavableLoadable
{

    void saveAsConfigObjectToFile( @NotNull final ConfigSavePriority configSavePriority );

    void loadAsConfigObjectFromFile( @NotNull final ConfigSavePriority configSavePriority );

}