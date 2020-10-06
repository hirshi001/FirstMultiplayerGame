package com.hirshi001.multiplayerrotmg.gamepieces.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;

public class Sword extends ItemEntity {

    public static int id;

    @Override
    public int getId() {
        return id;
    }

    public static final Texture t = new Texture("rpg-pack/props_n_decorations/generic-rpg-loot01.png");
    private float bounceHeight = 0;
    private float bounceHeightLim = 5;
    private boolean bouncingUp = true;

    static{
        EntityRegistry.addDisposable(t);
    }

    public Sword(){}
    public Sword(Vector2 position) {
        super(position);
    }

    @Override
    public void onDropped(MobEntity e) {

    }

    @Override
    public void onPicked(MobEntity e) {

    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }

    @Override
    public void drawItem(SpriteBatch batch) {
        batch.draw(t,getPosition().x* Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT+bounceHeight);
    }

    @Override
    public void set(ByteBuf buffer) {
        float x = buffer.readFloat();
        float y = buffer.readFloat();
        this.position.set(x,y);
    }

    @Override
    public void updateBoxEntity() {
        float d = (float)Math.random()/10;
        if(bouncingUp){
            bounceHeight+=0.2+d;
            if(bounceHeight>=bounceHeightLim){
                bouncingUp=false;
            }
        }
        else{
            bounceHeight-=0.2+d;
            if(bounceHeight<=0){
                bouncingUp=true;
            }
        }
    }
}
