package com.hirshi001.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.field.Chunk;
import com.hirshi001.field.Field;
import com.hirshi001.gameadapter.GameApplicationAdapter;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.gamepieces.items.ItemEntity;
import com.hirshi001.gamepieces.projecticles.GameProjectile;
import com.hirshi001.registry.Block;

import java.util.List;
import java.util.ListIterator;

public class Game implements Disposable {

    private Field field;

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
     * updates the state of all objects which 
     */
    public void update(){
        handleChunkLoading();
        field.update();
        handleEntityChunks();
        getField().handleChunks();
    }


    private void handleChunkLoading(){
        Vector3 camPos = getGameApplicationAdapter().getCamera().position;
        Vector2 pos = new Vector2(camPos.x, camPos.y).scl(1f/ Block.BLOCKWIDTH, 1f/Block.BLOCKHEIGHT);//field.getMainPlayer().getCenterPosition();
        //pos = getField().getMainPlayer().getCenterPosition();
        Chunk centerChunk = field.getChunkFromCoordinate(pos.x*Block.BLOCKWIDTH, pos.y*Block.BLOCKHEIGHT);
        //field.getChunks().add(centerChunk);
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

    private void handleEntityChunks() {

        List<GameMob> mobs = getField().getMobsList();

        for (GameMob m : mobs) {
            Vector2 pos = m.getCenterPosition();
            int col = getField().getChunkPosFromCoordinate(pos.x);
            int row = getField().getChunkPosFromCoordinate(pos.y);
            if (m.getChunk().getRow() != row || m.getChunk().getCol() != col) {
                m.getChunk().removeMob(m);
                Chunk c = getField().getChunk(row, col);
                c.addMob(m);
                m.setChunk(c);
            }
        }


        List<ItemEntity> items = getField().getItemsList();

        for (ItemEntity i : items) {
            Vector2 pos = i.getCenterPosition();
            int col = getField().getChunkPosFromCoordinate(pos.x);
            int row = getField().getChunkPosFromCoordinate(pos.y);
            if (i.getChunk().getRow() != row || i.getChunk().getCol() != col) {
                i.getChunk().removeItem(i);
                Chunk c = getField().getChunk(row, col);
                c.addItem(i);
                i.setChunk(c);
            }
        }


        List<GameProjectile> projectiles = getField().getProjectilesList();

        for (GameProjectile p : projectiles) {
            Vector2 pos = p.getCenterPosition();
            int col = getField().getChunkPosFromCoordinate(pos.x);
            int row = getField().getChunkPosFromCoordinate(pos.y);
            if (p.getChunk().getRow() != row || p.getChunk().getCol() != col) {
                p.getChunk().removeProjectile(p);
                Chunk c = getField().getChunk(row, col);
                c.addProjectile(p);
                p.setChunk(c);
            }
        }
    }

    @Override
    public void dispose() {
        field.dispose();
    }
}
