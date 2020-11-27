package com.hirshi001.multiplayerrotmg.gamepieces.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.registrations.DisposableRegistry;
import io.netty.buffer.ByteBuf;

public class Sword extends ItemEntity {

    public static final Texture t = new Texture("rpg-pack/props_n_decorations/generic-rpg-loot01.png");
    private float bounceHeight = 0;
    private float bounceHeightLim = 5;
    private boolean bouncingUp = true;

    static{
        DisposableRegistry.addDisposable(t);
    }

    public Sword(Vector2 position, int id) {
        super(position, id);
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
    public void write(ByteBuf out) {
        super.write(out);
        out.writeFloat(bounceHeight);
        out.writeFloat(bounceHeightLim);
        out.writeBoolean(bouncingUp);
    }

    @Override
    public void read(ByteBuf in) {
        super.read(in);
        bounceHeight = in.readFloat();
        bounceHeightLim = in.readFloat();
        bouncingUp = in.readBoolean();
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
