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

    public static final Registry<EntityRegistration<MobEntity>> MOB_ENTITY_REGISTRY = new ExpandableRegistry<EntityRegistration<MobEntity>>();

    public static final int PLAYER_MOB_ENTITY = MOB_ENTITY_REGISTRY.register(EntityRegistration.<MobEntity>registerEntity(Player::new));
    public static final int SLIME_MOB_ENTITY = MOB_ENTITY_REGISTRY.register(EntityRegistration.<MobEntity>registerEntity(Slime::new));


    public static final Registry<EntityRegistration<ItemEntity>> ITEM_ENTITY_REGISTRY = new ExpandableRegistry<EntityRegistration<ItemEntity>>();

    public static final int SWORD_ITEM_ENTITY = ITEM_ENTITY_REGISTRY.register(EntityRegistration.<ItemEntity>registerEntity(Sword::new));


    public static final Registry<EntityRegistration<ProjectileEntity>> PROJECTILE_ENTITY_REGISTRY = new ExpandableRegistry<EntityRegistration<ProjectileEntity>>();

    public static final int BULLET_PROJECTILE_ENTITY = PROJECTILE_ENTITY_REGISTRY.register(EntityRegistration.<ProjectileEntity>registerEntity(Bullet::new));
    public static final int FIREBALL_PROJECTILE_ENTITY = PROJECTILE_ENTITY_REGISTRY.register(EntityRegistration.<ProjectileEntity>registerEntity(Fireball::new));

}
