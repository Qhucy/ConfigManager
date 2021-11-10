package com.qhucy.configmanager.source;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Testing for the FileSource class.
 *
 * @see FileSource
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
@DisplayName( "FileSource Class Testing" )
final class FileSourceTest
{

    // Default File object for the text path.
    private final static File   SOURCE_FILE = new File( "src/test/resources/dummy_config.yml" );
    // Default text path for the file source for testing.
    private final static String SOURCE_PATH = SOURCE_FILE.getAbsolutePath();


    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception" )
        void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new FileSource( SOURCE_FILE ) );
        }

        @Test
        @DisplayName( "Doesn't instantiates if param fileSource is null" )
        void doesNotInstantiateIfParamFileSourceNull()
        {
            assertThrows( IllegalArgumentException.class, () -> new FileSource( null ) );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Getters Testing" )
    final class AttributeGettersTesting
    {

        // FileSource object used for testing the class attribute getters.
        private FileSource fileSource;

        @BeforeEach
        void setUp()
        {
            fileSource = new FileSource( SOURCE_FILE );
        }

        @Test
        @DisplayName( "getFileSource returns correct value" )
        void getFileSourceReturnsCorrectValue()
        {
            assertEquals( SOURCE_FILE, fileSource.getSourceFile() );
            assertEquals( SOURCE_PATH, fileSource.getSourcePath() );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Setters Testing" )
    final class AttributeSettersTesting
    {

        // The FileSource object used for unit testing.
        private FileSource fileSource;

        @BeforeEach
        void setUp()
        {
            this.fileSource = new FileSource( SOURCE_FILE );
        }

        @Test
        @DisplayName( "Throws exception if setting file source to null" )
        void throwsExceptionIfSettingFileSourceToNull()
        {
            assertThrows( IllegalArgumentException.class, () -> fileSource.setSourceFile( null ) );
        }

        @Test
        @DisplayName( "Setting file source changes file source" )
        void settingFileSourceChangesFileSource()
        {
            final File file = new File( "src/test/resources/dummy_config.toml" );

            assertEquals( SOURCE_FILE, fileSource.getSourceFile() );
            assertEquals( SOURCE_PATH, fileSource.getSourcePath() );

            fileSource.setSourceFile( file );

            assertEquals( file, fileSource.getSourceFile() );
            assertEquals( file.getAbsolutePath(), fileSource.getSourcePath() );
        }

    }

    @Nested
    @DisplayName( "Creating FileSource from a File" )
    final class FileSourceFromFile
    {

        @Test
        @DisplayName( "Throws exception if param file is null" )
        void throwsExceptionIfParamFileIsNull()
        {
            assertThrows( IllegalArgumentException.class, () -> FileSource.fromFile( null ) );
        }

        @Test
        @DisplayName( "Returns FileSource with the file it was created from" )
        void returnsFileSourceWithCorrectFile()
        {
            final FileSource fileSource = new FileSource( SOURCE_FILE );

            assertEquals( SOURCE_FILE, fileSource.getSourceFile() );
            assertEquals( SOURCE_PATH, fileSource.getSourcePath() );
        }

    }

}