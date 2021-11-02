package com.qhucy.configmanager.file;

import com.qhucy.configmanager.util.TestingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.InvalidPathException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit Testing for the ConfigFileManager class.
 *
 * @see ConfigFileManager
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
final class ConfigFileManagerTest
{

    @Nested
    @DisplayName( "Static method assertValidConfigFile Testing" )
    final class StaticMethodAssertValidConfigFileTesting
    {

        @Test
        @DisplayName( "Throws exception if param configFile is null" )
        void throwsExceptionIfParamConfigFileIsNull()
        {
            TestingUtil.assertParameter( () -> ConfigFileManager.assertValidConfigFile( null ) );
        }

        @Test
        @DisplayName( "Throws exception if file does not exist" )
        void throwsExceptionIfFileDoesNotExist()
        {
            assertThrows( InvalidPathException.class,
                          () -> ConfigFileManager.assertValidConfigFile( new File( "invalid-file.xrvz" ) ) );
        }

        @Test
        @DisplayName( "Throws exception if file is not a file" )
        void throwsExceptionIfFileIsNotAFile()
        {
            assertThrows( InvalidPathException.class,
                          () -> ConfigFileManager.assertValidConfigFile( new File( "src/test" ) ) );
        }

        @Test
        @DisplayName( "Throws exception if file is not YAML file" )
        void throwsExceptionIfFileIsNotYAMLFile()
        {
            assertThrows( InvalidPathException.class, () -> ConfigFileManager.assertValidConfigFile(
                    new File( "src/test/java/com/qhucy/configmanager/text" + ".txt" ) ) );
        }

        @Test
        @DisplayName( "Does not throw if valid config file" )
        void doesNotThrowIfValidConfigFile()
        {
            assertDoesNotThrow( () -> ConfigFileManager.assertValidConfigFile(
                    new File( "src/test/java/com/qhucy/configmanager" + "/dummy_config" + ".yml" ) ) );
        }

    }

}