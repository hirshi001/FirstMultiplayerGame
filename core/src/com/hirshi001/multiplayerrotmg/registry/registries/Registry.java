package com.hirshi001.multiplayerrotmg.registry.registries;

import com.hirshi001.multiplayerrotmg.gamepieces.Entity;


/**
 *
 * This object is used for registering objects
 * @param <T>
 */
public abstract class Registry<T> {

    /**
     *
     * @param object
     * @return the id of the registered object
     */
    public abstract int register(T object);

    /**
     *
     * @param id
     * @return T the object in the registry with the corresponding id
     */
    public abstract T getRegistration(int id);

}
