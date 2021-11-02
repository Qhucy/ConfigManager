package com.qhucy.configmanager.util;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Static utility method class for JUnit Testing.
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public final class TestingUtil
{

    /**
     * Short handed assertThrows method call but with IllegalArgumentException as the default
     * first argument and a default message argument.
     *
     * @param executable The executable code that should throw an IllegalArgumentException.
     */
    public static void assertParameter( @NotNull final Executable executable )
    {
        Validate.notNull( executable, "Parameter executable cannot be null." );

        assertThrows( IllegalArgumentException.class, executable,
                      "Expected " + "IllegalArgumentException to be thrown" );
    }

}