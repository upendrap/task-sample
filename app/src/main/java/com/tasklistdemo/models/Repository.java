package com.tasklistdemo.models;

import java.util.List;

/**
 * Created by upenp on 7/23/2017.
 * The basic template for a repository with CRUD capability.
 */

public interface Repository<T, U extends QuerySpecs> {
    public void add(T data);

    public void add(List<T> data);

    public List<T> query(U specs);

    public void update(T data);

    public void remove(T data);

    public void remove(U specs);

}
