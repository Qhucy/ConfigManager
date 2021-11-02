package com.qhucy.configmanager.config;

import com.qhucy.configmanager.ConfigManager;
import com.qhucy.configmanager.value.ConfigValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ConfigBuilderTest
{

    @Nested
    @DisplayName( "Static method buildConfigValueMapFromConfigValues Testing" )
    final class StaticMethodBuildConfigValueMapFromConfigValuesTesting
    {

        @Test
        @DisplayName( "Building empty map" )
        final void buildingEmptyMap()
        {
            assertEquals( 0, ConfigBuilder.buildConfigValueMapFromConfigValues().size() );
            assertEquals( 0, ConfigBuilder.buildConfigValueMapFromConfigValues( null ).size() );
        }

        @Test
        @DisplayName( "Throws if odd number of args" )
        final void throwsIfOddNumberOfArgs()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildConfigValueMapFromConfigValues( "field", new ConfigValue( 1, 2 ),
                                                                                   "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        final void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildConfigValueMapFromConfigValues( 1, 2 ) );
        }

        @Test
        @DisplayName( "Throws if value is not ConfigValue" )
        final void throwsIfValueIsNotConfigValue()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildConfigValueMapFromConfigValues( "field", 40 ) );
        }

        @Test
        @DisplayName( "Does not throw if value for ConfigValue is null" )
        final void doesNotThrowIfValueForConfigValueIsNull()
        {
            assertDoesNotThrow( () -> ConfigBuilder.buildConfigValueMapFromConfigValues( "field", null ) );
        }

        @Test
        @DisplayName( "Builds config field and value map correctly" )
        final void buildsConfigFieldAndValueMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigBuilder.buildConfigValueMapFromConfigValues( "a", null, "b", new ConfigValue( 1, 2 ) );

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
        final void buildingEmptyMap()
        {
            assertEquals( 0, ConfigBuilder.buildConfigValueMapFromObjects().size() );
            assertEquals( 0, ConfigBuilder.buildConfigValueMapFromObjects( null ).size() );
        }

        @Test
        @DisplayName( "Throws if number of args not divisible by 3" )
        final void throwsIfNumberOfArgsNotDivisibleBy3()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildConfigValueMapFromObjects( "field", 1, 2, "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        final void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildConfigValueMapFromObjects( 1, 2, 3 ) );
        }

        @Test
        @DisplayName( "Builds config field and value map correctly" )
        final void buildsConfigFieldAndValueMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigBuilder.buildConfigValueMapFromObjects( "a", null, null, "b", 1, 2 );

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
        final void buildingEmptyMap()
        {
            assertEquals( 0, ConfigBuilder.buildConfigObjectMap().size() );
            assertEquals( 0, ConfigBuilder.buildConfigObjectMap( null ).size() );
        }

        @Test
        @DisplayName( "Throws if odd number of args" )
        final void throwsIfOddNumberOfArgs()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigBuilder.buildConfigObjectMap( "field", 1, "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        final void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class, () -> ConfigBuilder.buildConfigObjectMap( 1, 2 ) );
        }

        @Test
        @DisplayName( "Builds config object map correctly" )
        final void buildsConfigObjectMapCorrectly()
        {
            final HashMap< String, Object > temp = ConfigBuilder.buildConfigObjectMap( "a", null, "b", 3 );

            assertNull( temp.get( "a" ) );
            assertEquals( 3, temp.get( "b" ) );
        }

    }

}