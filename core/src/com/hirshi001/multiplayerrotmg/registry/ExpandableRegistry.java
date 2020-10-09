package com.hirshi001.multiplayerrotmg.registry;

import java.util.ArrayList;

public class ExpandableRegistry<T> extends Registry<T> {

    private ArrayList<Registration<? extends T>> registrationList = new ArrayList<>();

    @Override
    public Registration<? extends T> register(Registration<? extends T> registration) {
        registrationList.add(registration);
        return registration;
    }

    @Override
    public Registration<? extends T> getRegistration(int id) {
        return registrationList.get(id);
    }
}
