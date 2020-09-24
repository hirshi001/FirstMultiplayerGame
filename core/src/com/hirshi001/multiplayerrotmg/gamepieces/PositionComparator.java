package com.hirshi001.multiplayerrotmg.gamepieces;

import java.util.Comparator;

public class PositionComparator implements Comparator<Entity> {
    @Override
    public int compare(Entity o1, Entity o2) {
        if(o1.getLayerPosition().y == o2.getLayerPosition().y) return 0;
        return o1.getLayerPosition().y-o2.getLayerPosition().y>0?-1:1;
    }
}
