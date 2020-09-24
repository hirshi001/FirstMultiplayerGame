package com.hirshi001.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.registry.Block;

import java.nio.ByteBuffer;

public class Bullet extends GameProjectile {

    public static final Texture t = new Texture("textures/entities/projectiles/bullet/bullet.png");
    private Vector2 angle;
    private float speed = 0.5f;
    private int lifeSpan = 200;
    private int life = 0;

    public Bullet(){}
    public Bullet(Vector2 position, Vector2 dir) {
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
        this.position = new Vector2(x,y);

        float ax = buffer.getFloat();
        float ay = buffer.getFloat();
        this.angle = new Vector2(ax,ay);

        life = buffer.getInt();

    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/ Block.BLOCKHEIGHT;
    }
}
