package com.hirshi001.multiplayerrotmg.registry;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.Entity;
import com.hirshi001.multiplayerrotmg.gamepieces.items.Sword;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Player;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Slime;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Bullet;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Fireball;

public class EntityRegistry {

    public static final Registry<EntityRegistration<? extends Entity>> ENTITY_REGISTRY = new ExpandableRegistry<EntityRegistration<? extends Entity>>();

    public static final EntityRegistration<Player> PLAYER_MOB_ENTITY = registerEntity(Player::new);
    public static final EntityRegistration<Slime> SLIME_MOB_ENTITY = registerEntity(Slime::new);

    public static final EntityRegistration<Sword> SWORD_ITEM_ENTITY = registerEntity(Sword::new);

    public static final EntityRegistration<Bullet> BULLET_PROJECTILE_ENTITY = registerEntity(Bullet::new);
    public static final EntityRegistration<Fireball> FIREBALL_PROJECTILE_ENTITY = registerEntity(Fireball::new);


    public static <T extends Entity> EntityRegistration<T> registerEntity(EntityRegistration.EntityCreator<T> ec){
        EntityRegistration<T> er = EntityRegistration.<T>registerEntity(ec);
        er.setId(ENTITY_REGISTRY.register(er));
        return er;
    }

    /**
     *
     * @param <T>
     *     Used for wrapping the initialization method of an Entity type for registrating in a Registry
     */
    public static class EntityRegistration<T>{

        public static <E> EntityRegistration<E> registerEntity(EntityCreator<E> ec){
            return new EntityRegistration<E>(ec);
        }

        private EntityCreator<T> ec;
        private int id;

        public void setId(int id) {
            this.id = id;
        }

        public int getId(){
            return id;
        }

        public EntityRegistration(EntityCreator<T> ec){
            this.ec = ec;
        }

        public EntityCreator<T> getEntityCreator(){
            return ec;
        }

        public interface EntityCreator<P>{
            public P create(Vector2 pos, int id);
        }

    }


}
