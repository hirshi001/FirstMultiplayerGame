package com.hirshi001.registry;

import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.gamepieces.items.ItemEntity;
import com.hirshi001.gamepieces.projecticles.GameProjectile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Registry {


    public static final Block
    GRASS = new Block((short)0).collidable(false),
    WALL = new Block((short)1).collidable(true),
    SNOW = new Block((short)2).collidable(false),
    GROUND = new Block((short)3).collidable(false),
    WATER = new Block((short)4).collidable(false);

    public static final List<Block> BLOCKS = new ArrayList<>();

    public static final ArrayList<Class<? extends GameMob>> MOBSCLASS = new ArrayList<>();
    public static final ArrayList<Class<? extends ItemEntity>> ITEMSCLASS = new ArrayList<>();
    public static final ArrayList<Class<? extends GameProjectile>> PROJECTILESCLASS = new ArrayList<>();

    static{
        BLOCKS.addAll(Arrays.asList(GRASS,WALL,SNOW,GROUND,WATER));
    }



    public static Block getBlock(int id){
        synchronized (BLOCKS){
            return BLOCKS.get(id);
        }
    }

    public static void registerMobClass(Class<? extends GameMob> gameMobClass) {
        MOBSCLASS.add(gameMobClass);
    }

    public static int getMobClassId(Class<? extends GameMob> gameMobClass){
        int i=0;
        for(Class<? extends GameMob> g: MOBSCLASS){
            if(g == gameMobClass){
                return i;
            }
            i++;
        }
        return -1;
    }

    public static Class<? extends GameMob> getMobClass(int id){
        return MOBSCLASS.get(id);
    }


    public static void registerItemClass(Class<? extends ItemEntity> itemEntityClass) {
        ITEMSCLASS.add(itemEntityClass);
    }

    public static int getItemClassId(Class<? extends ItemEntity> itemEntityClass){
        int i=0;
        for(Class<? extends ItemEntity> item: ITEMSCLASS){
            if(item.equals(itemEntityClass)){
                return i;
            }
            i++;
        }
        return -1;
    }

    public static Class<? extends ItemEntity> getItemClass(int id){
        return ITEMSCLASS.get(id);
    }

    public static void registerProjectileClass(Class<? extends GameProjectile> gameProjectileClass){
       PROJECTILESCLASS.add(gameProjectileClass);
    }

    public static int getProjectileClassId(Class<? extends GameProjectile> gameProjectileClass){
        int i=0;
        for(Class<? extends GameProjectile> projectile: PROJECTILESCLASS){
            if(projectile.equals(gameProjectileClass)){
                return i;
            }
            i++;
        }
        return -1;
    }

    public static Class<? extends GameProjectile> getProjectileClass(int id){
        return PROJECTILESCLASS.get(id);
    }


}

