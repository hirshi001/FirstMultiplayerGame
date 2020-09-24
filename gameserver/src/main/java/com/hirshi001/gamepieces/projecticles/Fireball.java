package com.hirshi001.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;

import java.nio.ByteBuffer;

public class Fireball extends GameProjectile {

    public static final Texture t = new Texture("textures/entities/projectiles/fireball/fireball.png");
    private Vector2 angle;
    private float speed = 0.3f;
    private int lifeSpan = 100;
    private int life = 0;
    public Fireball(){}
    public Fireball(Vector2 position, Vector2 dir) {
        super(position);
        this.angle = dir.nor().scl(speed);
    }

    @Override
    public void update() {
        getPosition().add(angle);
        life++;
        if(life>lifeSpan){
            getField().removeProjectile(this);
        }
    }

    @Override
    public void onTouchingMob(GameMob m) {
        if(m==getSource()) return;
        getPosition().sub(angle);
        angle.set(getCenterPosition().sub(m.getCenterPosition())).nor().scl(speed);
        getPosition().add(angle);
        m.applyDamage(5, this);
    }


    @Override
    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(20);
        buffer.putFloat(getPosition().x);
        buffer.putFloat(getPosition().y);
        buffer.putFloat(angle.x);
        buffer.putFloat(angle.y);
        buffer.putInt(life);
        return buffer.array();
    }

    @Override
    public void set(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        float x = buffer.getFloat();
        float y = buffer.getFloat();
        float ax = buffer.getFloat();
        float ay = buffer.getFloat();
        this.position = new Vector2(x,y);
        this.angle = new Vector2(ax,ay);
        life = buffer.getInt();

    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/ Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }
}
