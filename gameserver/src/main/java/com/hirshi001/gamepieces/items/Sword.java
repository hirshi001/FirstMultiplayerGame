package com.hirshi001.gamepieces.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;

import java.nio.ByteBuffer;

public class Sword extends ItemEntity {

    public static final Texture t = new Texture("rpg-pack/props_n_decorations/generic-rpg-loot01.png");

    public Sword(){}
    public Sword(Vector2 position) {
        super(position);
    }

    @Override
    public void onDropped(GameMob e) {

    }

    @Override
    public void onPicked(GameMob e) {

    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/ Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }

    @Override
    public byte[] toByteArray() {
        ByteBuffer b = ByteBuffer.allocate(8);
        b.putFloat(getPosition().x);
        b.putFloat(getPosition().y);
        return b.array();
    }

    @Override
    public void set(byte[] bytes) {
        ByteBuffer b = ByteBuffer.wrap(bytes);
        float x = b.getFloat();
        float y = b.getFloat();
        this.position.set(x,y);
    }

    @Override
    public void updateBoxEntity() {

    }
}
