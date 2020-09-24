package com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gameprojectileloaders;

import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.GameProjectile;
import com.hirshi001.multiplayerrotmg.util.identifiable.IdentifiableObject;

public abstract class GameProjectileLoader extends IdentifiableObject {


    public abstract GameProjectile spawn();


}
