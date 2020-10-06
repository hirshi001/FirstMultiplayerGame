package com.hirshi001.multiplayerrotmg.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.projectiletypes.StraightLineProjectile;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;

public class Bullet extends StraightLineProjectile {



    public static final Texture t = new Texture("textures/entities/projectiles/bullet/bullet.png");
    static{
        //EntityRegistry.addDisposable(t);
    }

    public Bullet(Vector2 position) {
        super(position);
        setSpeed(0.6f);
    }

    @Override
    public void drawProjectile(SpriteBatch batch) {
        //batch.draw(t,getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT);
        batch.draw(t,getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT, t.getWidth()/2, t.getHeight()/2, t.getWidth(), t.getHeight(), 1, 1, getAngle().angle()+180,0,0,t.getWidth(), t.getHeight(),false,false);
    }

    @Override
    public void onTouchingMob(MobEntity m) {
        if(m==getSource()) return;
        m.applyDamage(5, this);
    }

    @Override
    public float getWidth() {
        return (float)t.getWidth()/Block.BLOCKWIDTH;
    }

    @Override
    public float getHeight() {
        return (float)t.getHeight()/Block.BLOCKHEIGHT;
    }
}
