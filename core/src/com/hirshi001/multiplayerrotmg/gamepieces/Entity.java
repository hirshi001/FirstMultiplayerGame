package com.hirshi001.multiplayerrotmg.gamepieces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.field.Chunk;
import io.netty.buffer.ByteBuf;

import java.util.List;

public abstract class Entity {



    public Entity(Vector2 pos){

    }

    public abstract Vector2 getLayerPosition();
    public abstract void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b);

    public abstract Chunk getChunk();
    public abstract void setChunk(Chunk chunk);

    public abstract void write(ByteBuf out);
    public abstract void read(ByteBuf in);

}
