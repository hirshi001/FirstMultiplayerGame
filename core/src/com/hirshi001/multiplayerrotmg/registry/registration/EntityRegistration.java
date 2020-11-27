package com.hirshi001.multiplayerrotmg.registry.registration;

import com.hirshi001.multiplayerrotmg.registry.registrysuppliers.EntitySupplier;

public class EntityRegistration<T> extends Registration<EntitySupplier<T>>{
    public EntityRegistration(EntitySupplier<T> supplier) {
        super(supplier);
    }
}
