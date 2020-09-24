package com.hirshi001.gamepieces.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.gamepieces.items.ItemEntity;
import com.hirshi001.gamepieces.projecticles.Bullet;
import com.hirshi001.gamepieces.projecticles.GameProjectile;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;

import java.nio.ByteBuffer;

public class Player extends GameMob {

    public static final float WIDTH = 12f/ Block.BLOCKWIDTH, HEIGHT = 12f/Block.BLOCKHEIGHT;

    private int lastShot = 0;
    private int lastShotLim = 10;

    private boolean facingRight = true;

    //required for loading mobs
    public Player(){}

    public Player(Vector2 position){
        super(position);
    }

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void onItemTouching(ItemEntity i){ }

    private int count = 0;

    @Override
    public void update() {

    }

    @Override
    public GameMob applyDamage(int damage, GameProjectile source) {
        return this;
    }

    @Override
    protected void onMobCollision(GameMob e) {
        if(!(e instanceof Slime)){
            super.onMobCollision(e);
        }
    }

    @Override
    public String toString() {
        return "PLAYER at " + position.toString();
    }


    @Override
    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putFloat(getPosition().x);
        buffer.putFloat(getPosition().y);
        buffer.putInt(getHealth());
        return buffer.array();
    }

    @Override
    public void set(byte[] bytes) {
        ByteBuffer b = ByteBuffer.wrap(bytes);
        float x = b.getFloat();
        float y = b.getFloat();
        this.position.set(x,y);
        setHealth(b.getInt());
    }



}
