package com.hirshi001.multiplayerrotmg.registry;

public abstract class Registry<T> {

    /**
     *
     * @param id id of the object which is being registered. Each object should have a unique id within a registry
     *           ie: a registry should not have objects with duplicate ids
     * @param object
     * @return true if the object was succesfully registered, false if it was not registered properly, possibly because
     * of a duplicate id, space restraints, or some other internal problem
     */
    public abstract boolean register(int id, T object);

    /**
     *
     * @param id
     * @return the object in the registry with the corresponding id
     */
    public abstract T getObject(int id);

}
