package com.hirshi001.multiplayerrotmg.gamepieces.inventory;

import com.hirshi001.multiplayerrotmg.gamepieces.inventory.inventoryitem.InventoryItem;

public class Inventory {

    private InventoryItem[] items;

    private InventoryItem defaultItem;

    public InventoryItem getItem(int index){
        return items[index]==null?defaultItem:items[index];
    }

    public static final int DEFAULT_SIZE = 9;

    public Inventory(){
        this(DEFAULT_SIZE);
    }

    public Inventory(int size){
        items = new InventoryItem[size];
    }

    public Inventory setItem(InventoryItem item, int index){
        items[index] = item;
        return this;
    }

    public InventoryItem[] getItems(){
        return items;
    }

    public int size(){return items.length;}




}
