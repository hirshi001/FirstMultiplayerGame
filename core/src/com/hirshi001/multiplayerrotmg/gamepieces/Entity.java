package com.hirshi001.multiplayerrotmg.gamepieces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.field.Chunk;
import io.netty.buffer.ByteBuf;

import java.util.List;

public abstract class Entity {

    private Vector2 pos;

    private int id;

    public Entity(Vector2 pos, int id){ this.pos = pos; this.id = id;}

    public Vector2 getPosition(){return pos;}

    public Vector2 getLayerPosition(){
        return pos;
    }
    public abstract void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b);

    public abstract Chunk getChunk();
    public abstract void setChunk(Chunk chunk);

    /**
     * When called on the client side, this method generally wont have any functionaliy
     */
    public abstract void updateTick();

    /**
     * Writes to a ByteBuf all the necessary information needed to load the entity.
     * This method will not be called from the client side.
     * @param out
     */
    public abstract void write(ByteBuf out);

    /**
     * This method is called whenever a chunk is loaded which contains an entity.
     * This method will not be called from the client side.
     * @param in
     */
    public abstract void read(ByteBuf in);

    /**
     * updates the entity based on the bits in the array.
     * @param in
     */
    public void update(ByteBuf in){}



    public int getId() {
        return id;
    }
}
