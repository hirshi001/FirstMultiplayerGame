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
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Field implements Disposable {

    private final List<MobEntity> mobs = new LinkedList<>();
    private final Queue<MobEntity> mobsRemove = new LinkedList<>();
    private final Queue<MobEntity> mobsAdd = new LinkedList<>();

    private final List<ProjectileEntity> projectiles = new LinkedList<>();
    private final Queue<ProjectileEntity> projectilesRemove = new LinkedList<>();
    private final Queue<ProjectileEntity> projectilesAdd = new LinkedList<>();


    private final List<ItemEntity> items = new LinkedList<>();
    private final Queue<ItemEntity> itemsRemove = new LinkedList<>();
    private final Queue<ItemEntity> itemsAdd = new LinkedList<>();

    private final List<Chunk> chunksLoaded = new LinkedList<>();
    private final Map<Integer, Entity> entities = new HashMap<>();

    private Game game;
    private MobEntity mainPlayer;

    /** Used for generating map */
    private OpenSimplexNoise noise;

    private double waterSize = 40d;
    private double biomeSize = 500d;

    public Field(){
        noise = new OpenSimplexNoise(System.currentTimeMillis());
    }

    public List<MobEntity> getMobsList(){return mobs;}
    public List<ItemEntity> getItemsList(){return items;}
    public List<ProjectileEntity> getProjectilesList(){return projectiles;}
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
                for(MobEntity m:c.getMobs()){
                    mobs.remove(m);
                }
                for(ItemEntity i:c.getItems()){
                    items.remove(i);
                }
                for(ProjectileEntity p:c.getProjectiles()) {
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
        for(MobEntity m:mobs){ m.updateBoxEntity(); }
        for(MobEntity m:mobs){ m.tileCollision(); }
        for(MobEntity m:mobs){ m.mobCollision(mobs); }
        for(MobEntity m:mobs){ m.itemTouching(items); }
    }
    private void updateProjectiles(){
        for(ProjectileEntity p:projectiles){p.updateBoxEntity();}
        for(ProjectileEntity p:projectiles){p.touchingMob(mobs);}
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
        MobEntity e;
        while (!mobsAdd.isEmpty()) {
            e = mobsAdd.remove();
            e.setField(this);
            Vector2 pos = e.getCenterPosition();
            Chunk c = getChunkFromCoordinate(pos.x, pos.y);
            e.setChunk(c);
            c.addMob(e);

            mobs.add(e);
            entities.put(e.getId(), e);
        }

        while (!mobsRemove.isEmpty()) {
            e = mobsRemove.remove();
            mobs.remove(e);
            entities.remove(e.getId());
            e.getChunk().removeMob(e);
        }
    }

    private void handleProjectiles(){
        ProjectileEntity p;
        while (!projectilesAdd.isEmpty()) {
            p = projectilesAdd.remove();
            Vector2 pos = p.getCenterPosition();
            Chunk c = getChunkFromCoordinate(pos.x, pos.y);
            c.addProjectile(p);
            p.setChunk(c);
            p.setField(this);

            projectiles.add(p);
            entities.put(p.getId(), p);
        }

        while (!projectilesRemove.isEmpty()) {
            p = projectilesRemove.remove();
            projectiles.remove(p);
            entities.remove(p.getId());
            p.getChunk().removeProjectile(p);
        }
    }
    private void handleItems(){
        ItemEntity i;
        while (!itemsAdd.isEmpty()) {
            i = itemsAdd.remove();
            Vector2 pos = i.getCenterPosition();
            Chunk c = getChunkFromCoordinate(pos.x, pos.y);
            c.addItem(i);
            i.setChunk(c);
            i.setField(this);

            items.add(i);
            entities.put(i.getId(), i);
        }

        while (!itemsRemove.isEmpty()) {
            i = itemsRemove.remove();
            items.remove(i);
            entities.remove(i.getId());
            i.getChunk().removeItem(i);
        }
    }

    public void removeMob(MobEntity m){
        mobsRemove.add(m);
    }
    public void addMob(MobEntity m){mobsAdd.add(m);}

    public void addProjectile(ProjectileEntity p){
        projectilesAdd.add(p);
    }
    public void removeProjectile(ProjectileEntity p){projectilesRemove.add(p);}

    public void removeItem(ItemEntity i){
        itemsRemove.add(i);
    }
    public void addItem(ItemEntity i){itemsAdd.add(i); }

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
