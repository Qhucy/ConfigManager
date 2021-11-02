package com.qhucy.configmanager.config;

import com.qhucy.configmanager.value.ConfigValue;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Description.
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public final class ConfigBuilder
{

    /**
     * Returns a config field and value map from an input of alternating String fields and
     * ConfigValue values.
     *
     * @param fieldValue Alternating String fields and ConfigValue values.
     * @return A config field and value map.
     */
    public static HashMap< String, ConfigValue > buildConfigValueMapFromConfigValues(
            @Nullable final Object... fieldValue )
    {
        if ( fieldValue == null || fieldValue.length == 0 )
        {
            return new HashMap<>();
        }
        else if ( fieldValue.length % 2 != 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter fieldValue must have an even " + "length" + " for field and value " + "alternation." );
        }
        else
        {
            final HashMap< String, ConfigValue > configValueMap = new HashMap<>();

            for ( int i = 0; i < fieldValue.length; i += 2 )
            {
                if ( !( fieldValue[ i ] instanceof String ) )
                {
                    throw new IllegalArgumentException(
                            "Parameter fieldValue must have String " + "field objects alternating with " + "ConfigValue objects." );
                }
                else if ( fieldValue[ i + 1 ] == null )
                {
                    configValueMap.put( ( String ) fieldValue[ i ], new ConfigValue( null, null ) );
                }
                else if ( !( fieldValue[ i + 1 ] instanceof ConfigValue ) )
                {
                    throw new IllegalArgumentException(
                            "Parameter fieldValue must have " + "ConfigValue objects alternate " + "after String field objects." );
                }
                else
                {
                    configValueMap.put( ( String ) fieldValue[ i ], ( ConfigValue ) fieldValue[ i + 1 ] );
                }
            }

            return configValueMap;
        }
    }

    /**
     * Returns a config field and value map from an input of alternating String fields with its
     * respective value and default value.
     *
     * @param fieldValueDefaultValue Alternating String fields and its respective value and default
     *                               value.
     * @return A config field and value map.
     */
    public static HashMap< String, ConfigValue > buildConfigValueMapFromObjects(
            @Nullable final Object... fieldValueDefaultValue )
    {
        if ( fieldValueDefaultValue == null || fieldValueDefaultValue.length == 0 )
        {
            return new HashMap<>();
        }
        else if ( fieldValueDefaultValue.length % 3 != 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter fieldValueDefaultValue must have a " + "length" + " evenly divisible by 3 for field, value, and default value " + "alternation." );
        }
        else
        {
            final HashMap< String, ConfigValue > configValueMap = new HashMap<>();

            for ( int i = 0; i < fieldValueDefaultValue.length; i += 3 )
            {
                if ( !( fieldValueDefaultValue[ i ] instanceof String ) )
                {
                    throw new IllegalArgumentException(
                            "Parameter fieldValueDefaultValue must " + "have String " + "field objects " + "alternating with its respective " + "value and default value." );
                }
                else
                {
                    final String field = ( String ) fieldValueDefaultValue[ i ];
                    final ConfigValue configValue =
                            new ConfigValue( fieldValueDefaultValue[ i + 1 ], fieldValueDefaultValue[ i + 2 ] );

                    configValueMap.put( field, configValue );
                }
            }

            return configValueMap;
        }
    }

    /**
     * Returns a config object map from an input of alternating String fields and object values.
     *
     * @param fieldObject Alternating String fields and object values.
     * @return A config object map.
     */
    public static HashMap< String, Object > buildConfigObjectMap( @Nullable final Object... fieldObject )
    {
        if ( fieldObject == null || fieldObject.length == 0 )
        {
            return new HashMap<>();
        }
        else if ( fieldObject.length % 2 != 0 )
        {
            throw new IllegalArgumentException(
                    "Parameter fieldObject must have an even " + "length" + " for field and value " + "alternation." );
        }
        else
        {
            final HashMap< String, Object > fieldObjectMap = new HashMap<>();

            for ( int i = 0; i < fieldObject.length; i += 2 )
            {
                if ( !( fieldObject[ i ] instanceof String ) )
                {
                    throw new IllegalArgumentException(
                            "Parameter fieldValue must have String " + "field objects alternating with " + "ConfigValue objects." );
                }
                else
                {
                    fieldObjectMap.put( ( String ) fieldObject[ i ], fieldObject[ i + 1 ] );
                }
            }

            return fieldObjectMap;
        }
    }

}