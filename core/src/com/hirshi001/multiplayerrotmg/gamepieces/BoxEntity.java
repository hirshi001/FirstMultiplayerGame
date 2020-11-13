package com.hirshi001.multiplayerrotmg.gamepieces;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.field.Chunk;
import com.hirshi001.multiplayerrotmg.field.Field;
import io.netty.buffer.ByteBuf;

public abstract class BoxEntity extends Entity {

    protected Field field;

    private Chunk chunk;

    private Rectangle boundingBox;

    public BoxEntity(Vector2 position, int id){
        super(position, id);
        boundingBox = new Rectangle(getPosition().x, getPosition().y, getWidth(), getHeight());
    }

    public BoxEntity setField(Field f){this.field = f; return this;}
    public Field getField(){return this.field;}
    public Vector2 getCenterPosition(){return getPosition().cpy();}

    public BoxEntity shiftByCenter(){
        getPosition().sub(getWidth()/2, getHeight()/2);
        return this;
    }

    /**
     * The updateBoxEntity() method is generally used for updating movement.
     * The update() method is for checking collision and other events.
     */
    public abstract void updateBoxEntity();

    public abstract float getWidth();
    public abstract float getHeight();

    public Rectangle getBoundingBox(){
        return boundingBox;
    }

    @Override
    public boolean touchingEntity(Entity e){
        if(e instanceof BoxEntity){
            return ((BoxEntity) e).getBoundingBox().overlaps(getBoundingBox());
        }
        return false;
    }



    @Override
    public void write(ByteBuf out) {
        out.writeFloat(getPosition().x);
        out.writeFloat(getPosition().y);
    }

    @Override
    public void read(ByteBuf in) {
        getPosition().set(in.readFloat(), in.readFloat());
    }

    @Override
    public Chunk getChunk() {
        return this.chunk;
    }

    @Override
    public void setChunk(Chunk chunk) {
        this.chunk = chunk;
    }


}
