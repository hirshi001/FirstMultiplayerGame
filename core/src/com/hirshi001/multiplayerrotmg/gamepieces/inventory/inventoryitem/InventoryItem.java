package com.hirshi001.multiplayerrotmg.gamepieces.inventory.inventoryitem;

import com.badlogic.gdx.graphics.Texture;

public abstract class InventoryItem {

    private final int id;

    public InventoryItem(int id){
        this.id = id;
    }

    public abstract Texture getImage();

    public abstract void useOne();

    public abstract void useTwo();

    public final int getId(){
        return id;
    }


}
