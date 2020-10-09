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
     * @return the same object which was passed
     */
    public abstract Registration<? extends T> register(Registration<? extends T> object);

    /**
     *
     * @param id
     * @return the object in the registry with the corresponding id
     */
    public abstract Registration<? extends T> getRegistration(int id);

}
