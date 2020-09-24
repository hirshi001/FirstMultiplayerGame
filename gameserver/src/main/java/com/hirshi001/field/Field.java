package com.hirshi001.field;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.hirshi001.game.Game;
import com.hirshi001.gamepieces.entities.GameMob;
import com.hirshi001.gamepieces.items.ItemEntity;
import com.hirshi001.gamepieces.projecticles.GameProjectile;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;
import com.hirshi001.util.opensimplex.OpenSimplexNoise;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.Random;

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
                try {
                    saveChunk(c);
                } catch (IOException e){e.printStackTrace();}
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
        for(Chunk c:chunksLoaded){
            if(c.getRow() == row && c.getCol() == col){
                return c;
            }
        }

        String filePath = "gamedata/" + row + "-"+col+".dat";
        FileHandle handle = Gdx.files.local(filePath);
        Chunk c;
        if(handle.exists()){
            byte[] bytes = handle.readBytes();
            c = readChunk(bytes, row, col);
        }
        else{
            c = createChunk(row, col);
            try {
                saveChunk(c);
            } catch(IOException e){e.printStackTrace();}
        }
        chunksLoaded.add(c);
        return c;
    }

    public Chunk readChunk(byte[] bytes, int row, int col){
        short[][] tiles = new short[Chunk.CHUNKSIZE][Chunk.CHUNKSIZE];
        ByteBuffer b = ByteBuffer.wrap(bytes);
        int i, j;

        short count = 0;
        short size = 0;
        short id = 1;
        for(i=0;i<tiles.length;i++){
            for(j=0;j<tiles[i].length;j++){
                if(count==size){
                    count = 0;
                    id = b.getShort();
                    size = b.getShort();
                }
                tiles[i][j] = id;
                count++;
            }
        }

        Chunk c = new Chunk(row, col, tiles);

        //read mobs
        int entityCount = b.getInt();
        for(i=0;i<entityCount;i++){
            try {
                int byteSize = b.getInt();
                int entityId = b.getInt();
                byte[] bytesToRead = new byte[byteSize];
                b.get(bytesToRead);

                Class<? extends GameMob> cls = Registry.getMobClass(entityId);
                GameMob m = cls.newInstance();
                System.out.println("Mob loaded");
                m.set(bytesToRead);
                addMob(m);

            } catch(InstantiationException | IllegalAccessException ie){ie.printStackTrace();}
        }

        //read items
        entityCount = b.getInt();
        for(i=0;i<entityCount;i++){
            try {
                int byteSize = b.getInt();
                int entityId = b.getInt();
                byte[] bytesToRead = new byte[byteSize];
                b.get(bytesToRead);

                Class<? extends ItemEntity> cls = Registry.getItemClass(entityId);
                ItemEntity item = cls.newInstance();
                item.set(bytesToRead);
                addItem(item);

            } catch(InstantiationException | IllegalAccessException ie){
                ie.printStackTrace();
            }
        }

        //read projectiles
        entityCount = b.getInt();
        for(i=0;i<entityCount;i++){
            try {
                int byteSize = b.getInt();
                int entityId = b.getInt();
                byte[] bytesToRead = new byte[byteSize];
                b.get(bytesToRead);
                Class<? extends GameProjectile> cls = Registry.getProjectileClass(entityId);
                GameProjectile projectile = cls.newInstance();
                projectile.set(bytesToRead);
                addProjectile(projectile);

            } catch(InstantiationException | IllegalAccessException ie){
                ie.printStackTrace();
            }
        }
        return c;
    }


    public Chunk createChunk(int row, int col){
        //Create the tiles array
        short[][] tiles = new short[Chunk.CHUNKSIZE][Chunk.CHUNKSIZE];

        //create a random object with hopefully a unique seed, considering row and col are unique
        Random ran = new Random(row+col);

        //Declare variables to prevent redeclaration
        short i, j;
        int biome, c, rgb;
        double value;
        //iterate through the rows
        for(i=0;i<tiles.length;i++){
            for(j=0;j<tiles[i].length;j++){
                //Determine Biome
                value = noise.eval((col*Chunk.CHUNKSIZE+j)/ biomeSize,  (row*Chunk.CHUNKSIZE+i)/ biomeSize, 0.0);
                rgb = 0x010101 * (int)((value + 1) * 127.5);
                c = rgb%256;
                if(c<256/3){
                    biome = 0;
                }
                else if(c<256/3*2){
                    biome = 1;
                }
                else{
                    biome = 2;
                }

                //Randomly create walls or ground
                value = noise.eval((col*Chunk.CHUNKSIZE+j) / waterSize,  (row*Chunk.CHUNKSIZE+i)/ waterSize, 0.0);
                if(i==0 || j==0){
                    tiles[i][j] = Registry.WALL.getId();
                }
                else if(value<-.4){
                    tiles[i][j] = Registry.WATER.getId();
                }
                /*else if(Math.random()<0.01){
                    tiles[i][j] = new Tile(i,j, Registry.WALL);
                }
                 */
                else{
                    switch (biome){
                        case 0:
                            tiles[i][j] = Registry.GROUND.getId();
                            break;
                        case 1:
                            tiles[i][j] = Registry.GRASS.getId();
                            break;
                        case 2:
                            tiles[i][j] = Registry.SNOW.getId();
                            break;
                        default:
                            tiles[i][j] = Registry.GROUND.getId();
                            break;
                    }
                }
            }
        }
        return new Chunk(row, col, tiles);
    }

    public void saveChunk(Chunk c) throws IOException {
        String filePath = "gamedata/" + c.getRow() + "-"+c.getCol()+".dat";
        FileHandle handle = Gdx.files.local(filePath);

        OutputStream os = handle.write(false);

        //write tiles into file
        short[][] tiles = c.getTiles();

        short count=0;
        short id=tiles[0][0];
        int i, j;
        for(i=0;i<tiles.length;i++){
            for(j=0;j<tiles[i].length;j++){
                if(tiles[i][j]==id && count!=Short.MAX_VALUE){
                    count++;
                }
                else{
                    ByteBuffer b = ByteBuffer.allocate(4);
                    b.putShort(id);
                    b.putShort(count);
                    count = 1;
                    id = tiles[i][j];
                    os.write(b.array());
                }
            }
        }
        ByteBuffer b = ByteBuffer.allocate(4);
        b.putShort(id);
        b.putShort(count);
        os.write(b.array());

        //write mobs into file
        int entityCount = c.getMobs().size();
        os.write(ByteBuffer.allocate(4).putInt(entityCount).array());
        int size;
        for(GameMob m:c.getMobs()){
            byte[] array = m.toByteArray();
            size = array.length;
            ByteBuffer buff = ByteBuffer.allocate(8);
            buff.putInt(size);

            int classId = Registry.getMobClassId(m.getClass());;
            buff.putInt(classId);

            os.write(buff.array());
            os.write(array);
        }

        //write items into file
        entityCount = c.getItems().size();
        os.write(ByteBuffer.allocate(4).putInt(entityCount).array());
        for(ItemEntity itemEntity:c.getItems()){
            byte[] array = itemEntity.toByteArray();
            size = array.length;
            ByteBuffer buff = ByteBuffer.allocate(8);
            buff.putInt(size);
            buff.putInt(Registry.getItemClassId(itemEntity.getClass()));
            os.write(buff.array());
            os.write(array);
        }

        //write projectiles into file
        entityCount = c.getProjectiles().size();
        os.write(ByteBuffer.allocate(4).putInt(entityCount).array());
        for(GameProjectile p: c.getProjectiles()){
            byte[] array = p.toByteArray();
            size = array.length;
            ByteBuffer buff = ByteBuffer.allocate(8);
            buff.putInt(size);
            buff.putInt(Registry.getProjectileClassId(p.getClass()));
            os.write(buff.array());
            os.write(array);
        }

        os.flush();
        os.close();
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
