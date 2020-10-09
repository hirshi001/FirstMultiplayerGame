package com.hirshi001.multiplayerrotmg.registry;

import java.util.ArrayList;

public class ExpandableRegistry<T> extends Registry<T> {

    private ArrayList<T> registrationList = new ArrayList<>();

    @Override
    public int register(T registration) {
        registrationList.add(registration);
        return registrationList.size()-1;
    }

    @Override
    public T getRegistration(int id) {
        return registrationList.get(id);
    }
}
