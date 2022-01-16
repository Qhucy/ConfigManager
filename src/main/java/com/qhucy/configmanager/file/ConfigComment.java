package com.qhucy.configmanager.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Class that stores a comment's content and position in a config source file
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
@Setter
@Getter
@AllArgsConstructor
public class ConfigComment
{

    // The line number the comment is on.
    private int lineNumber;
    // The text content of the comment.
    @NonNull
    private String comment;

}