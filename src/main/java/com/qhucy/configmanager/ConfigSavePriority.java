package com.qhucy.configmanager;

/**
 * Enumeration constants representing how a config field and value map should be saved to a file.
 *
 * @see ConfigManager
 * @see ConfigFileManager
 * @see ConfigManagerSpigot
 * @see ConfigManagerBungee
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public enum ConfigSavePriority
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