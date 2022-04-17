package com.qhucy.configmanager.file;

import com.qhucy.configmanager.ConfigManager;
import lombok.NonNull;

/**
 * Interface that allows objects to save and load their contents to and from a config file
 * respectively.
 *
 * @see ConfigManager
 * @see ConfigFileManager
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
public interface ConfigStorable
{

    void saveToConfig( @NonNull final StorageType storageType );

    void saveToConfig();

    void loadFromConfig( @NonNull final StorageType storageType );

    void loadFromConfig();

}