package com.qhucy.configmanager;

import com.qhucy.configmanager.file.ConfigSource;
import com.qhucy.configmanager.util.TestingUtil;
import com.qhucy.configmanager.value.ConfigValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit Testing for the ConfigManager class.
 *
 * @see ConfigManager
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@DisplayName( "ConfigManager Class Testing" )
final class ConfigManagerTest
{

    // Configuration (config) field and value map used for constructing a ConfigManager.
    private final static HashMap< String, ConfigValue > CONFIG_VALUES = new HashMap<>();

    // Config field and value map used for constructing a ConfigManager.
    private final static HashMap< String, Object > VALUES         = new HashMap<>();
    // Config default field and value map used for constructing a ConfigManager.
    private final static HashMap< String, Object > DEFAULT_VALUES = new HashMap<>();

    // List of fields used for constructing a ConfigManager.
    private final static ArrayList< String > FIELDS              = new ArrayList<>();
    // List of values used for constructing a ConfigManager.
    private final static ArrayList< Object > VALUES_LIST         = new ArrayList<>();
    // List of default values used for constructing a ConfigManager.
    private final static ArrayList< Object > DEFAULT_VALUES_LIST = new ArrayList<>();

    // Default config file.
    private final static File                    CONFIG_FILE   =
            new File( "src/test/resources/dummy_config.yml" );
    private final static Map< String, String[] > COMMENTS      = new HashMap<>();
    // Random config source file path used for constructing a ConfigManager.
    private final static ConfigSource            CONFIG_SOURCE =
            new ConfigSource( "plugins/config.yml" );
    // Basic logger used for constructing a ConfigManager.
    private final static Logger                  LOGGER        =
            Logger.getLogger( ConfigManagerTest.class.getName() );

    /**
     * Puts fields and values into the config value field and value maps.
     */
    @BeforeAll
    static void setUpAll()
    {
        CONFIG_VALUES.put( "int", new ConfigValue( 3, 7 ) );
        CONFIG_VALUES.put( "double", new ConfigValue( 2.5, 9.2 ) );
        CONFIG_VALUES.put( "string", new ConfigValue( "hello", "goodbye" ) );
        CONFIG_VALUES.put( "def", new ConfigValue( null, 1.5 ) );
        CONFIG_VALUES.put( "all-missing", new ConfigValue( null, null ) );
        CONFIG_VALUES.put( "nested.value", new ConfigValue( true, false ) );

        VALUES.put( "int", 3 );
        VALUES.put( "double", 2.5 );
        VALUES.put( "string", "hello" );
        VALUES.put( "nested.value", true );

        DEFAULT_VALUES.put( "int", 7 );
        DEFAULT_VALUES.put( "double", 9.2 );
        DEFAULT_VALUES.put( "string", "goodbye" );
        DEFAULT_VALUES.put( "nested.value", false );

        FIELDS.add( "int" );
        FIELDS.add( "double" );
        FIELDS.add( "string" );
        FIELDS.add( "nested.value" );

        VALUES_LIST.add( 3 );
        VALUES_LIST.add( 2.5 );
        VALUES_LIST.add( "hello" );
        VALUES_LIST.add( true );

        DEFAULT_VALUES_LIST.add( 7 );
        DEFAULT_VALUES_LIST.add( 9.2 );
        DEFAULT_VALUES_LIST.add( "goodbye" );
        DEFAULT_VALUES_LIST.add( false );

        COMMENTS.put( "int", new String[]{ "# This is an int." } );
    }

    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception " )
        void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new ConfigManager( CONFIG_VALUES, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( VALUES, DEFAULT_VALUES, CONFIG_SOURCE,
                                                         LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, VALUES_LIST, DEFAULT_VALUES_LIST
                    , CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, VALUES_LIST, CONFIG_SOURCE,
                                                         LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( CONFIG_SOURCE, LOGGER, "field", 1, 2 ) );
            assertDoesNotThrow( () -> new ConfigManager( CONFIG_FILE, LOGGER ) );
        }

