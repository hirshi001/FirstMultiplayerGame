package com.hirshi001.multiplayerrotmg.field;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.client.packet.packethandlers.UnloadChunkHandler;
import com.hirshi001.multiplayerrotmg.game.Game;
import com.hirshi001.multiplayerrotmg.gamepieces.mobs.GameMob;
import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.GameProjectile;
import com.hirshi001.multiplayerrotmg.util.opensimplex.OpenSimplexNoise;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

public class Field implements Disposable {

    private final List<GameMob> mobs = new LinkedList<>();
    private final Queue<GameMob> mobsRemove = new LinkedList<>();
    private final Queue<GameMob> mobsAdd = new LinkedList<>();

    private final List<GameProjectile> projectiles = new LinkedList<>();
    private final Queue<GameProjectile> projectilesRemove = new LinkedList<>();
    private final Queue<GameProjectile> projectilesAdd = new LinkedList<>();


    private final List<ItemEntity> items = new LinkedList<>();
    private final Queue<ItemEntity> itemsRemove = new LinkedList<>();
    private final Queue<ItemEntity> itemsAdd = new LinkedList<>();


    private final List<Chunk> chunksLoaded = new LinkedList<>();

    private Game game;
    private GameMob mainPlayer;

    /** Used for generating map */
    private OpenSimplexNoise noise;

    private double waterSize = 40d;
    private double biomeSize = 500d;

    public Field(){
        noise = new OpenSimplexNoise(System.currentTimeMillis());
    }

    public List<GameMob> getMobsList(){return mobs;}
    public List<ItemEntity> getItemsList(){return items;}
    public List<GameProjectile> getProjectilesList(){return projectiles;}
    public GameMob getMainPlayer(){return mainPlayer;}
    public void setMainPlayer(GameMob m){
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
                for(GameMob m:c.getMobs()){
                    mobs.remove(m);
                }
                for(ItemEntity i:c.getItems()){
                    items.remove(i);
                }
                for(GameProjectile p:c.getProjectiles()) {
                    projectiles.remove(p);
                }
                Client.sendPacket(UnloadChunkHandler.generateUnloadPacket(c.getRow(), c.getCol()));
                iter.remove();
            }
        }
    }

    private void updateEntities(){

        handleMobs();
        handleItems();
        handleProjectiles();

        updateMobs();
        updateProjectiles();
        for(ItemEntity e:items){ e.updateBoxEntity(); }

    }

    private void updateMobs(){
        for(GameMob m:mobs){ m.updateBoxEntity(); }
        for(GameMob m:mobs){ m.tileCollision(); }
        for(GameMob m:mobs){ m.mobCollision(mobs); }
        for(GameMob m:mobs){ m.itemTouching(items); }
    }
    private void updateProjectiles(){
        for(GameProjectile p:projectiles){p.updateBoxEntity();}
        for(GameProjectile p:projectiles){p.touchingMob(mobs);}
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

    private void handleMobs(){
        GameMob e;
        while (!mobsAdd.isEmpty()) {
            e = mobsAdd.remove();
            e.setField(this);

            Vector2 pos = e.getCenterPosition();
            Chunk c = getChunkFromCoordinate(pos.x, pos.y);
            e.setChunk(c);
            c.addMob(e);

            mobs.add(e);
        }

        while (!mobsRemove.isEmpty()) {
            e = mobsRemove.remove();
            mobs.remove(e);
            e.getChunk().removeMob(e);
        }
    }

    private void handleProjectiles(){
        GameProjectile p;
        while (!projectilesAdd.isEmpty()) {
            p = projectilesAdd.remove();
            projectiles.add(p);
            Vector2 pos = p.getCenterPosition();
            Chunk c = getChunkFromCoordinate(pos.x, pos.y);
            c.addProjectile(p);
            p.setChunk(c);
            p.setField(this);
        }

        while (!projectilesRemove.isEmpty()) {
            p = projectilesRemove.remove();
            projectiles.remove(p);
            p.getChunk().removeProjectile(p);
        }
    }
    private void handleItems(){
        ItemEntity i;
        while (!itemsAdd.isEmpty()) {
            i = itemsAdd.remove();
            items.add(i);
            Vector2 pos = i.getCenterPosition();
            Chunk c = getChunkFromCoordinate(pos.x, pos.y);
            c.addItem(i);
            i.setChunk(c);
            i.setField(this);
        }

        while (!itemsRemove.isEmpty()) {
            i = itemsRemove.remove();
            items.remove(i);
            i.getChunk().removeItem(i);
        }
    }

    public void removeMob(GameMob m){
        mobsRemove.add(m);
    }
    public void addMob(GameMob m){mobsAdd.add(m);}

    public void addProjectile(GameProjectile p){
        projectilesAdd.add(p);
    }
    public void removeProjectile(GameProjectile p){projectilesRemove.add(p);}

    public void removeItem(ItemEntity i){
        itemsRemove.add(i);
    }
    public void addItem(ItemEntity i){itemsAdd.add(i); }


    @Override
    public void dispose() {
        //t.dispose();
    }

}
