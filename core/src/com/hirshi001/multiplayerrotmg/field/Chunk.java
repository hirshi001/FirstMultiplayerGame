package com.hirshi001.multiplayerrotmg.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hirshi001.multiplayerrotmg.gamepieces.Entity;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import com.hirshi001.multiplayerrotmg.registry.BlockRegistry;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Chunk {

    public static final int CHUNKSIZE = 16;
    private short[][] tiles;
    private int row, col;


    private final HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();

    public int unloadCount = 0;

    public Chunk(int row, int col, short[][] tiles){
        this.row = row;
        this.col = col;
        this.tiles = tiles;
    }

    public void drawTiles(SpriteBatch batch){
        int r, c;
        int chunkPosRow = row*CHUNKSIZE*Block.BLOCKHEIGHT;
        int chunkPosCol = col*CHUNKSIZE*Block.BLOCKWIDTH;

        for(r=0;r<tiles.length;r++){
            for(c=0;c<tiles.length;c++){
                batch.draw(BlockRegistry.BLOCK_REGISTRY.getRegistration(tiles[r][c]).getTexture(),chunkPosCol + c  * Block.BLOCKHEIGHT,chunkPosRow+ r * Block.BLOCKHEIGHT);
            }
        }
    }


    public void addEntity(Entity e){ entities.put(e.getId(), e); }
    public void removeEntity(Entity e) { entities.remove(e.getId()); }
    public Map<Integer, Entity> getEntitiesMap(){return entities;}

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
