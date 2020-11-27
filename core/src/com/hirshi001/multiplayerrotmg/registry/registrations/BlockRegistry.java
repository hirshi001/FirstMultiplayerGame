package com.hirshi001.multiplayerrotmg.registry.registrations;

import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.registries.ExpandableRegistry;
import com.hirshi001.multiplayerrotmg.registry.registries.Registry;

public class BlockRegistry {

    public static Registry<Block> BLOCK_REGISTRY = new ExpandableRegistry<>();

    public static final String DEFAULT_PATH = "";

    public static final int GRASS = BLOCK_REGISTRY.register(new Block((short)0).collidable(false).setTexture(DEFAULT_PATH + "grass"));
    public static final int WALL = BLOCK_REGISTRY.register(new Block((short)1).collidable(true).setTexture(DEFAULT_PATH + "wall"));
    public static final int SNOW = BLOCK_REGISTRY.register(new Block((short)2).collidable(false).setTexture(DEFAULT_PATH + "snow"));
    public static final int GROUND = BLOCK_REGISTRY.register(new Block((short)3).collidable(false).setTexture(DEFAULT_PATH + "ground"));
    public static final int WATER = BLOCK_REGISTRY.register(new Block((short)4).collidable(false).setTexture(DEFAULT_PATH + "water"));

}
