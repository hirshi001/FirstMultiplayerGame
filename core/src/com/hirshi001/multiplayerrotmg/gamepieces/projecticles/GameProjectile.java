package com.hirshi001.multiplayerrotmg.gamepieces.projecticles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.client.packet.packethandlers.UseInventoryItemHandler;
import com.hirshi001.multiplayerrotmg.gamepieces.BoxEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.GameMob;

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
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b) {
        if(shouldDraw(bottomLeft, topRight)){
            drawProjectile(b);
        }
    }

    @Override
    public void updateBoxEntity() {
        update();
    }

    public abstract void update();

    public abstract void drawProjectile(SpriteBatch batch);

    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }



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

}

class GameMobHit{

    GameMob mob;
    int count;

    public GameMobHit(GameMob mob){
        this.mob = mob;
        count = 0;
    }

}
