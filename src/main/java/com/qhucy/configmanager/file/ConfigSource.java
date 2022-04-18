package com.qhucy.configmanager.file;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.nio.file.Path;

/**
 * Object that stores the path and file source of where a config field
 * and value map was loaded from.
 *
 * @see com.qhucy.configmanager.ConfigManager
 * <p>
 * MIT License - Copyright (c) 2022 Qhucy Sijyo.
 */
@Getter
@RequiredArgsConstructor
public class ConfigSource
{

    /**
     * Returns a new ConfigSource object from a text path.
     *
     * @param sourcePath The text path of the config source.
     *
     * @return A new ConfigSource object from a text path.
     */
    @NonNull
    public static ConfigSource fromString( @NonNull final String sourcePath )
    {
        return new ConfigSource( sourcePath );
    }

    /**
     * Returns a new ConfigSource object from a path.
     *
     * @param sourcePath The path of the config source.
     *
     * @return A new ConfigSource object from a path.
     */
    @NonNull
    public static ConfigSource fromPath( @NonNull final Path sourcePath )
    {
        return new ConfigSource( sourcePath );
    }

    /**
     * Returns a new ConfigSource object from a file.
     *
     * @param sourceFile The config source file.
     *
     * @return A new ConfigSource object from a file.
     */
    @NonNull
    public static ConfigSource fromFile( @NonNull final File sourceFile )
    {
        return new ConfigSource( sourceFile );
    }

    // The text path that directs you to the source of where the
    // config field and value map was loaded from.
    @NonNull
    private String sourcePath;

    /**
     * Creates a ConfigSource from a path.
     *
     * @param sourcePath The path of the config source.
     */
    public ConfigSource( @NonNull final Path sourcePath )
    {
        setSourcePath( sourcePath );
    }

    /**
     * Creates a ConfigSource from a file.
     *
     * @param sourceFile The config source file.
     */
    public ConfigSource( @NonNull final File sourceFile )
    {
        setSourcePath( sourceFile );
    }

    /**
     * Sets the sourcePath from a text path.
     *
     * @param sourcePath The text path of the config source.
     */
    public void setSourcePath( @NonNull final String sourcePath )
    {
        this.sourcePath = sourcePath;
    }

    /**
     * Sets the sourcePath from a path.
     *
     * @param sourcePath The path of the config source.
     */
    public void setSourcePath( @NonNull final Path sourcePath )
    {
        this.sourcePath = sourcePath.toFile().getAbsolutePath();
    }

    /**
     * Sets the sourcePath from a file.
     *
     * @param sourceFile The config source file.
     */
    public void setSourcePath( @NonNull final File sourceFile )
    {
        this.sourcePath = sourceFile.getAbsolutePath();
    }

    /**
     * Returns the file located at the source path.
     *
     * @return The file located at the source path.
     */
    @NonNull
    public File getFileFromPath()
    {
        return new File( this.sourcePath );
    }

    /**
     * Returns if the source path leads to a file.
     *
     * @return If the source path leads to a file.
     */
    public boolean pathHasFile()
    {
        return getFileFromPath().exists() && getFileFromPath().isFile();
    }

}