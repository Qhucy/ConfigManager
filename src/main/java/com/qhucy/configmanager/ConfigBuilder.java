package com.qhucy.configmanager;

import com.qhucy.configmanager.value.ConfigValue;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Utility class containing static methods to build config field and value maps.
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
public final class ConfigBuilder
{

    /**
     * Returns a config field and value map from an input of alternating String fields and
     * ConfigValue values.
     *
     * @param fieldValue Alternating String fields and ConfigValue values.
     *
     * @return A config field and value map.
     */
    @NonNull
    public static HashMap< String, ConfigValue > buildFromConfigValues( @Nullable final Object... fieldValue )
    {
        if ( fieldValue == null || fieldValue.length == 0 )
        {
            return new HashMap<>();
        }
        else if ( fieldValue.length % 2 != 0 )
        {
            throw new IllegalArgumentException( """
                                                Parameter fieldValue must have an even length for \
                                                field and value alternation.
                                                """ );
        }
        else
        {
            final HashMap< String, ConfigValue > configValueMap = new HashMap<>();

            for ( int i = 0; i < fieldValue.length; i += 2 )
            {
                if ( !( fieldValue[ i ] instanceof final String field ) )
                {
                    throw new IllegalArgumentException( """
                                                        Parameter fieldValue must have String \
                                                        field objects alternating with ConfigValue \
                                                        objects.
                                                        """ );
                }
                else if ( fieldValue[ i + 1 ] == null )
                {
                    configValueMap.put( field, new ConfigValue( null ) );
                }
                else if ( !( fieldValue[ i + 1 ] instanceof final ConfigValue configValue ) )
                {
                    throw new IllegalArgumentException( """
                                                        Parameter fieldValue must have String \
                                                        field objects alternating with ConfigValue \
                                                        objects.
                                                        """ );
                }
                else
                {
                    configValueMap.put( field, configValue );
                }
            }

            return configValueMap;
        }
    }

    /**
     * Returns a config field and value map from an input of alternating String fields with its
     * respective value and default value.
     *
     * @param fieldValues Alternating String fields and its respective value and default value.
     *
     * @return A config field and value map.
     */
    @NonNull
    public static HashMap< String, ConfigValue > buildFromObjects( @Nullable final Object... fieldValues )
    {
        if ( fieldValues == null || fieldValues.length == 0 )
        {
            return new HashMap<>();
        }
        else if ( fieldValues.length % 3 != 0 )
        {
            throw new IllegalArgumentException( """
                                                Parameter fieldValues must have a \
                                                length evenly divisible by 3 for field, value, \
                                                and default value alternation.
                                                """ );
        }
        else
        {
            final HashMap< String, ConfigValue > configValueMap = new HashMap<>();

            for ( int i = 0; i < fieldValues.length; i += 3 )
            {
                if ( !( fieldValues[ i ] instanceof final String field ) )
                {
                    throw new IllegalArgumentException( """
                                                        Parameter fieldValues must \
                                                        have String field objects alternating with \
                                                        its respective value and default value.
                                                        """ );
                }
                else
                {
                    configValueMap.put( field, new ConfigValue( fieldValues[ i + 1 ],
                                                                fieldValues[ i + 2 ] ) );
                }
            }

            return configValueMap;
        }
    }

    /**
     * Returns a config field and value map from an input of alternating String fields with its
     * respective value. The default value is always set to null.
     *
     * @param fieldValue Alternating String fields and its respective value.
     *
     * @return A config field and value map.
     */
    @NonNull
    public static HashMap< String, ConfigValue > buildFromObjectsNoDefaultValue( @Nullable final Object... fieldValue )
    {
        if ( fieldValue == null || fieldValue.length == 0 )
        {
            return new HashMap<>();
        }
        else if ( fieldValue.length % 2 != 0 )
        {
            throw new IllegalArgumentException( """
                                                Parameter fieldValue must have an even length for \
                                                field and value alternation.
                                                """ );
        }
        else
        {
            final HashMap< String, ConfigValue > configValueMap = new HashMap<>();

            for ( int i = 0; i < fieldValue.length; i += 2 )
            {
                if ( !( fieldValue[ i ] instanceof final String field ) )
                {
                    throw new IllegalArgumentException( """
                                                        Parameter fieldValue must have String field\
                                                         objects alternating with value objects.
                                                        """ );
                }
                else
                {
                    configValueMap.put( field, new ConfigValue( fieldValue[ i + 1 ] ) );
                }
            }

            return configValueMap;
        }
    }

}