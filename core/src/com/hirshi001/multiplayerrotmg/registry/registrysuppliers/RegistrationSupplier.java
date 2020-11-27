package com.hirshi001.multiplayerrotmg.registry.registrysuppliers;

import com.hirshi001.multiplayerrotmg.registry.registration.Registration;

import java.util.function.Supplier;

public class RegistrationSupplier<T> extends Registration<Supplier<T>> {

    public RegistrationSupplier(Supplier<T> supplier) {
        super(supplier);
    }

    public T get(){
        return getObject().get();
    }
}
