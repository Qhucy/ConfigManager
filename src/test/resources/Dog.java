package com.qhucy.configmanager.resources;

import com.qhucy.configmanager.file.ConfigSavableLoadable;
import com.qhucy.configmanager.file.ConfigSavePriority;
import org.jetbrains.annotations.NotNull;

/**
 * Generic class object used for unit testing saving and loading config objects to and from files
 * respectively.
 * <p>
 * MIT License - Copyright (c) 2021 Qhucy Sijyo.
 */
public final class Dog
        implements ConfigSavableLoadable
{

    private String id;
    private String name;
    private int    age;
    private Sex    sex;

    public Dog( String id, String name, int age, Sex sex )
    {
        this.id   = id;
        this.name = name;
        this.age  = age;
        this.sex  = sex;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public int getAge()
    {
        return age;
    }

    public void setAge( int age )
    {
        this.age = age;
    }

    public Sex getSex()
    {
        return sex;
    }

    public void setSex( Sex sex )
    {
        this.sex = sex;
    }

    @Override
    public void saveAsConfigObjectToFile( @NotNull ConfigSavePriority configSavePriority )
    {

    }

    @Override
    public void saveAsConfigObjectToFile()
    {

    }

    @Override
    public void loadAsConfigObjectFromFile( @NotNull ConfigSavePriority configSavePriority )
    {

    }

    @Override
    public void loadAsConfigObjectFromFile()
    {

    }

    @Override
    public String toString()
    {
        return String.format( "Dog('%s', '%s', %s, %s)", name, id, age, sex.toString() );
    }

}