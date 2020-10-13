package com.hirshi001.multiplayerrotmg.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.field.Chunk;
import com.hirshi001.multiplayerrotmg.field.Field;
import com.hirshi001.multiplayerrotmg.gameadapter.GameApplicationAdapter;
import com.hirshi001.multiplayerrotmg.inputhandlers.InputHandler;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.util.camera.CameraStyles;

import java.awt.Point;
import java.util.ListIterator;

public class Game implements Disposable {

    private Field field;
    private SpriteBatch spriteBatch;
    private InputHandler inputHandler;

    private GameApplicationAdapter application;

    public Game(){  }

    /**
     *
     * @param application the GameApplicationAdapter which runs the game
     * @return a reference to this object for chaining
     */
    public Game setGameApplicationAdapter(GameApplicationAdapter application){
        this.application = application;
        return this;
    }

    /**
     *
     * @return a reference to the GameApplicationAdapter
     */
    public GameApplicationAdapter getGameApplicationAdapter(){
        return application;
    }

    /**
     *
     * @param batch SpriteBatch used to draw sprites
     * @return a reference to this object for chaining
     */
    public Game setSpriteBatch(SpriteBatch batch){
        this.spriteBatch = batch;
        return this;
    }

    /**
     *
     * @return the SpriteBatch used for drawing sprites
     */
    public SpriteBatch getSpriteBatch(){return this.spriteBatch;}

    /**
     *
     * @param field used for displaying the individual tiles, structures, and other entities in the game
     * @return a reference to this object for chaining
     */
    public Game setField(Field field){
        this.field = field;
        field.setGame(this);
        return this;
    }

    /**
     *
     * @return the field that will next be displayed on the screen. This will remain true unless the Field in this Game
     * object is changed after this method call. Changing the field can be done through the setField(Field field);
     * method. If the behavior of the game object is changed however, it can be done through other means.
     */
    public Field getField(){return field;}

    /**
     *
     * @param handler which handles all inputs including key presses and mouse clicks.
     * @return a reference to this object for chaining
     */
    public Game setInputHandler(InputHandler handler){
        this.inputHandler = handler;
        return this;
    }

    /**
     *
     * @return the InputHandler which handles all inputs including key presses and mouse clicks.
     */
    public InputHandler getInputHandler(){return this.inputHandler;}

    /**
     * draws the current state of the game onto the screen. Usually, little to no changes in the state of the game
     * should occur in this method. Changes in state should happen in the update method.
     */
    public void draw(){
        field.draw(spriteBatch);
    }

    /**
     * updates the state of all objects which 
     */
    public void update(){
        //sends request to server for chunks
        handleChunkLoading();
        //handles packets sent from the server (chunk, entity, message)
        field.update();
        //unloads chunks
        getField().handleChunks();
        //handles camera movement
        handleCameraPosition();
    }

    /**
     * Sets the camera position
     */
    private void handleCameraPosition(){
        if(getInputHandler().getScreenMover().isCameraFollow()){
            OrthographicCamera camera = getGameApplicationAdapter().getCamera();
            //System.out.println(camera.position);
            //camera.position.x = pos.x;
            //camera.position.y = pos.y;
            CameraStyles.lerpToTarget(camera.position,getField().getMainPlayer().getCenterPosition().scl(Block.BLOCKWIDTH, Block.BLOCKHEIGHT),0.1f);
        }
    }

    /**
     * Handles sending chunk requests to server and removing chunks from the field
     */
    private void handleChunkLoading(){
        Chunk centerChunk = getField().getMainPlayer().getChunk();
        int i, j;
        Chunk c;
        ListIterator<Chunk> iter;

        boolean contains;
        int k = 0;
        for(i=-1;i<=1;i++){
            for(j=-1;j<=1;j++){
                k++;
                iter = field.getChunks().listIterator();
                contains = false;
                while(iter.hasNext()){
                    c = iter.next();
                    if(c.getRow()==centerChunk.getRow()+i && c.getCol()==centerChunk.getCol()+j){
                        c.unloadCount = 50;
                        contains = true;
                        break;
                    }
                }
                if(!contains){
                    Chunk newChunk = field.getChunk(centerChunk.getRow() + i, centerChunk.getCol() + j);
                    newChunk.unloadCount = 50;
                    field.getChunks().add(newChunk);
                    //System.exit(-1);
                }
            }
        }
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
