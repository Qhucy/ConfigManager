package com.qhucy.configmanager.file;

import com.qhucy.configmanager.util.TestingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit Testing for the ConfigComment class.
 *
 * @see ConfigComment
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@DisplayName( "ConfigComment Class Testing" )
final class ConfigCommentTest
{

    // The default value for the line number for unit tests.
    private final static int    LINE_NUMBER = 1;
    // The default value for the string comment for unit tests.
    private final static String COMMENT     = "# This is a YAML config file.";

    @Nested
    @DisplayName( "Constructor Testing" )
    final class ConstructorTest
    {

        @Test
        @DisplayName( "Instantiates with no exception" )
        void instantiatesWithNoException()
        {
            assertDoesNotThrow( () -> new ConfigComment( LINE_NUMBER, COMMENT ) );
        }

        @Test
        @DisplayName( "Does not instantiate if required params are null" )
        void doesNotInstantiateIfReqParamsNull()
        {
            TestingUtil.assertParameter( () -> new ConfigComment( LINE_NUMBER, null ) );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Getters Testing" )
    final class AttributeGettersTesting
    {

        // ConfigComment object used for testing the class attribute getters.
        private ConfigComment ConfigComment;

        @BeforeEach
        void setUp()
        {
            ConfigComment = new ConfigComment( LINE_NUMBER, COMMENT );
        }

        @Test
        @DisplayName( "lineNumber" )
        void getLineNumber()
        {
            assertEquals( LINE_NUMBER, ConfigComment.getLineNumber() );
        }

        @Test
        @DisplayName( "comment" )
        void getComment()
        {
            assertEquals( COMMENT, ConfigComment.getComment() );
        }

    }

    @Nested
    @DisplayName( "Class Attribute Setters Testing" )
    final class AttributeSettersTesting
    {

        // The ConfigComment object used for unit testing.
        private ConfigComment configComment;

        @BeforeEach
        void setUp()
        {
            this.configComment = new ConfigComment( LINE_NUMBER, COMMENT );
        }

        @Test
        @DisplayName( "Throws exception if setting comment to null" )
        void throwsExceptionIfSettingCommentToNull()
        {
            TestingUtil.assertParameter( () -> configComment.setComment( null ) );
        }

        @Test
        @DisplayName( "Setting lineNumber" )
        void settingLineNumber()
        {
            assertEquals( LINE_NUMBER, configComment.getLineNumber() );

            configComment.setLineNumber( 10 );
            assertEquals( 10, configComment.getLineNumber() );
        }

        @Test
        @DisplayName( "Setting comment" )
        void settingComment()
        {
            assertEquals( COMMENT, configComment.getComment() );

            configComment.setComment( "# This is a new comment." );
            assertEquals( "# This is a new comment.", configComment.getComment() );
        }

    }

}