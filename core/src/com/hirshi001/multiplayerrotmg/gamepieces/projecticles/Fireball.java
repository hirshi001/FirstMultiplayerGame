package com.hirshi001.multiplayerrotmg.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.projectiletypes.StraightLineProjectile;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.DisposableRegistry;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import com.hirshi001.multiplayerrotmg.registry.Registry;

public class Fireball extends StraightLineProjectile {


    public static final Texture t = new Texture("textures/entities/projectiles/fireball/fireball.png");

    static{
        DisposableRegistry.addDisposable(t);
    }


   // public Fireball(){}
    public Fireball(Vector2 position) {
        super(position);
        setSpeed(0.5f);
    }


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
