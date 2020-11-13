package com.hirshi001.multiplayerrotmg.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.client.packethandlers.UnloadChunkHandler;
import com.hirshi001.multiplayerrotmg.game.Game;
import com.hirshi001.multiplayerrotmg.gamepieces.Entity;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.MobEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import com.hirshi001.multiplayerrotmg.util.opensimplex.OpenSimplexNoise;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Field implements Disposable {

    private final HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
    private final Queue<Entity> entityRemove = new LinkedList<>();
    private final Queue<Entity> entityAdd = new LinkedList<>();


    private final List<Chunk> chunksLoaded = new LinkedList<>();

    private Game game;
    private MobEntity mainPlayer;

    /** Used for generating map */
    private OpenSimplexNoise noise;

    private double waterSize = 40d;
    private double biomeSize = 500d;

    public Field(){
        noise = new OpenSimplexNoise(System.currentTimeMillis());
    }

    public HashMap<Integer, Entity> getEntitiesMap(){return entities;}
    public MobEntity getMainPlayer(){return mainPlayer;}
    public void setMainPlayer(MobEntity m){
        mainPlayer = m;
    }

    public Game getGame(){return this.game;}
    public Field setGame(Game game){this.game = game; return this;}

    public List<Chunk> getChunks(){ return  chunksLoaded; }

    public int getChunkPosFromCoordinate(float coord){
        return (int)Math.floor(coord/Chunk.CHUNKSIZE);
    }



    public void update(){
        updateEntities();
    }

    public void handleChunks(){

        Chunk c;
        ListIterator<Chunk> iter = chunksLoaded.listIterator();
        while(iter.hasNext()){
            c = iter.next();
            c.unloadCount--;
            if(c.unloadCount<=0){
                c.getEntitiesMap().forEach((id, entity) -> entities.remove(id));
                Client.sendPacket(UnloadChunkHandler.generateUnloadPacket(c.getRow(), c.getCol()));
                iter.remove();
            }
        }
    }

    private void updateEntities(){
        getEntitiesMap().forEach((id, entity) -> entity.updateTick());
    }


    public Chunk getChunkFromCoordinate(float x, float y){
        return getChunk((int)Math.floor(y/(Chunk.CHUNKSIZE* Block.BLOCKHEIGHT)),  (int)Math.floor(x/(Chunk.CHUNKSIZE*Block.BLOCKWIDTH)));
    }

    public Chunk getChunk(int row, int col){
        for(Chunk c:chunksLoaded) {
            if (c.getRow() == row && c.getCol() == col) {
                return c;
            }
        }
        return null;
    }


    public void raddEntity(Entity e){ entityAdd.add(e); }
    public void removeEntity(Entity e){ entityRemove.add(e); }

    public void draw(SpriteBatch batch){
        for(Chunk c:getChunks()) {
            c.drawTiles(batch);
        }

    }

    @Override
    public void dispose() {
        //t.dispose();
    }

}
