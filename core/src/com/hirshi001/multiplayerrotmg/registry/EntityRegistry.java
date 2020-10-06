package com.hirshi001.multiplayerrotmg.registry;

import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.items.Sword;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Player;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Slime;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Bullet;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Fireball;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;

public class EntityRegistry {

    public static final Registry<MobEntity> MOB_ENTITY_REGISTRY = new ExpandableRegistry<MobEntity>();

    public static final int PLAYER_MOB_ENTITY = MOB_ENTITY_REGISTRY.register(Player.class);
    public static final int SLIME_MOB_ENTITY = MOB_ENTITY_REGISTRY.register(Slime.class);


    public static final Registry<ItemEntity> ITEM_ENTITY_REGISTRY = new ExpandableRegistry<ItemEntity>();

    public static final int SWORD_ITEM_ENTITY = ITEM_ENTITY_REGISTRY.register(Sword.class);


    public static final Registry<ProjectileEntity> PROJECTILE_ENTITY_REGISTRY = new ExpandableRegistry<ProjectileEntity>();

    public static final int BULLET_PROJECTILE_ENTITY = PROJECTILE_ENTITY_REGISTRY.register(Bullet.class);
    public static final int FIREBALL_PROJECTILE_ENTITY = PROJECTILE_ENTITY_REGISTRY.register(Fireball.class);

}
