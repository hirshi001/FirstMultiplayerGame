package com.hirshi001.multiplayerrotmg.registry;

import com.hirshi001.multiplayerrotmg.gamepieces.Entity;


/**
 *
 * @param <T>
 *     This object is used for registering objects
 */
public abstract class Registry<T> {

    /**
     *
     * @param object
     * @return the id of the Object registered which is provided by the Registry
     */
    public abstract int register(T object);

    /**
     *
     * @param id
     * @return the object in the registry with the corresponding id
     */
    public abstract T getObject(int id);

}
