package com.hirshi001.multiplayerrotmg.gameadapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.client.packet.packethandlers.UseInventoryItemHandler;
import com.hirshi001.multiplayerrotmg.client.packet.packethandlers.UnloadChunkHandler;
import com.hirshi001.multiplayerrotmg.field.Field;
import com.hirshi001.multiplayerrotmg.game.Game;
import com.hirshi001.multiplayerrotmg.game.GameBuilder;
import com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gameprojectileloaders.FireballLoader;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.GameMob;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Player;
import com.hirshi001.multiplayerrotmg.inputhandlers.InputHandler;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.PacketRegistry;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;

public class GameApplication extends GameApplicationAdapter{

    private GameMob mainPlayer;

    @Override
    public void init() throws NoSuchFieldException, IllegalAccessException {
        EntityRegistry.registerMobLoader(new PlayerLoader());
        EntityRegistry.registerMobLoader(new SlimeLoader());

        EntityRegistry.registerItemLoader(new SwordLoader());

        EntityRegistry.registerProjectileLoader(new FireballLoader());
        EntityRegistry.registerProjectileLoader(new BulletLoader());

        PacketRegistry.registerPacketHandler(new UseInventoryItemHandler());
        PacketRegistry.registerPacketHandler(new UnloadChunkHandler());
    }

    @Override
    public void startup(){

        Field field = new Field();
        mainPlayer = new Player(new Vector2(1,1));
        field.setMainPlayer(mainPlayer);

        //field.addMob(mainPlayer);

        getCamera().position.x = mainPlayer.getPosition().x* Block.BLOCKWIDTH;
        getCamera().position.y = mainPlayer.getPosition().y*Block.BLOCKHEIGHT;

        Game g = new GameBuilder(field).inputHandler(new InputHandler()).gameApplicationAdapter(this).build();
        setGame(g);
        Gdx.input.setInputProcessor(getGame().getInputHandler());
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


}
