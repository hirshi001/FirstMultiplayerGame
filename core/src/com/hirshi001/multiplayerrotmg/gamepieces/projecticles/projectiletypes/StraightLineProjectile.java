package com.hirshi001.multiplayerrotmg.gamepieces.projecticles.projectiletypes;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.GameProjectile;
import io.netty.buffer.ByteBuf;

public abstract class StraightLineProjectile extends GameProjectile {

    private Vector2 angle;
    private float speed;
    private int lifespan;
    private int life;

    public StraightLineProjectile() {
    }

    public StraightLineProjectile(Vector2 position, Vector2 angle, float speed, int lifespan, int life) {
        super(position);
        this.angle = angle.nor().scl(speed);
        this.speed = speed;
        this.lifespan = lifespan;
        this.life = life;
    }


    public Vector2 getAngle() {
        return angle;
    }

    public StraightLineProjectile setAngle(Vector2 angle) {
        this.angle = angle;
        return this;
    }

    public float getSpeed() {
        return speed;
    }

    public StraightLineProjectile setSpeed(float speed) {
        this.speed = speed;
        return this;
    }

    public int getLifespan() {
        return lifespan;
    }

    public StraightLineProjectile setLifespan(int lifespan) {
        this.lifespan = lifespan;
        return this;
    }

    public int getLife() {
        return life;
    }

    public StraightLineProjectile setLife(int life) {
        this.life = life;
        return this;
    }

    @Override
    public void update() {
        getPosition().add(angle);
    }

}

