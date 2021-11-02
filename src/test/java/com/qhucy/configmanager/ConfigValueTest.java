package com.qhucy.configmanager;

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
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
@DisplayName( "ConfigValue Class Testing" )
final class ConfigValueTest
{

    // Value constant for unit testing.
    private final static int VALUE         = 14;
    // Default value constant for unit testing.
    private final static int DEFAULT_VALUE = 7;

    // ConfigValue object constant used for testing the class attribute getters.
    private final ConfigValue CONFIG_VALUE = new ConfigValue( VALUE, DEFAULT_VALUE );

    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception " )
        final void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new ConfigValue( null, null ) );
            assertDoesNotThrow( () -> new ConfigValue( null ) );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Getters Testing" )
    final class AttributeGettersTesting
    {

        @Test
        @DisplayName( "Getting the value class attribute" )
        final void gettingTheValueClassAttribute()
        {
            assertEquals( VALUE, CONFIG_VALUE.getValue() );
        }

        @Test
        @DisplayName( "hasValue true when non-error value" )
        final void hasValueTrueWhenNonError()
        {
            final ConfigValue configValue = new ConfigValue( VALUE, DEFAULT_VALUE );

            assertTrue( configValue.hasValue() );
        }

        @Test
        @DisplayName( "hasValue false when error value" )
        final void hasValueFalseWhenError()
        {
            final ConfigValue configValue = new ConfigValue( ErrorValue.MISSING, DEFAULT_VALUE );

            assertFalse( configValue.hasValue() );
        }

        @Test
        @DisplayName( "Getting the defaultValue class attribute" )
        final void gettingTheDefaultValueClassAttribute()
        {
            assertEquals( DEFAULT_VALUE, CONFIG_VALUE.getDefaultValue() );
        }

        @Test
        @DisplayName( "hasDefaultValue true when non-error value" )
        final void hasDefaultValueTrueWhenNonError()
        {
            final ConfigValue configValue = new ConfigValue( VALUE, 30 );

            assertTrue( configValue.hasDefaultValue() );
        }

        @Test
        @DisplayName( "hasDefaultValue false when error value" )
        final void hasDefaultValueFalseWhenError()
        {
            final ConfigValue configValue = new ConfigValue( VALUE, ErrorValue.MISSING );

            assertFalse( configValue.hasDefaultValue() );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Setters Testing" )
    final class AttributeSettersTesting
    {

        // The ConfigValue object used for unit testing.
        private ConfigValue configValue;

        @BeforeEach
        final void setUp()
        {
            this.configValue = new ConfigValue( VALUE, DEFAULT_VALUE );
        }

        @Test
        @DisplayName( "Setting the value class attribute" )
        final void settingTheValueClassAttribute()
        {
            configValue.setValue( "hi" );

            assertEquals( "hi", configValue.getValue() );
        }

        @Test
        @DisplayName( "Setting the defaultValue class attribute" )
        final void settingTheDefaultValueClassAttribute()
        {
            configValue.setDefaultValue( true );

            assertEquals( true, configValue.getDefaultValue() );
        }

    }

}