package com.qhucy.configmanager.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    private final static String SOURCE_PATH = "src/test/resources/dummy_config.yml";
    // Default file for the config source for testing.
    private final static File   SOURCE_FILE = new File( SOURCE_PATH );

    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception" )
        void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new ConfigSource( SOURCE_PATH ) );
            assertDoesNotThrow( () -> new ConfigSource( SOURCE_FILE ) );
            assertDoesNotThrow( () -> new ConfigSource( SOURCE_FILE.toPath() ) );
        }

        @Test
        @DisplayName( "Does not instantiate if required params are null" )
        void doesNotInstantiateIfReqParamsNull()
        {
            assertThrows( NullPointerException.class, () -> new ConfigSource( ( String ) null ) );
            assertThrows( NullPointerException.class, () -> new ConfigSource( ( Path ) null ) );
            assertThrows( NullPointerException.class, () -> new ConfigSource( ( File ) null ) );
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
            configSource = new ConfigSource( SOURCE_PATH );
        }

        @Test
        @DisplayName( "sourcePath" )
        void getSourcePath()
        {
            assertEquals( configSource.getSourcePath(), SOURCE_PATH );
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
            this.configSource = new ConfigSource( SOURCE_PATH );
        }

        @Test
        @DisplayName( "Throws exception if setting sourcePath to null" )
        void throwsExceptionIfSettingSourcePathToNull()
        {
            assertThrows( NullPointerException.class, () -> configSource.setSourcePath( ( String ) null ) );
            assertThrows( NullPointerException.class, () -> configSource.setSourcePath( ( Path ) null ) );
            assertThrows( NullPointerException.class, () -> configSource.setSourcePath( ( File ) null ) );
        }

        @Test
        @DisplayName( "Setting sourcePath from string" )
        void settingSourcePathFromString()
        {
            assertEquals( configSource.getSourcePath(), SOURCE_PATH );

            configSource.setSourcePath( "another_config.yml" );
            assertEquals( "another_config.yml", configSource.getSourcePath() );
        }

        @Test
        @DisplayName( "Setting sourcePath from path" )
        void settingSourcePathFromPath()
        {
            final File file = new File( "text.txt" );

            configSource.setSourcePath( file.toPath() );
            assertEquals( file.getAbsolutePath(), configSource.getSourcePath() );
        }

        @Test
        @DisplayName( "Setting sourcePath from file" )
        void settingSourcePathFromFile()
        {
            final File file = new File( "text.txt" );

            configSource.setSourcePath( file );
            assertEquals( file.getAbsolutePath(), configSource.getSourcePath() );
        }

    }

    @Nested
    @DisplayName( "Creating ConfigSource from a string" )
    final class ConfigSourceFromString
    {

        @Test
        @DisplayName( "Throws exception if required params are null" )
        void throwsExceptionIfReqParamsNull()
        {
            assertThrows( NullPointerException.class, () -> ConfigSource.fromString( null ) );
        }

        @Test
        @DisplayName( "Returns valid ConfigSource" )
        void returnsValidConfigSource()
        {
            final ConfigSource configSource = ConfigSource.fromString( SOURCE_PATH );

            assertEquals( SOURCE_PATH, configSource.getSourcePath() );
        }

    }

    @Nested
    @DisplayName( "Creating ConfigSource from a path" )
    final class ConfigSourceFromPath
    {

        @Test
        @DisplayName( "Throws exception if required params are null" )
        void throwsExceptionIfReqParamsNull()
        {
            assertThrows( NullPointerException.class, () -> ConfigSource.fromPath( null ) );
        }

        @Test
        @DisplayName( "Returns valid ConfigSource" )
        void returnsValidConfigSource()
        {
            final ConfigSource configSource = ConfigSource.fromPath( SOURCE_FILE.toPath() );

            assertEquals( SOURCE_FILE.getAbsolutePath(), configSource.getSourcePath() );
        }

    }

    @Nested
    @DisplayName( "Creating ConfigSource from a file" )
    final class ConfigSourceFromFile
    {

        @Test
        @DisplayName( "Throws exception if required params are null" )
        void throwsExceptionIfReqParamsNull()
        {
            assertThrows( NullPointerException.class, () -> ConfigSource.fromFile( null ) );
        }

        @Test
        @DisplayName( "Returns valid ConfigSource" )
        void returnsValidConfigSource()
        {
            final ConfigSource configSource = ConfigSource.fromFile( SOURCE_FILE );

            assertEquals( SOURCE_FILE.getAbsolutePath(), configSource.getSourcePath() );
        }

    }

    @Nested
    @DisplayName( "Getting file from path" )
    final class PathFromFile
    {

        // The ConfigSource object used for unit testing.
        private ConfigSource configSource;

        @BeforeEach
        void setUp()
        {
            this.configSource = new ConfigSource( SOURCE_PATH );
        }

        @Test
        @DisplayName( "Getting file from path returns correct file" )
        void gettingFileFromPathReturnsCorrectFile()
        {
            assertEquals( SOURCE_FILE, configSource.getFileFromPath() );
        }

        @Test
        @DisplayName( "Returns true if path leads to a file" )
        void returnsTrueIfPathLeadsToFile()
        {
            assertTrue( configSource.pathHasFile() );
        }

        @Test
        @DisplayName( "Returns false if path leads to a directory" )
        void returnsFalseIfPathLeadsToDirectory()
        {
            configSource.setSourcePath( new File( "src" ) );

            assertFalse( configSource.pathHasFile() );
        }

        @Test
        @DisplayName( "Returns false if path leads to nothing" )
        void returnsFalseIfPathLeadsToNothing()
        {
            configSource.setSourcePath( "src/nofile.txt" );

            assertFalse( configSource.pathHasFile() );
        }

    }

}