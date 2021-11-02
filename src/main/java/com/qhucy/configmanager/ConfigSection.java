package com.qhucy.configmanager;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description.
 *
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public class ConfigSection
{

    /**
     * ConfigFileManager needs not to be abstract because we can save and load from FILES WITHOUT
     * NEEDING IMPLEMENTATION FROM BUNGEE AND SPIGOT
     *
     * ConfigManagerSpigot + ConfigManagerBungee still need to exist for those that want to load
     * from these implementations still
     *
     *
     * saving and loading from files in a ConfigFileManager preserves the comments while this is
     * not the case for a regular ConfigManager
     *
     *
     * ConfigManager needs to have a config section instead of using hashmaps. config sections
     * contain all the fields, values, and default values for a given place
     * in config.
     *
     *
     * ConfigSection has save/load function
     */

    // The config field and value map for this config section.
    private Map< String, ConfigValue >   fieldValueMap    = new LinkedHashMap<>();
    // The nested section map for config sections under specific fields.
    private Map< String, ConfigSection > nestedSectionMap = new LinkedHashMap<>();

    public ConfigSection()
    {

    }

    // contains key

    // get path


    /**
     * config:
     *   section: 1
     *   barkon: 2
     *   cylink: 3
     *
     * ConfigManager stores the entire map of fields and config sections
     * but each config section can also go to nested config sections and the parent config section.
     * each config section can also get a value
     */

}