package com.hirshi001.multiplayerrotmg.registry.registration;

import java.util.function.Supplier;

public class Registration<T>{

    private T object;
    private int id;

    public Registration(T object){
        this.object = object;
    }

    public T getObject(){
        return object;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }


}
