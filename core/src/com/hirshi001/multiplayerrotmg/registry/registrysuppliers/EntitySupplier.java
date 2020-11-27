package com.hirshi001.multiplayerrotmg.registry.registrysuppliers;

import com.badlogic.gdx.math.Vector2;

public interface EntitySupplier<T> {
    public void get(Vector2 pos, int id);
}
