package com.hirshi001.gamepieces.items;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.gamepieces.BoxEntity;
import com.hirshi001.gamepieces.entities.GameMob;

public abstract class ItemEntity extends BoxEntity {

    public ItemEntity(){super(new Vector2(0,0));}
    public ItemEntity(Vector2 position) {
        super(position);
    }

    public abstract void onDropped(GameMob e);
    public abstract void onPicked(GameMob e);

    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }

    public abstract byte[] toByteArray();
    public abstract void set(byte[] bytes);
}