        @Test
        @DisplayName( "Instantiates if param values is null" )
        void instantiatesIfParamValuesIsNull()
        {
            assertDoesNotThrow( () -> new ConfigManager( null, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( null, DEFAULT_VALUES, CONFIG_SOURCE,
                                                         LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, null, DEFAULT_VALUES_LIST,
                                                         CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, null, CONFIG_SOURCE, LOGGER ) );
        }

        @Test
        @DisplayName( "Instantiates if param defaultValues is null" )
        void instantiatesIfParamDefaultValuesIsNull()
        {
            assertDoesNotThrow( () -> new ConfigManager( VALUES, null, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, VALUES_LIST, null, CONFIG_SOURCE
                    , LOGGER ) );
        }

        @Test
        @DisplayName( "Throws exception if param configSource is null" )
        void throwsExceptionIfParamConfigSourceIsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(), null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(),
                                                                  new HashMap<>(), null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null, null,
                                                                  null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null, null,
                                                                  LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( null, LOGGER, "field", 1, 2 ) );
        }

        @Test
        @DisplayName( "Throws exception if param logger is null" )
        void throwsExceptionIfParamLoggerIsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(), CONFIG_SOURCE,
                                                                  null ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(),
                                                                  new HashMap<>(), CONFIG_SOURCE,
                                                                  null ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null, null,
                                                                  CONFIG_SOURCE, null ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null,
                                                                  CONFIG_SOURCE, null ) );
            TestingUtil.assertParameter( () -> new ConfigManager( CONFIG_SOURCE, null, "field", 1
                    , 2 ) );
            TestingUtil.assertParameter( () -> new ConfigManager( CONFIG_FILE, null ) );
        }

        @Test
        @DisplayName( "Throws exception if param configFile is null" )
        void throwsExceptionIfParamConfigFileIsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigManager( null, LOGGER ) );
        }

        @Test
        @DisplayName( "Throws exception if param fields is null" )
        void throwsExceptionIfParamFieldsIsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigManager( null, VALUES_LIST,
                                                                  DEFAULT_VALUES_LIST,
                                                                  CONFIG_SOURCE, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( null, VALUES_LIST,
                                                                  CONFIG_SOURCE, LOGGER ) );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Getters Testing" )
    final class AttributeGettersTesting
    {

        // ConfigManager object used for testing the class attribute getters.
        private ConfigManager configManager;

        @BeforeEach
        void setUp()
        {
            final HashMap< String, ConfigValue > values = new HashMap<>();

            values.put( "int", new ConfigValue( 3, 7 ) );
            values.put( "def", new ConfigValue( 1.5, 7.2 ) );
            values.put( "all-missing", new ConfigValue( null, null ) );
            values.put( "nested.value", new ConfigValue( true, false ) );
            values.put( "nested.another", new ConfigValue( 10, 11 ) );
            values.put( "field.deep", new ConfigValue( 1, 2 ) );
            values.put( "field.even.deeper", new ConfigValue( 3, 4 ) );
            values.put( "field.even.more.deeper", new ConfigValue( 5, 6 ) );

            this.configManager = new ConfigManager( values, CONFIG_SOURCE, LOGGER );
        }

        @Test
        @DisplayName( "getConfigValue throws exception if param field is null" )
        void getConfigValueThrowsExceptionIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getConfigValue( null ) );
        }

        @Test
        @DisplayName( "Getting existing configValue" )
        void gettingExistingConfigValue()
        {
            assertEquals( 3, configManager.getConfigValue( "int" ).getValue() );
            assertEquals( 7, configManager.getConfigValue( "int" ).getDefaultValue() );
        }

        @Test
        @DisplayName( "Getting non-existing configValue" )
        void gettingNonExistingConfigValue()
        {
            assertNull( configManager.getConfigValue( "non-existing-field" ) );
        }

        @Test
        @DisplayName(
                "Getting config section fields throws exception if param parentField is null" )
        void gettingConfigSectionFieldsThrowsExceptionIfParamParentFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getConfigSectionFields( null, false,
                                                                                     false ) );
        }

        @Test
        @DisplayName( "Getting config section fields returns empty list if no nested fields" )
        void gettingConfigSectionReturnsEmptyListIfNoNestedFields()
        {
            assertEquals( 0, configManager.getConfigSectionFields( "int", false, false ).size() );
        }

        @Test
        @DisplayName( "Getting config section fields returns nested fields" )
        void gettingConfigSectionReturnsNestedFields()
        {
            assertEquals( 2, configManager.getConfigSectionFields( "nested", false, false )
                                          .size() );
            assertEquals( "deeper", configManager.getConfigSectionFields( "field.even.more",
                                                                          false, false )
                                                 .get( 0 ) );
        }

        @Test
        @DisplayName( "Getting config section fields with full fields" )
        void gettingConfigSectionFieldsWithFullFields()
        {
            assertEquals( "field.even.more.deeper", configManager.getConfigSectionFields( "field.even.more", true, false )
                                                                 .get( 0 ) );
        }

        @Test
        @DisplayName( "Getting config section fields with non full fields" )
        void gettingConfigSectionFieldsWithNonFullFields()
        {
            assertEquals( "deeper", configManager.getConfigSectionFields( "field.even.more",
                                                                          false, false )
                                                 .get( 0 ) );
        }

        @Test
        @DisplayName( "Getting config section fields with deep fields" )
        void gettingConfigSectionFieldsWithDeepFields()
        {
            assertEquals( 3, configManager.getConfigSectionFields( "field", false, true ).size() );

            assertTrue( configManager.getConfigSectionFields( "field", false, true )
                                     .contains( "deep" ) );
            assertTrue( configManager.getConfigSectionFields( "field", false, true )
                                     .contains( "even.deeper" ) );
            assertTrue( configManager.getConfigSectionFields( "field", false, true )
                                     .contains( "even.more.deeper" ) );
        }

        @Test
        @DisplayName( "Getting config section fields without deep fields" )
        void gettingConfigSectionFieldsWithoutDeepFields()
        {
            assertEquals( 1, configManager.getConfigSectionFields( "field", false, false ).size() );

            assertTrue( configManager.getConfigSectionFields( "field", false, false )
                                     .contains( "deep" ) );
        }

        @Test
        @DisplayName( "Getting config section fields with full and deep fields" )
        void gettingConfigSectionFieldsWithFullAndDeepFields()
        {
            assertEquals( 3, configManager.getConfigSectionFields( "field", true, true ).size() );

            assertTrue( configManager.getConfigSectionFields( "field", true, true )
                                     .contains( "field.deep" ) );
            assertTrue( configManager.getConfigSectionFields( "field", true, true )
                                     .contains( "field.even.deeper" ) );
            assertTrue( configManager.getConfigSectionFields( "field", true, true )
                                     .contains( "field.even.more.deeper" ) );
        }

        @Test
        @DisplayName( "Getting config section fields does not include parent field" )
        void gettingConfigSectionFieldsDoesNotIncludeParentField()
        {
            assertFalse( configManager.getConfigSectionFields( "nested", true, true )
                                      .contains( "nested" ) );
        }

        @Test
        @DisplayName( "Getting value throws exception if param field is null" )
        void gettingValueThrowsExceptionIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getValue( null ) );
        }

        @Test
        @DisplayName( "Getting value from non-existent field is null" )
        void gettingValueFromNonExistentFieldIsNull()
        {
            assertNull( configManager.getValue( "non-existent-field" ) );
        }

        @Test
        @DisplayName( "Getting value from field" )
        void gettingValueFromField()
        {
            assertEquals( 3, configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Getting value from field goes to default if missing" )
        void gettingValueFromFieldGoesToDefaultIfMissing()
        {
            assertEquals( 1.5, configManager.getValue( "def" ) );
        }

        @Test
        @DisplayName( "Getting value with both missing returns null" )
        void gettingValueWithBothMissingReturnsNull()
        {
            assertNull( configManager.getValue( "all-missing" ) );
        }

        @Test
        @DisplayName( "Getting default value throws exception if param field is null" )
        void gettingDefaultValueThrowsExceptionIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getDefaultValue( null ) );
        }

        @Test
        @DisplayName( "Getting default value from non-existent field is null" )
        void gettingDefaultValueFromNonExistentFieldIsNull()
        {
            assertNull( configManager.getDefaultValue( "non-existent-field" ) );
        }

        @Test
        @DisplayName( "Getting default value from field" )
        void gettingDefaultValueFromField()
        {
            final HashMap< String, ConfigValue > temp = new HashMap<>();

            temp.put( "int", new ConfigValue( 3, 7 ) );

            final ConfigManager configManager = new ConfigManager( temp, CONFIG_SOURCE, LOGGER );

            assertEquals( 7, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "Getting default value when missing but value is not is null" )
        void gettingDefaultValueWhenMissingButValueIsNotIsNull()
        {
            assertNull( configManager.getDefaultValue( "all-missing" ) );
        }

        @Test
        @DisplayName( "Getting the comments class attribute" )
        void gettingTheCommentsClassAttribute()
        {
            assertEquals( COMMENTS, configManager.getComments() );
        }

        @Test
        @DisplayName( "Getting the configSource class attribute" )
        void gettingTheConfigSourceClassAttribute()
        {
            assertEquals( CONFIG_SOURCE, configManager.getConfigSource() );
        }

        @Test
        @DisplayName( "Getting the logger class attribute" )
        void gettingTheLoggerClassAttribute()
        {
            assertEquals( LOGGER, configManager.getLogger() );
        }

        @Test
        @DisplayName( "Getting the missingValues class attribute" )
        void gettingTheMissingValuesClassAttribute()
        {
            assertFalse( configManager.isMissingValues() );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Setters Testing" )
    final class AttributeSettersTesting
    {

        // The ConfigManager object used for unit testing.
        private ConfigManager configManager;

        @BeforeEach
        void setUp()
        {
            this.configManager = new ConfigManager( CONFIG_VALUES, CONFIG_SOURCE, LOGGER );
        }

        @Test
        @DisplayName( "Setting the config values to null makes map empty" )
        void settingTheConfigValuesToNullMakesMapEmpty()
        {
            configManager.setValues( null );

            assertNull( configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting config values" )
        void settingConfigValues()
        {
            assertNull( configManager.getValue( "hello" ) );

            final HashMap< String, ConfigValue > temp = new HashMap<>();

            temp.put( "hello", new ConfigValue( 8.2, 0.0 ) );

            configManager.setValues( temp );

            assertEquals( 8.2, configManager.getValue( "hello" ) );
            assertEquals( 0.0, configManager.getDefaultValue( "hello" ) );
        }

        @Test
        @DisplayName( "Setting single config value to null" )
        void settingSingleConfigValueToNull()
        {
            configManager.setConfigValue( "int", null );

            assertNull( configManager.getValue( "int" ) );
            assertNull( configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting single config value" )
        void settingSingleConfigValue()
        {
            configManager.setConfigValue( "int", new ConfigValue( 1, 9 ) );

            assertEquals( 1, configManager.getValue( "int" ) );
            assertEquals( 9, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "setValue throws if param field is null" )
        void setValueThrowsIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setValue( null, null ) );
        }

        @Test
        @DisplayName( "Setting value changes value in map" )
        void settingValueChangesValueInMap()
        {
            assertEquals( 3, configManager.getValue( "int" ) );

            configManager.setValue( "int", 5 );

            assertEquals( 5, configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting value adds new field and value in map" )
        void settingValueAddsNewFieldAndValueInMap()
        {
            configManager.setValue( "missing-value", "yellow" );

            assertEquals( "yellow", configManager.getValue( "missing-value" ) );
            assertNull( configManager.getDefaultValue( "missing-value" ) );
        }

        @Test
        @DisplayName( "Transferring null makes all fields' values null" )
        void transferringNullMakesAllFieldsValuesNull()
        {
            configManager.transferValues( null );

            assertNull( configManager.getValue( "int" ) );
            assertNull( configManager.getValue( "string" ) );
            assertNull( configManager.getValue( "double" ) );
        }

        @Test
        @DisplayName( "Transferring config field values" )
        void transferringConfigFieldValues()
        {
            final HashMap< String, Object > values = new HashMap<>();

            values.put( "brand-new", 1 );
            values.put( "int", 5 );

            configManager.transferValues( values );

            assertEquals( 1, configManager.getValue( "brand-new" ) );
            assertNull( configManager.getDefaultValue( "brand-new" ) );

            assertEquals( 5, configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting default value throws exception if param field null" )
        void settingDefaultValueThrowsExceptionIfParamFieldNull()
        {
            TestingUtil.assertParameter( () -> configManager.setDefaultValue( null, null ) );
        }

        @Test
        @DisplayName( "Setting default value changes it in map" )
        void settingDefaultValueChangesItInMap()
        {
            configManager.setDefaultValue( "int", 10 );

            assertEquals( 10, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting default value adds new field and value in map" )
        void settingDefaultValueAddsNewFieldAndValueInMap()
        {
            configManager.setDefaultValue( "field-missing", "yellow" );

            assertEquals( "yellow", configManager.getDefaultValue( "field-missing" ) );
        }

        @Test
        @DisplayName( "Transferring null makes all fields' default values null" )
        void transferringNullMakesAllFieldsDefaultValuesNull()
        {
            configManager.transferDefaultValues( null );

            assertNull( configManager.getDefaultValue( "int" ) );
            assertNull( configManager.getDefaultValue( "string" ) );
            assertNull( configManager.getDefaultValue( "double" ) );
        }

        @Test
        @DisplayName( "Transferring config field default values" )
        void transferringConfigFieldDefaultValues()
        {
            final HashMap< String, Object > values = new HashMap<>();

            values.put( "new-value", 1 );
            values.put( "int", 5 );

            configManager.transferDefaultValues( values );

            assertEquals( 1, configManager.getDefaultValue( "new-value" ) );
            assertEquals( 1, configManager.getValue( "new-value" ) );

            assertEquals( 5, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "setComments throws if param comments is null" )
        void setCommentsThrowsIfParamCommentsIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setComments( null ) );
        }

        @Test
        @DisplayName( "Setting comments" )
        void settingComments()
        {
            configManager.setComments( COMMENTS );

            assertEquals( COMMENTS, configManager.getComments() );
        }

        @Test
        @DisplayName( "setConfigSource throws if param configSource is null" )
        void setConfigSourceThrowsIfParamConfigSourceIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setConfigSource( null ) );
        }

        @Test
        @DisplayName( "Setting config source" )
        void settingConfigSource()
        {
            configManager.setConfigSource( new ConfigSource( "config.yml" ) );

            assertEquals( "config.yml", configManager.getConfigSource().getSourcePath() );
        }

        @Test
        @DisplayName( "setLogger throws if param logger is null" )
        void setLoggerThrowsIfParamLoggerIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setLogger( null ) );
        }

        @Test
        @DisplayName( "Setting the logger" )
        void settingTheLogger()
        {
            final Logger logger = Logger.getLogger( "Test" );

            configManager.setLogger( logger );

            assertEquals( logger, configManager.getLogger() );
        }

        @Test
        @DisplayName( "Setting missing values" )
        void settingMissingValues()
        {
            configManager.setMissingValues( true );

            assertTrue( configManager.isMissingValues() );
        }

    }

    @Nested
    @DisplayName( "Logging Methods Testing" )
    final class LoggingMethodsTesting
    {

        // ConfigManager object used for testing the logging methods.
        private final ConfigManager CONFIG_MANAGER =
                new ConfigManager( CONFIG_VALUES, CONFIG_SOURCE, LOGGER );

        @Test
        @DisplayName( "logMessage throws if param level is null" )
        void logMessagesThrowsIfParamLevelIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMessage( null, "" ) );
        }

        @Test
        @DisplayName( "logMessage throws if param messages is null or empty" )
        void logMessageThrowsIfParamMessagesIsNullOrEmpty()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMessage( Level.INFO, null ) );
        }

        @Test
        @DisplayName( "logMissingValueWithReplacement throws if param field is null" )
        void logMissingValueWithReplacementThrowsIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValueWithReplacement( null, "", "" ) );
        }

        @Test
        @DisplayName( "logMissingValueWithReplacement throws if param replacement is null" )
        void logMissingValueWithReplacementThrowsIfParamReplacementIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValueWithReplacement( "",
                                                                                              null, "" ) );
        }

        @Test
        @DisplayName( "logMissingValueWithReplacement throws if param extraMessages is null" )
        void logMissingValueWithReplacementThrowsIfParamExtraMessagesIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValueWithReplacement( "",
                                                                                              "",
                                                                                              null ) );
        }

        @Test
        @DisplayName( "Logging missing value sets missing values to true" )
        void loggingMissingValueSetsMissingValuesToTrue()
        {
            CONFIG_MANAGER.setMissingValues( false );

            assertFalse( CONFIG_MANAGER.isMissingValues() );

            CONFIG_MANAGER.logMissingValueWithReplacement( "", "" );

            assertTrue( CONFIG_MANAGER.isMissingValues() );

            CONFIG_MANAGER.setMissingValues( false );

            assertFalse( CONFIG_MANAGER.isMissingValues() );

            CONFIG_MANAGER.logMissingValue( "" );

            assertTrue( CONFIG_MANAGER.isMissingValues() );
        }

        @Test
        @DisplayName( "logMissingValue throws if param field is null" )
        void logMissingValueThrowsIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValue( null, "" ) );
        }

        @Test
        @DisplayName( "logMissingValue throws if param extraMessages is null" )
        void logMissingValueThrowsIfParamExtraMessagesIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValue( "", null ) );
        }

    }

}