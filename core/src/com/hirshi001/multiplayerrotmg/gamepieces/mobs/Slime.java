package com.hirshi001.multiplayerrotmg.gamepieces.mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.DisposableRegistry;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import com.hirshi001.multiplayerrotmg.util.animation.AnimationCycle;
import com.hirshi001.multiplayerrotmg.util.animation.Animator;
import io.netty.buffer.ByteBuf;

import java.util.List;

public class Slime extends MobEntity {


    public static final float WIDTH = 16f/Block.BLOCKWIDTH, HEIGHT = 16f/Block.BLOCKHEIGHT;
    private MobEntity master;
    private static Texture t1, t2;

    private int updateCount = 60;

    static{
        t1 = new Texture("textures/entities/mobs/slime/slimeIdle1.png");
        t2 = new Texture("textures/entities/mobs/slime/slimeIdle2.png");
        DisposableRegistry.addDisposable(t1);
        DisposableRegistry.addDisposable(t2);
    }

    private AnimationCycle cycle = new AnimationCycle(new Animator(new TextureRegion[]{
            new TextureRegion(t1), new TextureRegion(t2)
    }));;

    private boolean facingRight = true;
    private int count = 0;

    public Slime(Vector2 position) {
        super(position);
    }

    @Override
    public void itemTouching(List<ItemEntity> items) {
        return;
    }

    public MobEntity getMaster(){return master;}

    @Override
    public float getWidth() {
        return WIDTH;
    }

    @Override
    public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void drawEntity(SpriteBatch batch) {
        TextureRegion t = cycle.get();
        if(facingRight != t.isFlipX()){
            t.flip(true, false);
        }
        batch.draw(t, getPosition().x*Block.BLOCKWIDTH, getPosition().y*Block.BLOCKHEIGHT,t.getRegionWidth()/2f, t.getRegionHeight()/2f, t.getRegionWidth(), t.getRegionHeight(),1f,1f,0f);
    }


    @Override
    public void update() {
       moveToMaster();


        count++;
        if(count>12){
            cycle.cycle();
            count = -(int)(Math.random()*3);
        }
    }

    private void moveToMaster(){
        updateCount--;
        if(updateCount<0){
            updateCount=60*5;
            master = getField().getMainPlayer();
            List<MobEntity> mobs = getField().getMobsList();
            for(MobEntity m:mobs){
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
}
