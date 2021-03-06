package com.hirshi001.multiplayerrotmg.gamepieces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.field.Chunk;
import com.hirshi001.multiplayerrotmg.util.bytewritable.ByteWritable;
import com.hirshi001.multiplayerrotmg.util.bytewritable.ByteWritableMap;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Map;

public abstract class Entity {

    protected Map<String, ByteWritable> entityData;

    private Vector2 pos;

    private int id;

    private ByteWritableMap data;

    public Entity(Vector2 pos, int id){ this.pos = pos; this.id = id;}

    public Vector2 getPosition(){return pos;}

    public Vector2 getLayerPosition(){
        return pos;
    }
    public abstract void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b);

    public abstract Chunk getChunk();
    public abstract void setChunk(Chunk chunk);

    public ByteWritableMap getData(){
        return data;
    }

    /**
     * When called on the client side, this method generally wont have any functionality.
     * This is the only "Update" method that will be called by the game system internally (field/chunk).
     * Different subclasses may implement this style of this method differently, perhaps creating other update methods.
     */
    public void tick(){
    }

    /**
     * This method should be called after all the calculations in the tick.
     */
    public void updateData(){
        updateData(data);
    }

    /**
     * Used for writing the information of an entity to a map in which the updated information is sent from the server
     * to client through packets. This is not used for sending updates from client to server, which may be needed for
     * updating player data.
     * @param data
     */
    protected void updateData(ByteWritableMap data){
        data.putFloat("x",pos.x);
        data.putFloat("y", pos.y);
    }

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
     * called when a
     * @param in
     */
    public void update(ByteBuf in){}

    /**
     *
     * @param e
     * @return true if the provided entity is touching this entity.
     * This relationship may not necessarily go both ways. That is:
     * Entity1.touchingEntity(Entity2) may return true, but
     * Entity2.touchingEntity(Entity1) is not also guaranteed to return true
     * and vice versa.
     */
    public abstract boolean touchingEntity(Entity e);


    /**
     *
     * @return the id of this entity. Each entity should have a unique id. This could also be seen as
     * the hash code of the entity, however that method is not implemented.
     */
    public int getId() {
        return id;
    }



    ///EVENTS
    public void onTouchingEntity(Entity e){}
}
