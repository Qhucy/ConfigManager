package com.qhucy.configmanager.file;

import com.qhucy.configmanager.util.TestingUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Testing for the ConfigFileManager class.
 *
 * @see ConfigFileManager
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
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
            TestingUtil.assertParameter( () -> ConfigFileManager.isValidConfigFile( null ) );
        }

        @Test
        @DisplayName( "Throws exception if file does not exist" )
        void throwsExceptionIfFileDoesNotExist()
        {
            assertFalse( () -> ConfigFileManager.isValidConfigFile( new File( "invalid-file.xrvz" ) ) );
        }

        @Test
        @DisplayName( "Throws exception if file is not a file" )
        void throwsExceptionIfFileIsNotAFile()
        {
            assertFalse( () -> ConfigFileManager.isValidConfigFile( new File( "src/test" ) ) );
        }

        @Test
        @DisplayName( "Throws exception if file is not YAML file" )
        void throwsExceptionIfFileIsNotYAMLFile()
        {
            assertFalse( () -> ConfigFileManager.isValidConfigFile( new File( "src/test/resources/text.txt" ) ) );
        }

        @Test
        @DisplayName( "Does not throw if valid config file" )
        void doesNotThrowIfValidConfigFile()
        {
            assertTrue(
                    () -> ConfigFileManager.isValidConfigFile( new File( "src/test/resources/dummy_config.yml" ) ) );
        }

    }

}