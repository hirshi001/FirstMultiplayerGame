package com.hirshi001.registry;

public class Block {

    public static final int BLOCKWIDTH = 16, BLOCKHEIGHT = 16;
    private boolean collidable;
    private final short id;

    public Block(short id){
        this.id = id;
    }

    public short getId(){
        return id;
    }

    public Block collidable(boolean collidable){
        this.collidable = collidable;
        return this;
    }

    public boolean isCollidable(){return collidable;}

}
