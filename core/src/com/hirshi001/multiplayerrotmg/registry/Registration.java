package com.hirshi001.multiplayerrotmg.registry;

public class Registration<T>{

    public static <E> Registration<E> registerObject(ObjectCreator<E> ec){
        return new Registration<E>(ec);
    }


    private ObjectCreator<T> ec;
    private int id;

    public Registration(ObjectCreator<T> ec){
        this.ec = ec;
    }

    public ObjectCreator<T> getObjectCreator(){
        return ec;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public interface ObjectCreator<P>{

        public P create();
    }

}
