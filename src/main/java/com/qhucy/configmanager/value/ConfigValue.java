package com.qhucy.configmanager.value;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

/**
 * Class that stores a value and default value for a field in a config field and value map.
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@Setter
@Getter
public class ConfigValue
{

    // The value for a field in a config field and value map.
    @Nullable
    private Object value;
    // The default value for a field in a config field and value map.
    @Nullable
    private Object defaultValue;

    /**
     * Instantiates ConfigValue from a value and default value.
     *
     * @param value        The value for the field in a config field and value map.
     * @param defaultValue The default value for the field in a config field and value map.
     */
    public ConfigValue(@Nullable final Object value, @Nullable final Object defaultValue)
    {
        setValue(value);
        setDefaultValue(defaultValue);
    }

    /**
     * Instantiates ConfigValue from a value. The default value is set to null.
     *
     * @param value The value for the field in a config field and value map.
     */
    public ConfigValue(@Nullable final Object value)
    {
        setValue(value);
        setDefaultValue(null);
    }

    /**
     * Returns if the value class attribute is stored without a null value.
     *
     * @return If the value class attribute is stored without a null value.
     */
    public final boolean hasValue()
    {
        return getValue() != null;
    }

    /**
     * Returns if the defaultValue class attribute is stored without a null value.
     *
     * @return If the defaultValue class attribute is stored without a null value.
     */
    public final boolean hasDefaultValue()
    {
        return getDefaultValue() != null;
    }

    /**
     * Returns true if the value and defaultValue class attribute are both null.
     *
     * @return True if the value and defaultValue class attribute are both null.
     */
    public final boolean valuesMissing()
    {
        return !hasValue() && !hasDefaultValue();
    }

}