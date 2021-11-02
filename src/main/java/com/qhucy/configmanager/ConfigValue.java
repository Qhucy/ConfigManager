package com.qhucy.configmanager;

import org.jetbrains.annotations.Nullable;

/**
 * Class that stores a value and default value for a field in a config field and value map.
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public class ConfigValue
{

    // The value for a field in a config field and value map.
    private Object value;
    // The default value for a field in a config field and value map.
    private Object defaultValue;

    /**
     * Instantiates ConfigValue from a value and default value.
     *
     * @param value        The value for the field in a config field and value map.
     * @param defaultValue The default value for the field in a config field and value map.
     */
    public ConfigValue( @Nullable final Object value, @Nullable final Object defaultValue )
    {
        setValue( value );
        setDefaultValue( defaultValue );
    }

    /**
     * Instantiates ConfigValue from a value. The default value is set to missing.
     *
     * @param value The value for the field in a config field and value map.
     */
    public ConfigValue( @Nullable final Object value )
    {
        setValue( value );
        setDefaultValue( ErrorValue.MISSING );
    }

    /**
     * Returns the value class attribute.
     *
     * @return The value class attribute.
     */
    @Nullable
    public final Object getValue()
    {
        return value;
    }

    /**
     * Sets the value class attribute to another value.
     *
     * @param value The new value class attribute.
     */
    public final void setValue( @Nullable final Object value )
    {
        this.value = value;
    }

    /**
     * Returns if the value class attribute is stored without an error value.
     *
     * @return If the value class attribute is stored without an error value.
     */
    public final boolean hasValue()
    {
        return !( getValue() instanceof ErrorValue );
    }

    /**
     * Returns the defaultValue class attribute.
     *
     * @return The defaultValue class attribute.
     */
    @Nullable
    public final Object getDefaultValue()
    {
        return defaultValue;
    }

    /**
     * Sets the defaultValue class attribute to another value.
     *
     * @param defaultValue The new defaultValue class attribute.
     * @see ErrorValue
     */
    public final void setDefaultValue( @Nullable final Object defaultValue )
    {
        this.defaultValue = defaultValue;
    }

    /**
     * Returns if the defaultValue class attribute is stored without an error value.
     *
     * @return If the defaultValue class attribute is stored without an error value.
     */
    public final boolean hasDefaultValue()
    {
        return !( getDefaultValue() instanceof ErrorValue );
    }

}