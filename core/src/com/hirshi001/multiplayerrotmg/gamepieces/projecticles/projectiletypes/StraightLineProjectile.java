package com.hirshi001.multiplayerrotmg.gamepieces.projecticles.projectiletypes;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import io.netty.buffer.ByteBuf;

public abstract class StraightLineProjectile extends ProjectileEntity {

    private Vector2 angle;
    private float speed;
    private int lifespan;
    private int life;

    public StraightLineProjectile(Vector2 position, int id) {
        super(position, id);
    }

    public StraightLineProjectile setAngleSpeed(Vector2 angle, float speed){
        this.angle = angle.nor().scl(speed);
        this.speed = speed;
        return this;
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
    public void write(ByteBuf out) {
        super.write(out);
        out.writeFloat(angle.x);
        out.writeFloat(angle.y);
        out.writeFloat(speed);
        out.writeInt(lifespan);
        out.writeInt(life);
    }

    @Override
    public void read(ByteBuf in) {
        super.read(in);
        setAngleSpeed(new Vector2(in.readFloat(), in.readFloat()), in.readFloat());
        setLifespan(in.readInt());
        setLife(in.readInt());
    }

    @Override
    public void tick() {
        getPosition().add(angle);
    }

}

