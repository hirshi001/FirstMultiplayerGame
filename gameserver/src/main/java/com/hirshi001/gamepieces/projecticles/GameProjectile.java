package com.hirshi001.gamepieces.projecticles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.gamepieces.BoxEntity;
import com.hirshi001.gamepieces.entities.GameMob;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class GameProjectile extends BoxEntity {

    protected GameMob source;
    private LinkedList<GameMobHit> mobsHit = new LinkedList<>();

    public GameProjectile(){super(new Vector2(0,0));}
    public GameProjectile(Vector2 position) {
        super(position);
    }

    @Override
    public void updateBoxEntity() {
        Iterator<GameMobHit> iter = mobsHit.iterator();
        GameMobHit m;
        while(iter.hasNext()){
            m = iter.next();
            m.count++;
            if(m.count>40){
                iter.remove();
            }
        }
        update();
    }

    public abstract void update();


    public GameProjectile source(GameMob o){
        this.source = o;
        return this;
    }

    public Object getSource(){
        return source;
    }

    public void touchingMob(List<GameMob> mobs){
        for(GameMob m:mobs){
            if(touchingBox(m.getPosition(),m.getWidth(), m.getHeight())){
                if(mobsHitContains(m)) continue;
                mobsHit.add(new GameMobHit(m));
                onTouchingMob(m);
            }
        }
    }

    public boolean mobsHitContains(GameMob m){
        for(GameMobHit mobHit :mobsHit){
            if(mobHit.mob.equals(m)) return true;
        }
        return false;
    }

    @Override
    public GameProjectile shiftByCenter() {
        return (GameProjectile)super.shiftByCenter();
    }

    public abstract void onTouchingMob(GameMob m);

    public abstract byte[] toByteArray();
    public abstract void set(byte[] bytes);
}

class GameMobHit{

    GameMob mob;
    int count;

    public GameMobHit(GameMob mob){
        this.mob = mob;
        count = 0;
    }

}
