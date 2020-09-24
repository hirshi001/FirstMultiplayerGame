package com.hirshi001.gamepieces.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.gamepieces.items.ItemEntity;
import com.hirshi001.registry.Block;
import com.hirshi001.registry.Registry;

import java.nio.ByteBuffer;
import java.util.List;

public class Slime extends GameMob {

    public static final float WIDTH = 16f/ Block.BLOCKWIDTH, HEIGHT = 16f/Block.BLOCKHEIGHT;
    private GameMob master;
    private static Texture t1, t2;

    private int updateCount = 60;

    private boolean facingRight = true;
    private int count = 0;

    public Slime(){}

    public Slime(Vector2 position) {
        super(position);
    }

    @Override
    public void itemTouching(List<ItemEntity> items) {
        return;
    }

    public GameMob getMaster(){return master;}

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void update() {
       moveToMaster();
    }

    private void moveToMaster(){
        updateCount--;
        if(updateCount<0){
            updateCount=60*5;
            master = getField().getMainPlayer();
            List<GameMob> mobs = getField().getMobsList();
            for(GameMob m:mobs){
                if(m instanceof Player && m.getCenterPosition().dst2(getCenterPosition())<400){
                    master = m;
                    break;
                }
            }
        }

        if(master==null) return;

        Vector2 playerPos = master.getCenterPosition();

        float dx = playerPos.x - getCenterPosition().x;
        float dy = playerPos.y - getCenterPosition().y;
        if(!(playerPos.dst2(getCenterPosition())<4)) {
            if (playerPos.dst2(getCenterPosition()) > 30 * 30) {
                getPosition().x = playerPos.x;
                getPosition().y = playerPos.y;
            }
            else {
                Vector2 mov = new Vector2(dx, dy).nor();
                getPosition().add(mov.scl(0.202f));
            }
        }
        facingRight = getCenterPosition().x<getMaster().getCenterPosition().x;
    }


    @Override
    public byte[] toByteArray() {
        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putFloat(getPosition().x);
        buffer.putFloat(getPosition().y);
        buffer.putInt(getHealth());
        return buffer.array();
    }


    @Override
    public void set(byte[] bytes) {
        ByteBuffer b = ByteBuffer.wrap(bytes);
        float x = b.getFloat();
        float y = b.getFloat();
        setHealth(b.getInt());
        this.position = new Vector2(x,y);
    }


}
