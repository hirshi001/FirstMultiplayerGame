package com.hirshi001.multiplayerrotmg.registry;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * @param <T>
 *     Used for wrapping the initialization method of an Entity type for registrating in a Registry
 */
public class EntityRegistration<T>{

    public static <E> EntityRegistration<E> registerEntity(EntityCreator<E> ec){
        return new EntityRegistration<E>(ec);
    }

    private EntityCreator<T> ec;

    public EntityRegistration(EntityCreator<T> ec){
        this.ec = ec;
    }

    public EntityCreator<T> getEntityCreator(){
        return ec;
    }



    public interface EntityCreator<P>{
        public P create(Vector2 pos);
    }

}
