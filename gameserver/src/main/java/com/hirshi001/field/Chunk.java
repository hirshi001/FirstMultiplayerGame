package com.hirshi001.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.gamepieces.items.ItemEntity;
import com.hirshi001.gamepieces.projecticles.GameProjectile;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;

import java.util.LinkedList;
import java.util.List;

public class Chunk {

    public static final int CHUNKSIZE = 16;
    private short[][] tiles;
    private int row, col;

    private final List<GameMob> mobs = new LinkedList<>();

    private final List<GameProjectile> projectiles = new LinkedList<>();

    private final List<ItemEntity> items = new LinkedList<>();

    public int unloadCount = 0;

    public Chunk(int row, int col, short[][] tiles){
        this.row = row;
        this.col = col;
        this.tiles = tiles;
    }

    public void addMob(GameMob m){ mobs.add(m);}
    public void removeMob(GameMob m) {mobs.remove(m);}
    public List<GameMob> getMobs(){return mobs;}

    public void addProjectile(GameProjectile p){ projectiles.add(p);}
    public void removeProjectile(GameProjectile p) {projectiles.remove(p);}
    public List<GameProjectile> getProjectiles(){return projectiles;}

    public void addItem(ItemEntity i){ items.add(i);}
    public void removeItem(ItemEntity i) {items.remove(i);}
    public List<ItemEntity> getItems(){return items;}

    public short[][] getTiles() {
        return tiles;
    }

    public Chunk setTiles(short[][] tiles) {
        this.tiles = tiles;
        return this;
    }

    public int getRow() {
        return row;
    }

    public Chunk setRow(int row) {
        this.row = row;
        return this;
    }

    public int getCol() {
        return col;
    }

    public Chunk setCol(int col) {
        this.col = col;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        Chunk c = (Chunk)obj;
        return c.getCol()==getCol() && c.getRow() == getRow();
    }


    @Override
    public String toString() {
        return super.toString() + " : row = " + row + ", col = " + col;
    }

}
