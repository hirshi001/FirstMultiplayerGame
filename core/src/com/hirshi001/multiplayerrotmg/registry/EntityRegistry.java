package com.hirshi001.multiplayerrotmg.registry;

import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gamemobloaders.GameMobLoader;
import com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gameprojectileloaders.GameProjectileLoader;
import com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.itemloaders.ItemLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EntityRegistry{


    public static final String TEXTURE_PATH = "textures/blocks/";

    public static final Block
    GRASS = new Block((short)0).setTexture(TEXTURE_PATH + "grass.png").collidable(false),
    WALL = new Block((short)1).setTexture(TEXTURE_PATH + "wall.png").collidable(true),
    SNOW = new Block((short)2).setTexture(TEXTURE_PATH + "snow.png").collidable(false),
    GROUND = new Block((short)3).setTexture(TEXTURE_PATH + "ground.png").collidable(false),
    WATER = new Block((short)4).setTexture(TEXTURE_PATH + "water.png").collidable(false);

    public static final List<Block> BLOCKS = new ArrayList<>();
    public static final List<Disposable> TODISPOSE = new LinkedList<>();

    public static final ArrayList<GameMobLoader> MOBLOADERS = new ArrayList<>();
    public static final ArrayList<ItemLoader> ITEMLOADERS = new ArrayList<>();
    public static final ArrayList<GameProjectileLoader> PROJECTILELOADERS = new ArrayList<>();

    static{
        BLOCKS.addAll(Arrays.asList(GRASS,WALL,SNOW,GROUND,WATER));
    }

    public static void dispose(){
        synchronized (BLOCKS) {
            for (Block b : BLOCKS) {
                b.dispose();
            }
        }
        synchronized (TODISPOSE) {
            for (Disposable d:TODISPOSE) {
                d.dispose();
            }
        }
    }

    public static Block getBlock(int id){
        synchronized (BLOCKS){
            return BLOCKS.get(id);
        }
    }

    public static void addDisposable(Disposable d){
        synchronized (TODISPOSE){
            TODISPOSE.add(d);
        }
    }

    public static void registerMobLoader(GameMobLoader loader){
        loader.setId(MOBLOADERS.size());
        MOBLOADERS.add(loader);
    }

    public static int getMobLoaderId(GameMobLoader loader){
        return loader.getId();
    }

    public static GameMobLoader getMobLoader(int id){
        return MOBLOADERS.get(id);
    }


    public static void registerItemLoader(ItemLoader loader){
        loader.setId(ITEMLOADERS.size());
        ITEMLOADERS.add(loader);
    }

    public static int getItemLoaderId(ItemLoader loader){
        return loader.getId();
    }

    public static ItemLoader getItemLoader(int id){
        return ITEMLOADERS.get(id);
    }

    public static void registerProjectileLoader(GameProjectileLoader loader) throws NoSuchFieldException, IllegalAccessException {
        loader.setId(PROJECTILELOADERS.size());
        PROJECTILELOADERS.add(loader);
    }

    public static int getProjectileLoaderId(GameProjectileLoader loader) throws NoSuchFieldException, IllegalAccessException {
        return loader.getId();
    }

    public static GameProjectileLoader getProjectileLoader(int id){
        return PROJECTILELOADERS.get(id);
    }

}

