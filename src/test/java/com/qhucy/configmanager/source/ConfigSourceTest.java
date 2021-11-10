package com.qhucy.configmanager.source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Testing for the ConfigSource class.
 *
 * @see ConfigSource
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
@DisplayName( "ConfigSource Class Testing" )
final class ConfigSourceTest
{

    // Default text path for the config source for testing.
    private final static String CONFIG_SOURCE = "config.yml";

    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception" )
        void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new ConfigSource( CONFIG_SOURCE ) );
        }

        @Test
        @DisplayName( "Doesn't instantiates if param configSource is null" )
        void doesNotInstantiateIfParamConfigSourceNull()
        {
            assertThrows( IllegalArgumentException.class, () -> new ConfigSource( null ) );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Getters Testing" )
    final class AttributeGettersTesting
    {

        // ConfigSource object used for testing the class attribute getters.
        private ConfigSource configSource;

        @BeforeEach
        void setUp()
        {
            configSource = new ConfigSource( CONFIG_SOURCE );
        }

        @Test
        @DisplayName( "getConfigSource returns correct value" )
        void getConfigSourceReturnsCorrectValue()
        {
            assertEquals( configSource.getSource(), CONFIG_SOURCE );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Setters Testing" )
    final class AttributeSettersTesting
    {

        // The ConfigSource object used for unit testing.
        private ConfigSource configSource;

        @BeforeEach
        void setUp()
        {
            this.configSource = new ConfigSource( CONFIG_SOURCE );
        }

        @Test
        @DisplayName( "Throws exception if setting config source to null" )
        void throwsExceptionIfSettingConfigSourceToNull()
        {
            assertThrows( IllegalArgumentException.class, () -> configSource.setSource( null ) );
        }

        @Test
        @DisplayName( "Setting config source changes config source" )
        void settingConfigSourceChangesConfigSource()
        {
            assertEquals( configSource.getSource(), CONFIG_SOURCE );

            configSource.setSource( "another_config.yml" );

            assertEquals( "another_config.yml", configSource.getSource() );
        }

    }

    @Nested
    @DisplayName( "Creating ConfigSource from a string" )
    final class ConfigSourceFromString
    {

        @Test
        @DisplayName( "Throws exception if param file is null" )
        void throwsExceptionIfParamFileIsNull()
        {
            assertThrows( IllegalArgumentException.class, () -> ConfigSource.fromString( null ) );
        }

        @Test
        @DisplayName( "Returns ConfigSource with the string it was created from" )
        void returnsConfigSourceWithCorrectString()
        {
            final ConfigSource configSource = new ConfigSource( CONFIG_SOURCE );

            assertEquals( CONFIG_SOURCE, configSource.getSource() );
        }

    }

}