package com.qhucy.configmanager.file;

import com.qhucy.configmanager.ConfigManager;

/**
 * Enumeration constants representing how a config field and value map should be saved to a file.
 *
 * @see ConfigManager
 * @see ConfigFileManager
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
public enum StorageType
{

    /**
     * The config field and value map will be saved using the least amount of memory possible.
     */
    MEMORY,

    /**
     * The config field and value map will be saved in the most easily readable way by humans.
     */
    READABILITY

}