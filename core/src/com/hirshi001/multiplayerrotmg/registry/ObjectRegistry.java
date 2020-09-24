package com.hirshi001.multiplayerrotmg.registry;

import com.hirshi001.multiplayerrotmg.util.identifiable.IdentifiableObject;

import java.util.ArrayList;
import java.util.List;

public class ObjectRegistry<E extends IdentifiableObject> {

    public List<E> objects = new ArrayList<>();

    public ObjectRegistry(){

    }

    public void registerObject(E e){
        e.setId(objects.size());
        objects.add(e);
    }

    public E getObject(int id){
        return objects.get(id);
    }





}
