package com.qhucy.configmanager;

import com.qhucy.configmanager.util.TestingUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit Testing for the ConfigManager class.
 *
 * @see ConfigManager
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
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

    // Random config source file path used for constructing a ConfigManager.
    private final static String CONFIG_SOURCE = "plugins/config.yml";
    // Basic logger used for constructing a ConfigManager.
    private final static Logger LOGGER        = Logger.getLogger( ConfigManagerTest.class.getName() );

    /**
     * Puts fields and values into the config value field and value maps.
     */
    @BeforeAll
    static void setUpAll()
    {
        CONFIG_VALUES.put( "int", new ConfigValue( 3, 7 ) );
        CONFIG_VALUES.put( "double", new ConfigValue( 2.5, 9.2 ) );
        CONFIG_VALUES.put( "string", new ConfigValue( "hello", "goodbye" ) );
        CONFIG_VALUES.put( "def", new ConfigValue( ErrorValue.MISSING, 1.5 ) );
        CONFIG_VALUES.put( "all-missing", new ConfigValue( ErrorValue.MISSING, ErrorValue.MISSING ) );
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
    }

    @Nested
    @DisplayName( "Static method buildConfigValueMapFromConfigValues Testing" )
    final class StaticMethodBuildConfigValueMapFromConfigValuesTesting
    {

        @Test
        @DisplayName( "Building empty map" )
        final void buildingEmptyMap()
        {
            assertThat( 0 );
            assertEquals( 0, ConfigManager.buildConfigValueMapFromConfigValues().size() );
            assertEquals( 0, ConfigManager.buildConfigValueMapFromConfigValues( null ).size() );
        }

        @Test
        @DisplayName( "Throws if odd number of args" )
        final void throwsIfOddNumberOfArgs()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigManager.buildConfigValueMapFromConfigValues( "field", new ConfigValue( 1, 2 ),
                                                                                   "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        final void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigManager.buildConfigValueMapFromConfigValues( 1, 2 ) );
        }

        @Test
        @DisplayName( "Throws if value is not ConfigValue" )
        final void throwsIfValueIsNotConfigValue()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigManager.buildConfigValueMapFromConfigValues( "field", 40 ) );
        }

        @Test
        @DisplayName( "Does not throw if value for ConfigValue is null" )
        final void doesNotThrowIfValueForConfigValueIsNull()
        {
            assertDoesNotThrow( () -> ConfigManager.buildConfigValueMapFromConfigValues( "field", null ) );
        }

        @Test
        @DisplayName( "Builds config field and value map correctly" )
        final void buildsConfigFieldAndValueMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigManager.buildConfigValueMapFromConfigValues( "a", null, "b", new ConfigValue( 1, 2 ) );

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
            assertEquals( 0, ConfigManager.buildConfigValueMapFromObjects().size() );
            assertEquals( 0, ConfigManager.buildConfigValueMapFromObjects( null ).size() );
        }

        @Test
        @DisplayName( "Throws if number of args not divisible by 3" )
        final void throwsIfNumberOfArgsNotDivisibleBy3()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigManager.buildConfigValueMapFromObjects( "field", 1, 2, "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        final void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigManager.buildConfigValueMapFromObjects( 1, 2, 3 ) );
        }

        @Test
        @DisplayName( "Builds config field and value map correctly" )
        final void buildsConfigFieldAndValueMapCorrectly()
        {
            final HashMap< String, ConfigValue > temp =
                    ConfigManager.buildConfigValueMapFromObjects( "a", null, null, "b", 1, 2 );

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
            assertEquals( 0, ConfigManager.buildConfigObjectMap().size() );
            assertEquals( 0, ConfigManager.buildConfigObjectMap( null ).size() );
        }

        @Test
        @DisplayName( "Throws if odd number of args" )
        final void throwsIfOddNumberOfArgs()
        {
            assertThrows( IllegalArgumentException.class,
                          () -> ConfigManager.buildConfigObjectMap( "field", 1, "another-field" ) );
        }

        @Test
        @DisplayName( "Throws if key is not String" )
        final void throwsIfKeyIsNotString()
        {
            assertThrows( IllegalArgumentException.class, () -> ConfigManager.buildConfigObjectMap( 1, 2 ) );
        }

        @Test
        @DisplayName( "Builds config object map correctly" )
        final void buildsConfigObjectMapCorrectly()
        {
            final HashMap< String, Object > temp = ConfigManager.buildConfigObjectMap( "a", null, "b", 3 );

            assertNull( temp.get( "a" ) );
            assertEquals( 3, temp.get( "b" ) );
        }

    }

    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception " )
        final void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new ConfigManager( CONFIG_VALUES, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( VALUES, DEFAULT_VALUES, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow(
                    () -> new ConfigManager( FIELDS, VALUES_LIST, DEFAULT_VALUES_LIST, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, VALUES_LIST, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( CONFIG_SOURCE, LOGGER, "field", 1, 2 ) );
        }

        @Test
        @DisplayName( "Instantiates if param values is null" )
        final void instantiatesIfParamValuesIsNull()
        {
            assertDoesNotThrow( () -> new ConfigManager( null, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( null, DEFAULT_VALUES, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, null, DEFAULT_VALUES_LIST, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, null, CONFIG_SOURCE, LOGGER ) );
        }

        @Test
        @DisplayName( "Instantiates if param defaultValues is null" )
        final void instantiatesIfParamDefaultValuesIsNull()
        {
            assertDoesNotThrow( () -> new ConfigManager( VALUES, null, CONFIG_SOURCE, LOGGER ) );
            assertDoesNotThrow( () -> new ConfigManager( FIELDS, VALUES_LIST, null, CONFIG_SOURCE, LOGGER ) );
        }

        @Test
        @DisplayName( "Throws exception if param configSource is null" )
        final void throwsExceptionIfParamConfigSourceIsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(), null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(), new HashMap<>(), null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null, null, null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null, null, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( null, LOGGER, "field", 1, 2 ) );
        }

        @Test
        @DisplayName( "Throws exception if param logger is null" )
        final void throwsExceptionIfParamLoggerIsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigManager( new HashMap<>(), CONFIG_SOURCE, null ) );
            TestingUtil.assertParameter(
                    () -> new ConfigManager( new HashMap<>(), new HashMap<>(), CONFIG_SOURCE, null ) );
            TestingUtil.assertParameter(
                    () -> new ConfigManager( new ArrayList<>(), null, null, CONFIG_SOURCE, null ) );
            TestingUtil.assertParameter( () -> new ConfigManager( new ArrayList<>(), null, CONFIG_SOURCE, null ) );
            TestingUtil.assertParameter( () -> new ConfigManager( CONFIG_SOURCE, null, "field", 1, 2 ) );
        }

        @Test
        @DisplayName( "Throws exception if param fields is null" )
        final void throwsExceptionIfParamFieldsIsNull()
        {
            TestingUtil.assertParameter(
                    () -> new ConfigManager( null, VALUES_LIST, DEFAULT_VALUES_LIST, CONFIG_SOURCE, LOGGER ) );
            TestingUtil.assertParameter( () -> new ConfigManager( null, VALUES_LIST, CONFIG_SOURCE, LOGGER ) );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Getters Testing" )
    final class AttributeGettersTesting
    {

        // ConfigManager object used for testing the class attribute getters.
        private ConfigManager configManager;

        @BeforeEach
        final void setUp()
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
        final void getConfigValueThrowsExceptionIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getConfigValue( null ) );
        }

        @Test
        @DisplayName( "Getting existing configValue" )
        final void gettingExistingConfigValue()
        {
            assertEquals( 3, configManager.getConfigValue( "int" ).getValue() );
            assertEquals( 7, configManager.getConfigValue( "int" ).getDefaultValue() );
        }

        @Test
        @DisplayName( "Getting non-existing configValue" )
        final void gettingNonExistingConfigValue()
        {
            assertNull( configManager.getConfigValue( "non-existing-field" ) );
        }

        @Test
        @DisplayName( "Getting config section fields throws exception if param parentField is null" )
        final void gettingConfigSectionFieldsThrowsExceptionIfParamParentFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getConfigSectionFields( null, false, false ) );
        }

        @Test
        @DisplayName( "Getting config section fields returns empty list if no nested fields" )
        final void gettingConfigSectionReturnsEmptyListIfNoNestedFields()
        {
            assertEquals( 0, configManager.getConfigSectionFields( "int", false, false ).size() );
        }

        @Test
        @DisplayName( "Getting config section fields returns nested fields" )
        final void gettingConfigSectionReturnsNestedFields()
        {
            assertEquals( 2, configManager.getConfigSectionFields( "nested", false, false ).size() );
            assertEquals( "deeper", configManager.getConfigSectionFields( "field.even.more", false, false ).get( 0 ) );
        }

        @Test
        @DisplayName( "Getting config section fields with full fields" )
        final void gettingConfigSectionFieldsWithFullFields()
        {
            assertEquals( "field.even.more.deeper",
                          configManager.getConfigSectionFields( "field.even.more", true, false ).get( 0 ) );
        }

        @Test
        @DisplayName( "Getting config section fields with non full fields" )
        final void gettingConfigSectionFieldsWithNonFullFields()
        {
            assertEquals( "deeper", configManager.getConfigSectionFields( "field.even.more", false, false ).get( 0 ) );
        }

        @Test
        @DisplayName( "Getting config section fields with deep fields" )
        final void gettingConfigSectionFieldsWithDeepFields()
        {
            assertEquals( 3, configManager.getConfigSectionFields( "field", false, true ).size() );

            assertTrue( configManager.getConfigSectionFields( "field", false, true ).contains( "deep" ) );
            assertTrue( configManager.getConfigSectionFields( "field", false, true ).contains( "even.deeper" ) );
            assertTrue( configManager.getConfigSectionFields( "field", false, true ).contains( "even.more.deeper" ) );
        }

        @Test
        @DisplayName( "Getting config section fields without deep fields" )
        final void gettingConfigSectionFieldsWithoutDeepFields()
        {
            assertEquals( 1, configManager.getConfigSectionFields( "field", false, false ).size() );

            assertTrue( configManager.getConfigSectionFields( "field", false, false ).contains( "deep" ) );
        }

        @Test
        @DisplayName( "Getting config section fields with full and deep fields" )
        final void gettingConfigSectionFieldsWithFullAndDeepFields()
        {
            assertEquals( 3, configManager.getConfigSectionFields( "field", true, true ).size() );

            assertTrue( configManager.getConfigSectionFields( "field", true, true ).contains( "field.deep" ) );
            assertTrue( configManager.getConfigSectionFields( "field", true, true ).contains( "field.even.deeper" ) );
            assertTrue(
                    configManager.getConfigSectionFields( "field", true, true ).contains( "field.even.more.deeper" ) );
        }

        @Test
        @DisplayName( "Getting config section fields does not include parent field" )
        final void gettingConfigSectionFieldsDoesNotIncludeParentField()
        {
            assertFalse( configManager.getConfigSectionFields( "nested", true, true ).contains( "nested" ) );
        }

        @Test
        @DisplayName( "Getting value throws exception if param field is null" )
        final void gettingValueThrowsExceptionIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getValue( null ) );
        }

        @Test
        @DisplayName( "Getting value from non-existent field is null" )
        final void gettingValueFromNonExistentFieldIsNull()
        {
            assertNull( configManager.getValue( "non-existent-field" ) );
        }

        @Test
        @DisplayName( "Getting value from field" )
        final void gettingValueFromField()
        {
            assertEquals( 3, configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Getting value from field goes to default if missing" )
        final void gettingValueFromFieldGoesToDefaultIfMissing()
        {
            assertEquals( 1.5, configManager.getValue( "def" ) );
        }

        @Test
        @DisplayName( "Getting value with both missing returns null" )
        final void gettingValueWithBothMissingReturnsNull()
        {
            assertNull( configManager.getValue( "all-missing" ) );
        }

        @Test
        @DisplayName( "Getting default value throws exception if param field is null" )
        final void gettingDefaultValueThrowsExceptionIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.getDefaultValue( null ) );
        }

        @Test
        @DisplayName( "Getting default value from non-existent field is null" )
        final void gettingDefaultValueFromNonExistentFieldIsNull()
        {
            assertNull( configManager.getDefaultValue( "non-existent-field" ) );
        }

        @Test
        @DisplayName( "Getting default value from field" )
        final void gettingDefaultValueFromField()
        {
            final HashMap< String, ConfigValue > temp = new HashMap<>();

            temp.put( "int", new ConfigValue( 3, 7 ) );

            final ConfigManager configManager = new ConfigManager( temp, CONFIG_SOURCE, LOGGER );

            assertEquals( 7, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "Getting default value when missing but value is not is null" )
        final void gettingDefaultValueWhenMissingButValueIsNotIsNull()
        {
            assertNull( configManager.getDefaultValue( "all-missing" ) );
        }

        @Test
        @DisplayName( "Getting the configSource class attribute" )
        final void gettingTheConfigSourceClassAttribute()
        {
            assertEquals( CONFIG_SOURCE, configManager.getConfigSource() );
        }

        @Test
        @DisplayName( "Getting the logger class attribute" )
        final void gettingTheLoggerClassAttribute()
        {
            assertEquals( LOGGER, configManager.getLogger() );
        }

        @Test
        @DisplayName( "Getting the missingValues class attribute" )
        final void gettingTheMissingValuesClassAttribute()
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
        final void setUp()
        {
            this.configManager = new ConfigManager( CONFIG_VALUES, CONFIG_SOURCE, LOGGER );
        }

        @Test
        @DisplayName( "Setting the config values to null makes map empty" )
        final void settingTheConfigValuesToNullMakesMapEmpty()
        {
            configManager.setConfigValues( null );

            assertNull( configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting config values" )
        final void settingConfigValues()
        {
            assertNull( configManager.getValue( "hello" ) );

            final HashMap< String, ConfigValue > temp = new HashMap<>();

            temp.put( "hello", new ConfigValue( 8.2, 0.0 ) );

            configManager.setConfigValues( temp );

            assertEquals( 8.2, configManager.getValue( "hello" ) );
            assertEquals( 0.0, configManager.getDefaultValue( "hello" ) );
        }

        @Test
        @DisplayName( "Setting single config value to null" )
        final void settingSingleConfigValueToNull()
        {
            configManager.setConfigValue( "int", null );

            assertNull( configManager.getValue( "int" ) );
            assertNull( configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting single config value" )
        final void settingSingleConfigValue()
        {
            configManager.setConfigValue( "int", new ConfigValue( 1, 9 ) );

            assertEquals( 1, configManager.getValue( "int" ) );
            assertEquals( 9, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "setValue throws if param field is null" )
        final void setValueThrowsIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setValue( null, null ) );
        }

        @Test
        @DisplayName( "Setting value changes value in map" )
        final void settingValueChangesValueInMap()
        {
            assertEquals( 3, configManager.getValue( "int" ) );

            configManager.setValue( "int", 5 );

            assertEquals( 5, configManager.getValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting value adds new field and value in map" )
        final void settingValueAddsNewFieldAndValueInMap()
        {
            configManager.setValue( "missing-value", "yellow" );

            assertEquals( "yellow", configManager.getValue( "missing-value" ) );
            assertNull( configManager.getDefaultValue( "missing-value" ) );
        }

        @Test
        @DisplayName( "Transferring null makes all fields' values null" )
        final void transferringNullMakesAllFieldsValuesNull()
        {
            configManager.transferValues( null );

            assertNull( configManager.getValue( "int" ) );
            assertNull( configManager.getValue( "string" ) );
            assertNull( configManager.getValue( "double" ) );
        }

        @Test
        @DisplayName( "Transferring config field values" )
        final void transferringConfigFieldValues()
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
        final void settingDefaultValueThrowsExceptionIfParamFieldNull()
        {
            TestingUtil.assertParameter( () -> configManager.setDefaultValue( null, null ) );
        }

        @Test
        @DisplayName( "Setting default value changes it in map" )
        final void settingDefaultValueChangesItInMap()
        {
            configManager.setDefaultValue( "int", 10 );

            assertEquals( 10, configManager.getDefaultValue( "int" ) );
        }

        @Test
        @DisplayName( "Setting default value adds new field and value in map" )
        final void settingDefaultValueAddsNewFieldAndValueInMap()
        {
            configManager.setDefaultValue( "field-missing", "yellow" );

            assertEquals( "yellow", configManager.getDefaultValue( "field-missing" ) );
        }

        @Test
        @DisplayName( "Transferring null makes all fields' default values null" )
        final void transferringNullMakesAllFieldsDefaultValuesNull()
        {
            configManager.transferDefaultValues( null );

            assertNull( configManager.getDefaultValue( "int" ) );
            assertNull( configManager.getDefaultValue( "string" ) );
            assertNull( configManager.getDefaultValue( "double" ) );
        }

        @Test
        @DisplayName( "Transferring config field default values" )
        final void transferringConfigFieldDefaultValues()
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
        @DisplayName( "setConfigSource throws if param configSource is null" )
        final void setConfigSourceThrowsIfParamConfigSourceIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setConfigSource( null ) );
        }

        @Test
        @DisplayName( "Setting config source" )
        final void settingConfigSource()
        {
            configManager.setConfigSource( "config.yml" );

            assertEquals( "config.yml", configManager.getConfigSource() );
        }

        @Test
        @DisplayName( "setLogger throws if param logger is null" )
        final void setLoggerThrowsIfParamLoggerIsNull()
        {
            TestingUtil.assertParameter( () -> configManager.setLogger( null ) );
        }

        @Test
        @DisplayName( "Setting the logger" )
        final void settingTheLogger()
        {
            final Logger logger = Logger.getLogger( "Test" );

            configManager.setLogger( logger );

            assertEquals( logger, configManager.getLogger() );
        }

        @Test
        @DisplayName( "Setting missing values" )
        final void settingMissingValues()
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
        private final ConfigManager CONFIG_MANAGER = new ConfigManager( CONFIG_VALUES, CONFIG_SOURCE, LOGGER );

        @Test
        @DisplayName( "logMessage throws if param level is null" )
        final void logMessagesThrowsIfParamLevelIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMessage( null, "" ) );
        }

        @Test
        @DisplayName( "logMessage throws if param messages is null or empty" )
        final void logMessageThrowsIfParamMessagesIsNullOrEmpty()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMessage( Level.INFO, null ) );
        }

        @Test
        @DisplayName( "logMissingValueWithReplacement throws if param field is null" )
        final void logMissingValueWithReplacementThrowsIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValueWithReplacement( null, "", "" ) );
        }

        @Test
        @DisplayName( "logMissingValueWithReplacement throws if param replacement is null" )
        final void logMissingValueWithReplacementThrowsIfParamReplacementIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValueWithReplacement( "", null, "" ) );
        }

        @Test
        @DisplayName( "logMissingValueWithReplacement throws if param extraMessages is null" )
        final void logMissingValueWithReplacementThrowsIfParamExtraMessagesIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValueWithReplacement( "", "", null ) );
        }

        @Test
        @DisplayName( "Logging missing value sets missing values to true" )
        final void loggingMissingValueSetsMissingValuesToTrue()
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
        final void logMissingValueThrowsIfParamFieldIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValue( null, "" ) );
        }

        @Test
        @DisplayName( "logMissingValue throws if param extraMessages is null" )
        final void logMissingValueThrowsIfParamExtraMessagesIsNull()
        {
            TestingUtil.assertParameter( () -> CONFIG_MANAGER.logMissingValue( "", null ) );
        }

    }

}