package com.qhucy.configmanager.util;

import lombok.NonNull;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Static utility method class for JUnit Testing.
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
public final class TestingUtil
{

    /**
     * Short handed assertThrows method call but with NullPointerException as the default
     * first argument and a default message argument.
     *
     * @param executable The executable code that should throw an NullPointerException.
     */
    public static void assertParameter( @NonNull final Executable executable )
    {
        assertThrows( NullPointerException.class, executable, "Expected NullPointerException to be thrown" );
    }

}