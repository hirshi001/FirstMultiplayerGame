package com.hirshi001.multiplayerrotmg.gamepieces.mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hirshi001.multiplayerrotmg.client.Client;
import com.hirshi001.multiplayerrotmg.client.packet.EntityMovePacket;
import com.hirshi001.multiplayerrotmg.client.packet.UseInventoryItemPacket;
import com.hirshi001.multiplayerrotmg.client.packethandlers.UseInventoryItemHandler;
import com.hirshi001.multiplayerrotmg.gamepieces.inventory.Inventory;
import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.Bullet;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import com.hirshi001.multiplayerrotmg.field.Block;
import com.hirshi001.multiplayerrotmg.registry.DisposableRegistry;
import com.hirshi001.multiplayerrotmg.registry.PacketRegistry;
import com.hirshi001.multiplayerrotmg.util.animation.AnimationCycle;
import com.hirshi001.multiplayerrotmg.util.animation.Animator;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Player extends MobEntity {


    public static final float WIDTH = 12f/ Block.BLOCKWIDTH, HEIGHT = 12f/Block.BLOCKHEIGHT;
    private static Texture t1, t2;

    private int lastShot = 0;
    private int lastShotLim = 10;

    static{
        t1 = new Texture("textures/entities/player/player1.png");
        t2 = new Texture("textures/entities/player/player2.png");
        DisposableRegistry.addDisposable(t1);
        DisposableRegistry.addDisposable(t2);
    }

    private AnimationCycle cycle = new AnimationCycle(new Animator(new TextureRegion[]{
            new TextureRegion(t1),new TextureRegion(t2)}));;
    private boolean facingRight = true;

    private Inventory inv;
    private int currentInvItem = 0;

    public Player(Vector2 position, int id){
        super(position, id);
        initializeInv();
    }

    private void initializeInv(){
        inv = new Inventory(9);
        inv.setItem(new FirebalItem(), 0);
        inv.setItem(new BulletItem(), 1);

    }

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
        batch.draw(t, (getPosition().x+getWidth()/2)*Block.BLOCKWIDTH-t.getRegionWidth()/2f, (getPosition().y+getHeight()/2)*Block.BLOCKHEIGHT-t.getRegionHeight()/2f,t.getRegionWidth()/2f, t.getRegionHeight()/2f, t.getRegionWidth(), t.getRegionHeight(),1f,1f,0f);
    }

    @Override
    public void onItemTouching(ItemEntity i){ }

    private int count = 0;

    @Override
    public void updateTick() {
        Vector2 mov = Vector2.Zero.cpy();
        if(Gdx.input.isKeyPressed(Input.Keys.D)) mov.x++;
        if(Gdx.input.isKeyPressed(Input.Keys.A)) mov.x--;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) mov.y++;
        if(Gdx.input.isKeyPressed(Input.Keys.S)) mov.y--;
        mov.nor().scl(0.223f);

        if(!mov.equals(Vector2.Zero)){
            getPosition().add(mov);
            EntityMovePacket packet = PacketRegistry.ENTITY_MOVE_PACKET.getObjectCreator().create();
            packet.setMoveToPosition(getPosition());
            packet.generate();
            Client.sendPacket(packet);
        }

        OrthographicCamera camera = getField().getGame().getGameApplicationAdapter().getCamera();

        Vector2 centerPos = getCenterPosition();
        Vector3 screenPos = camera.project(new Vector3(centerPos.x*Block.BLOCKWIDTH, centerPos.y*Block.BLOCKHEIGHT, 0));
        facingRight = screenPos.x<Gdx.input.getX();




        count++;
        if(count>10){
            cycle.cycle();
            count = 0;
        }

        lastShot++;
        if(lastShot>lastShotLim){
            lastShot=lastShotLim;
        }
        if(lastShot==lastShotLim && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            lastShot = 0;
            Vector3 dir3 = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(),0));
            Vector2 usePos = new Vector2(dir3.x, dir3.y);
            //Vector2 dir = getCenterPosition().scl(Block.BLOCKWIDTH, Block.BLOCKHEIGHT).sub(dir3.x, dir3.y).rotate(180+(int)(Math.random()*20)-10);

            UseInventoryItemPacket packet = PacketRegistry.USE_ONE_PACKET.getObjectCreator().create();

            packet.setId(PacketRegistry.USE_ONE_PACKET.getId());
            packet.setItem(inv.getItem(currentInvItem));
            packet.setInventoryIndex(currentInvItem);
            packet.setUsePosition(usePos);
            packet.generate();
            Client.sendPacket(packet);
            /*
            Bullet b = new Bullet(getCenterPosition().add(dir.nor().scl(1.15f)));
            b.setAngle(dir);
            b.shiftByCenter();
            b.source(this);
            */
        }
    }

    @Override
    public MobEntity applyDamage(int damage, ProjectileEntity source) {
        return this;
    }

    @Override
    protected void onMobCollision(MobEntity e) {
        if(!(e instanceof Slime)){
            super.onMobCollision(e);
        }
    }

    @Override
    public String toString() {
        return "PLAYER at " + getPosition().toString();
    }

    public void updateMob(ByteBuf buffer) {
        int updateType = buffer.readInt();

        switch (updateType){
            case 0 :{
                this.getPosition().set(buffer.readFloat(), buffer.readFloat());
                break;
            }
            case 1:
                setHealth(buffer.readInt());
                break;
            case 2:
                for(int i=0; i<inv.size(); i++) inv.setItem(InventoryItemRegistry.get(buffer.readInt()));
                break;
            case 3:
                inv.setItem(buffer.readInt(), InventoryItemRegistry.get(buffer.readInt()));
                break;
        }
    }

    @Override
    public void write(ByteBuf out) {
        super.write(out);
        out.writeBoolean(facingRight);
    }

    @Override
    public void read(ByteBuf in) {
        super.read(in);
        facingRight = in.readBoolean();
    }
}
