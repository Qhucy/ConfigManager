package com.qhucy.configmanager.file;

import com.qhucy.configmanager.ConfigManager;
import com.qhucy.configmanager.ConfigManagerBungee;
import com.qhucy.configmanager.ConfigManagerSpigot;
import lombok.NonNull;

/**
 * Interface that allows objects to save and load their contents to and from a config file respectively.
 *
 * @see ConfigManager
 * @see ConfigFileManager
 * @see ConfigManagerSpigot
 * @see ConfigManagerBungee
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
public interface ConfigStorable
{

    void saveToConfig( @NonNull final ConfigSaveEmphasis configSaveEmphasis );

    void saveToConfig();

    void loadFromConfig( @NonNull final ConfigSaveEmphasis configSaveEmphasis );

    void loadFromConfig();

}