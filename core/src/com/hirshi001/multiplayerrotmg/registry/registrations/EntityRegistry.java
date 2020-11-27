package com.hirshi001.multiplayerrotmg.registry.registrations;

import com.hirshi001.multiplayerrotmg.gamepieces.Entity;
import com.hirshi001.multiplayerrotmg.gamepieces.items.Sword;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Player;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Slime;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Bullet;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Fireball;
import com.hirshi001.multiplayerrotmg.registry.registration.EntityRegistration;
import com.hirshi001.multiplayerrotmg.registry.registrysuppliers.EntitySupplier;
import com.hirshi001.multiplayerrotmg.registry.registries.ExpandableRegistry;

public class EntityRegistry {

    public static final ExpandableRegistry<EntityRegistration<? extends Entity>> ENTITY_REGISTRY = new ExpandableRegistry<>();

    public static final EntityRegistration<Player> PLAYER_MOB_ENTITY = registerEntity(Player::new);
    public static final EntityRegistration<Slime> SLIME_MOB_ENTITY = registerEntity(Slime::new);

    public static final EntityRegistration<Sword> SWORD_ITEM_ENTITY = registerEntity(Sword::new);

    public static final EntityRegistration<Bullet> BULLET_PROJECTILE_ENTITY = registerEntity(Bullet::new);
    public static final EntityRegistration<Fireball> FIREBALL_PROJECTILE_ENTITY = registerEntity(Fireball::new);


    public static <T extends Entity> EntityRegistration<T> registerEntity(EntitySupplier<T> supplier){
        EntityRegistration<T> r = new EntityRegistration<>(supplier);
        r.setId(ENTITY_REGISTRY.register(r));
        return r;
    }


}
