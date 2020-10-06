package com.hirshi001.multiplayerrotmg.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.projectiletypes.StraightLineProjectile;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;

public class Fireball extends StraightLineProjectile {

    private static float speed = 0.3f;
    private static int lifespan = 100;

    public static final Texture t = new Texture("textures/entities/projectiles/fireball/fireball.png");

    private int life = 0;
    static{
        EntityRegistry.addDisposable(t);
    }


   // public Fireball(){}
    public Fireball(Vector2 position, Vector2 dir) {
        super(position, dir, speed, lifespan, 0);
    }


    @Override
    public void update(){}

    @Override
    public void drawProjectile(SpriteBatch batch) {
        batch.draw(t,getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT);
    }

    @Override
    public void onTouchingMob(MobEntity m) {  }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }
}
