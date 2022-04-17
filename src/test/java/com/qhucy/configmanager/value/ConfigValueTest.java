package com.qhucy.configmanager.value;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Testing for the ConfigValue class.
 *
 * @see ConfigValue
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@DisplayName("ConfigValue Class Testing")
final class ConfigValueTest
{

    // Value constant for unit testing.
    private final static int VALUE = 14;
    // Default value constant for unit testing.
    private final static int DEFAULT_VALUE = 7;

    // ConfigValue object constant used for testing the class attribute getters.
    private final ConfigValue CONFIG_VALUE = new ConfigValue(VALUE, DEFAULT_VALUE);

    @Nested
    @DisplayName("Constructor Testing")
    final class ConstructorTest
    {

        @Test
        @DisplayName("Instantiates with no exception on null value")
        void instantiatesWithNoExceptionNull()
        {
            assertDoesNotThrow(() -> new ConfigValue(null, null));
            assertDoesNotThrow(() -> new ConfigValue(null));
        }

        @Test
        @DisplayName("Instantiates with no exception on non-null value")
        void instantiatesWithNoExceptionNonNull()
        {
            assertDoesNotThrow(() -> new ConfigValue(VALUE, DEFAULT_VALUE));
            assertDoesNotThrow(() -> new ConfigValue(VALUE));
        }

    }

    @Nested
    @DisplayName("Class Attribute Getters Testing")
    final class AttributeGettersTesting
    {

        @Test
        @DisplayName("Getting the value class attribute")
        void gettingTheValueClassAttribute()
        {
            assertEquals(VALUE, CONFIG_VALUE.getValue());
        }

        @Test
        @DisplayName("hasValue true when non-error value")
        void hasValueTrueWhenNonError()
        {
            final ConfigValue configValue = new ConfigValue(VALUE, DEFAULT_VALUE);

            assertTrue(configValue.hasValue());
        }

        @Test
        @DisplayName("hasValue false when error value")
        void hasValueFalseWhenError()
        {
            final ConfigValue configValue = new ConfigValue(null, DEFAULT_VALUE);

            assertFalse(configValue.hasValue());
        }

        @Test
        @DisplayName("Getting the defaultValue class attribute")
        void gettingTheDefaultValueClassAttribute()
        {
            assertEquals(DEFAULT_VALUE, CONFIG_VALUE.getDefaultValue());
        }

        @Test
        @DisplayName("hasDefaultValue true when non-error value")
        void hasDefaultValueTrueWhenNonError()
        {
            final ConfigValue configValue = new ConfigValue(VALUE, 30);

            assertTrue(configValue.hasDefaultValue());
        }

        @Test
        @DisplayName("hasDefaultValue false when error value")
        void hasDefaultValueFalseWhenError()
        {
            final ConfigValue configValue = new ConfigValue(VALUE, null);

            assertFalse(configValue.hasDefaultValue());
        }

        @Test
        @DisplayName("valuesMissing true when both values missing")
        void valuesMissingTrueWhenBothMissing()
        {
            final ConfigValue configValue = new ConfigValue(null, null);

            assertTrue(configValue.valuesMissing());
        }

        @Test
        @DisplayName("valuesMissing false when value not missing")
        void valuesMissingFalseWhenValueNotMissing()
        {
            final ConfigValue configValue = new ConfigValue(VALUE, null);

            assertFalse(configValue.valuesMissing());
        }

        @Test
        @DisplayName("valuesMissing false when defaultValue not missing")
        void valuesMissingFalseWhenDefaultValueNotMissing()
        {
            final ConfigValue configValue = new ConfigValue(null, DEFAULT_VALUE);

            assertFalse(configValue.valuesMissing());
        }

    }

    @Nested
    @DisplayName("Class Attribute Setters Testing")
    final class AttributeSettersTesting
    {

        // The ConfigValue object used for unit testing.
        private ConfigValue configValue;

        @BeforeEach
        void setUp()
        {
            this.configValue = new ConfigValue(VALUE, DEFAULT_VALUE);
        }

        @Test
        @DisplayName("Setting the value class attribute")
        void settingTheValueClassAttribute()
        {
            configValue.setValue("hi");

            assertEquals("hi", configValue.getValue());
        }

        @Test
        @DisplayName("Setting the defaultValue class attribute")
        void settingTheDefaultValueClassAttribute()
        {
            configValue.setDefaultValue(true);

            assertEquals(true, configValue.getDefaultValue());
        }

    }

}