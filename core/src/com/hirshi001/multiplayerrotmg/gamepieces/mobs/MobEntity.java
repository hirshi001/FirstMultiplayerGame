package com.hirshi001.multiplayerrotmg.gamepieces.mobs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.field.Field;
import com.hirshi001.multiplayerrotmg.gamepieces.BoxEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.items.ItemEntity;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import io.netty.buffer.ByteBuf;

import java.util.List;
import java.util.Random;

public abstract class MobEntity extends BoxEntity {

    private Vector2 lastPosition;

    private int lastItemTouchingCheck = 0;
    private int itemTouchingCheckLim = 20;
    private int health = 20;

    public MobEntity(final Vector2 position, int id){
        super(position, id);
        lastPosition = getPosition().cpy();
    }


    public Vector2 getLastPosition(){return lastPosition;}
    public Vector2 getCenterPosition(){return getPosition().cpy().add(getWidth()/2f, getHeight()/2f);}
    public Vector2 getCenterPosition(Vector2 v){return v.cpy().add(getWidth()/2f, getHeight()/2f);}

    public MobEntity applyDamage(int damage){
        health-=damage;
        if(health<0){

        }
        return this;
    }

    public int getHealth(){
        return health;
    }
    public MobEntity setHealth(int health){
        this.health = health;
        return this;
    }


    public MobEntity applyDamage(int damage, ProjectileEntity source){
        return applyDamage(damage);
    }


    public MobEntity applyDamage(int damage, MobEntity source){
        return applyDamage(damage);
    }


    @Override
    public MobEntity shiftByCenter() {
        return (MobEntity)super.shiftByCenter();
    }

    @Override
    public void draw(Vector2 bottomLeft, Vector2 topRight, SpriteBatch batch) {
        if(shouldDraw(bottomLeft, topRight)){
            drawEntity(batch);
        }
    }

    public abstract void drawEntity(SpriteBatch batch);
    public boolean shouldDraw(Vector2 bottomLeft, Vector2 topRight){
        return !(getPosition().x+getWidth()<bottomLeft.x || getPosition().x>topRight.x || getPosition().y+getHeight()<bottomLeft.y || getPosition().y>topRight.y);
    }

    @Override
    public void updateBoxEntity(){
        lastPosition.set(getPosition());
    }

    @Override
    public MobEntity setField(Field f) {
        field = f;
        return this;
    }

    public void tileCollision(){
        /*
        short[][] tiles =getField().getTiles();
        Vector2 dir = getPosition().cpy().sub(getLastPosition());
        //System.out.println(dir);
        //check right side of tile

        Vector2 centerPosition = getCenterPosition();
        Vector2 topLeft = new Vector2(centerPosition).sub(getWidth() / 2, getHeight() / 2);
        Vector2 bottomRight = new Vector2(centerPosition).add(getWidth()/2, getHeight()/2);
        int startX, endX, dx;
        if(dir.x>0) {
            startX = (int) topLeft.x;
            endX = (int) bottomRight.x;
            dx = 1;
        }
        else{
            endX = (int) topLeft.x;
            startX = (int) bottomRight.x;
            dx = -1;
        }

        int startY, endY, dy;
        if(dir.y>0) {
            startY = (int) topLeft.y;
            endY = (int) bottomRight.y;
            dy = 1;
        }
        else{
            endY = (int) topLeft.y;
            startY = (int) bottomRight.y;
            dy = -1;
        }
        float slope, b;
        //System.out.println(startX + " : " + endX + " :: "+startY + " : " + endY + " | dx : "+dx+" - dy : "+ dy);

        for(int i=startX;dir.x>0?i<=endX:i>=endX;i+=dx){
            for(int j=startY;dir.y>0?j<=endY:j>=endY;j+=dy){
                if (Registry.getBlock(tiles[j][i]).isCollidable()) {
                    if (!touchingBox(new Vector2(i, j), 1, 1, false)) continue;
                    if(dir.x!=0 && dir.y!=0) {
                        slope = dir.y / dir.x;
                        if (dir.x < 0 && dir.y < 0) {
                            b=getPosition().y-slope*getPosition().x;
                            float intX = (j+1-b)/slope;

                            if(intX>i+1) getPosition().x=i+1;
                            else getPosition().y=j+1;

                        }else if (dir.x < 0 && dir.y > 0) {
                            b=getPosition().y-slope*(getPosition().x)+getHeight();
                            float intX = (j-b)/slope;

                            if(intX>i+1) getPosition().x=i+1;
                            else getPosition().y=j-getHeight();

                        } else if (dir.x > 0 && dir.y < 0) {
                            b=getPosition().y-slope*(getPosition().x+getWidth());
                            float intX = (j+1-b)/slope;

                            if(intX<i) getPosition().x=i-getWidth();
                            else getPosition().y=j+1;

                        } else if (dir.x > 0 && dir.y > 0) {
                            b=getPosition().y-slope*(getPosition().x+getHeight())+getHeight();
                            float intX = (j-b)/slope;

                            if(intX<i) getPosition().x=i-getWidth();
                            else getPosition().y=j-getHeight();

                        }
                    }
                    else{
                        if(dir.x==0){
                            if(dir.y>0){
                                getPosition().y=j-getHeight();
                            }
                            else{
                                getPosition().y=j+1;
                            }
                        }
                        else {
                            if(dir.x>0){
                                getPosition().x=i-getWidth();
                            }
                            else{
                                getPosition().x=i+1;
                            }
                        }
                    }
                }
            }
        }

        /*
        boolean shouldBreak = false;
        for(int i=startX;dir.x>0?i<=endX:i>=endX;i+=dx){
            for(int j=startY;dir.y>0?j<=endY:j>=endY;j+=dy) {
                if(field.getStructureTiles()[j][i].isDoor()){
                    if(onTouchingDoor(field.getStructureTiles()[j][i])) {
                        shouldBreak = true;
                        break;
                    }
                }
            }
            if(shouldBreak) break;
        }
         */
    }


    protected void onTouchingEntity(MobEntity e) {
        Random r = new Random();
        if(e.getCenterPosition().equals(getCenterPosition())){
            getLastPosition().set(getPosition());
            getPosition().add( (r.nextBoolean()?-1:1)*0.00001f,(r.nextBoolean()?-1:1)*0.00001f);
            tileCollision();
        }

        Vector2 mov = new Vector2(e.getCenterPosition().x - getCenterPosition().x, e.getCenterPosition().y-getCenterPosition().y);
        mov.nor().scl(0.01f);

        int i=0;
        while (touchingEntity(e)) {
            if (i > 20){
                break;
            }
            if(mov.len2()>1) mov.nor().scl(1);
            getLastPosition().set(getPosition());
            getPosition().sub(mov);
            i++;
            tileCollision();
        }
    }

    public void itemTouching(List<ItemEntity> items){
        lastItemTouchingCheck++;
        if(lastItemTouchingCheck<itemTouchingCheckLim) return;
        lastItemTouchingCheck = 0;
        for(ItemEntity i:items){
            if(touchingEntity(i)){
                onItemTouching(i);
            }
        }
    }

    public void onItemTouching(ItemEntity i){
    }

    @Override
    public void write(ByteBuf out) {
        super.write(out);
        out.writeInt(health);
        out.writeInt(lastItemTouchingCheck);
        out.writeInt(itemTouchingCheckLim);
    }

    @Override
    public void read(ByteBuf in) {
        super.read(in);
        health = in.readInt();
        lastItemTouchingCheck = in.readInt();
        itemTouchingCheckLim = in.readInt();
    }
}
