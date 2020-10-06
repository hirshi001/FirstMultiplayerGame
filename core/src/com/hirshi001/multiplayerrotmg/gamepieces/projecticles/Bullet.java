package com.hirshi001.multiplayerrotmg.gamepieces.projecticles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.projectiletypes.StraightLineProjectile;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;

public class Bullet extends StraightLineProjectile {

    private static float speed = 0.5f;
    private static int lifeSpan = 200;
    private static int life = 0;


    public static final Texture t = new Texture("textures/entities/projectiles/bullet/bullet.png");
    static{
        EntityRegistry.addDisposable(t);
    }

    public Bullet(){}
    public Bullet(Vector2 position, Vector2 dir) {
        super(position, dir, speed, lifeSpan, life);
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
