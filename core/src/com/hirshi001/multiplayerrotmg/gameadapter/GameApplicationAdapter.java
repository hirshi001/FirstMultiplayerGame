package com.hirshi001.multiplayerrotmg.gameadapter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.game.Game;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.Player;

public abstract class GameApplicationAdapter implements Disposable {

    /**
     * Reference to the game object
     */
    private Game game;
    /**
     * Reference to the Orthographic camera
     */
    private OrthographicCamera camera;

    private Client client;

    private Player player;

    public abstract void init() throws NoSuchFieldException, IllegalAccessException;

    /**
     * Called before the program's main loop starts to setup all necessary objects and references.
     */
    public abstract void startup();

    /**
     *
     * @return reference to the game object
     */
    public Game getGame(){
        return game;
    }
    public final GameApplicationAdapter setGame(Game game){
        this.game = game;
        return this;
    }
    public void update(){
        if(game.isReady()){
            getGame().update();
        }
        else{
            //idk
        }
    }

    /**
     * Draws on the screen based on the state of the client (connected to server, not connected, etc...)
     * @param batch - the sprite batch used to draw the sprites
     */
    public void draw(SpriteBatch batch){
        if(game.isReady()) {
            game.setSpriteBatch(batch);
            game.draw();
        }
        else{
            //idk
        }
    }

    /**
     *
     * @return camera used in rendering
     */
    public OrthographicCamera getCamera(){
        return this.camera;
    }

    /**
     * sets the camera used for rendering
     * @param camera
     * @return this object for chaining purposes
     */
    public GameApplicationAdapter setCamera(OrthographicCamera camera){
        this.camera = camera;
        return this;
    }

    @Override
    public void dispose() {
        game.dispose();
    }
}
