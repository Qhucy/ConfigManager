package com.qhucy.configmanager;

import com.qhucy.configmanager.value.ConfigValue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing class for ConfigBuilder.
 *
 * @see ConfigBuilder
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@DisplayName( "ConfigBuilder Class Testing" )
class ConfigBuilderTest
{

    @Nested
    @DisplayName( "Static method buildConfigValueMapFromConfigValues Testing" )
    final class StaticMethodBuildConfigValueMapFromConfigValuesTesting
    {

        @Test
        @DisplayName( "Building empty map" )
        void buildingEmptyMap()
        {
            assertEquals( 0, ConfigBuilder.buildFromConfigValues().size() );
            assertEquals( 0, ConfigBuilder.buildFromConfigValues( null ).size() );
        }

        @Test
        @DisplayName( "Throws if odd number of args" )
        void throwsIfOddNumberOfArgs()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildFromConfigValues( "field", new ConfigValue( 1,
                                                                                               2 ), "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildFromConfigValues( 1, 2 ) );
        }

        @Test
        @DisplayName( "Throws if value is not ConfigValue" )
        void throwsIfValueIsNotConfigValue()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildFromConfigValues( "field", 40 ) );
        }

        @Test
        @DisplayName( "Does not throw if value for ConfigValue is null" )
        void doesNotThrowIfValueForConfigValueIsNull()
        {
            Assertions.assertDoesNotThrow( () -> ConfigBuilder.buildFromConfigValues( "field",
                                                                                      null ) );
        }

        @Test
        @DisplayName( "Builds config field and value map correctly" )
        void buildsConfigFieldAndValueMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigBuilder.buildFromConfigValues( "a", null, "b", new ConfigValue( 1, 2 ) );

            assertNull( temp.get( "a" ).getValue() );
            assertNull( temp.get( "a" ).getDefaultValue() );
            assertEquals( 1, temp.get( "b" ).getValue() );
            assertEquals( 2, temp.get( "b" ).getDefaultValue() );


        }

    }

    @Nested
    @DisplayName( "Static method buildConfigValueMapFromObjects Testing" )
    final class StaticMethodBuildConfigValueMapFromObjectsTesting
    {

        @Test
        @DisplayName( "Building empty map" )
        void buildingEmptyMap()
        {
            assertEquals( 0, ConfigBuilder.buildFromObjects().size() );
            assertEquals( 0, ConfigBuilder.buildFromObjects( null ).size() );
        }

        @Test
        @DisplayName( "Throws if number of args not divisible by 3" )
        void throwsIfNumberOfArgsNotDivisibleBy3()
        {
            assertThrows( IllegalArgumentException.class, () -> ConfigBuilder.buildFromObjects(
                    "field", 1, 2, "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class, () -> ConfigBuilder.buildFromObjects( 1
                    , 2, 3 ) );
        }

        @Test
        @DisplayName( "Builds config field and value map correctly" )
        void buildsConfigFieldAndValueMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigBuilder.buildFromObjects( "a", null, null, "b", 1, 2 );

            assertNull( temp.get( "a" ).getValue() );
            assertNull( temp.get( "a" ).getDefaultValue() );
            assertEquals( 1, temp.get( "b" ).getValue() );
            assertEquals( 2, temp.get( "b" ).getDefaultValue() );
        }

    }

    @Nested
    @DisplayName( "Static method buildConfigObjectMap Testing" )
    final class StaticMethodBuildConfigObjectMapTesting
    {

        @Test
        @DisplayName( "Building empty map" )
        void buildingEmptyMap()
        {
            assertEquals( 0, ConfigBuilder.buildFromObjectsNoDefaultValue().size() );
            assertEquals( 0, ConfigBuilder.buildFromObjectsNoDefaultValue( null ).size() );
        }

        @Test
        @DisplayName( "Throws if odd number of args" )
        void throwsIfOddNumberOfArgs()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildFromObjectsNoDefaultValue( "field", 1,
                                                                              "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildFromObjectsNoDefaultValue( 1, 2 ) );
        }

        @Test
        @DisplayName( "Builds config object map correctly" )
        void buildsConfigObjectMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigBuilder.buildFromObjectsNoDefaultValue( "a", null, "b", 3 );

            assertTrue( temp.get( "a" ).valuesMissing() );
            assertEquals( 3, temp.get( "b" ).getValue() );
            assertFalse( temp.get( "b" ).hasDefaultValue() );
        }

    }

}