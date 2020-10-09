package com.hirshi001.multiplayerrotmg.gamepieces.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.BoxEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;

public abstract class ItemEntity extends BoxEntity {

    public ItemEntity(Vector2 position, int id) {
        super(position, id);
    }

    public abstract void onDropped(MobEntity e);
    public abstract void onPicked(MobEntity e);

    @Override
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch b){
        if(shouldDraw(bottomLeft, topRight)){
            drawItem(b);
        }
    }

    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }

    public abstract void drawItem(SpriteBatch batch);

}
