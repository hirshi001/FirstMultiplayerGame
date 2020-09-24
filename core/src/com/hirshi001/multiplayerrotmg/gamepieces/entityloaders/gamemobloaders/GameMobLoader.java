package com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gamemobloaders;

import com.hirshi001.multiplayerrotmg.gamepieces.mobs.GameMob;
import com.hirshi001.multiplayerrotmg.util.identifiable.IdentifiableObject;
import io.netty.buffer.ByteBuf;

public abstract class GameMobLoader extends IdentifiableObject {

    public abstract GameMob spawn(ByteBuf b);
}
