package com.hirshi001.gameadapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.field.Field;
import com.hirshi001.game.Game;
import com.hirshi001.game.GameBuilder;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.gamepieces.entities.Player;
import com.hirshi001.gamepieces.entities.Slime;
import com.hirshi001.gamepieces.items.Sword;
import com.hirshi001.gamepieces.projecticles.Bullet;
import com.hirshi001.gamepieces.projecticles.Fireball;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;

public class GameApplication extends GameApplicationAdapter{

    private GameMob mainPlayer;

    @Override
    public void init() {
        Registry.registerMobClass(Player.class);
        Registry.registerMobClass(Slime.class);

        Registry.registerItemClass(Sword.class);


        Registry.registerProjectileClass(Fireball.class);
        Registry.registerProjectileClass(Bullet.class);
    }

    @Override
    public void startup(){

        Field field = new Field();
        mainPlayer = new Player(new Vector2(1,1));
        field.setMainPlayer(mainPlayer);
        field.addMob(mainPlayer);

        field.addMob(new Slime(new Vector2(mainPlayer.getCenterPosition())));
        getCamera().position.x = mainPlayer.getPosition().x* Block.BLOCKWIDTH;
        getCamera().position.y = mainPlayer.getPosition().y*Block.BLOCKHEIGHT;

        for(int i=0;i<5;i++) field.addMob(new Slime(mainPlayer.getCenterPosition().cpy().add((int)(Math.random()*10)-5,(int)(Math.random()*10)+-5)));
        //for(int i=0;i<50;i++) field.addItem(new Sword(new Vector2((int)(Math.random()*(field.getCols()-3)),(int)(Math.random()*(field.getRows()-3)))));
        //field.addProjectile(new Fireball(mainPlayer.getPosition().cpy(),new Vector2(1,1)));

        Game g = new GameBuilder(field).gameApplicationAdapter(this).build();
        setGame(g);
    }


}
