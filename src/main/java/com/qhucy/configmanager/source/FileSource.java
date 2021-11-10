package com.qhucy.configmanager.source;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * Object that stores information about a source file of a config field and value map.
 *
 * @see ConfigSource
 * @see com.qhucy.configmanager.ConfigManager
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public class FileSource
        extends ConfigSource
{

    /**
     * Returns a new FileSource object from a given file.
     *
     * @param file The file to create the FileSource object from.
     * @return A new FileSource object from a given file.
     */
    @NotNull
    public static FileSource fromFile( @NotNull final File file )
    {
        return new FileSource( file );
    }

    // The source file object where the config field and value map was loaded from.
    private File sourceFile;

    /**
     * Instantiates a FileSource given source file.
     *
     * @param sourceFile The source file.
     */
    public FileSource( @NotNull final File sourceFile )
    {
        super( sourceFile.getAbsolutePath() );

        setSourceFile( sourceFile );
    }

    /**
     * Returns the source file object where the config field and value map was loaded from.
     *
     * @return The source file object where the config field and value map was loaded from.
     */
    @NotNull
    public File getSourceFile()
    {
        return sourceFile;
    }

    /**
     * Sets a new source file and source file text path.
     *
     * @param sourceFile The new source file.
     */
    public void setSourceFile( @NotNull final File sourceFile )
    {
        Validate.notNull( sourceFile, "Parameter sourceFile cannot be null." );

        this.sourceFile = sourceFile;
        super.setSource( sourceFile.getAbsolutePath() );
    }

}