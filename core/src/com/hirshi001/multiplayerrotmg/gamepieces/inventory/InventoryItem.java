package com.hirshi001.multiplayerrotmg.gamepieces.inventory;

import com.badlogic.gdx.graphics.Texture;

public abstract class InventoryItem {

    public abstract Texture getImage();

    public abstract void useOne();

    public abstract void useTwo();

    public abstract int getId();

}
