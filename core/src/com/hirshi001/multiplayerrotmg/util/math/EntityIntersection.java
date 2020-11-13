package com.hirshi001.multiplayerrotmg.util.math;

import com.badlogic.gdx.math.Intersector;
import com.hirshi001.multiplayerrotmg.gamepieces.BoxEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.Entity;

public class EntityIntersection {

    public static boolean intersects(Entity e1, Entity e2){
        if(e1 instanceof BoxEntity){
            if(e2 instanceof BoxEntity){
                return ((BoxEntity) e1).getBoundingBox().overlaps(((BoxEntity) e2).getBoundingBox());
            }
        }
        return false;
    }


}
