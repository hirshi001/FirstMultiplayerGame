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
    public abstract void update();

    /**
     * This method will not be called from the client side. Information for Entity updates will be sent through packets.
     * @param out
     */
    public abstract void write(ByteBuf out);

    /**
     * This method is called whenever this Entity Object is created. The ByteBuf should contain all the data needed to
     * fully and properly initialize this Object.
     * @param in
     */
    public abstract void read(ByteBuf in);

    @Override
    public int hashCode() {
        String
        return id;
    }
}
