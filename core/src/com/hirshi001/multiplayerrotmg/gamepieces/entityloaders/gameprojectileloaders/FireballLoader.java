package com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gameprojectileloaders;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Fireball;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.GameProjectile;
import io.netty.buffer.ByteBuf;

public class FireballLoader extends GameProjectileLoader {

    @Override
    public GameProjectile spawn(ByteBuf b) {
        float x = b.readFloat();
        float y = b.readFloat();

        float dirx = b.readFloat();
        float diry = b.readFloat();

        return new Fireball(new Vector2(x,y), new Vector2(dirx, diry));
    }
}
