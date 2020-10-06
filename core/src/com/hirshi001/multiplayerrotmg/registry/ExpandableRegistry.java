package com.hirshi001.multiplayerrotmg.registry;

import java.util.ArrayList;

public class ExpandableRegistry<T> extends Registry<T> {

    private ArrayList<Class<? extends T>> registrationList = new ArrayList<>();

    @Override
    public int register(Class<? extends T> object) {
        registrationList.add(object);
        return registrationList.size()-1;
    }

    @Override
    public Class<? extends T> getObject(int id) {
        return registrationList.get(id);
    }
}
