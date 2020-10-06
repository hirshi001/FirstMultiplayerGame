package com.hirshi001.multiplayerrotmg.registry;

import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;

public class DisposableRegistry {

    static ArrayList<Disposable> disposables;

    public static void addDisposable(Disposable d){
        disposables.add(d);
    }

    public static void dispose(){
        for(Disposable d: disposables){
            d.dispose();
        }
    }



}
