package com.hirshi001.multiplayerrotmg.gamepieces;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.field.Chunk;
import com.hirshi001.multiplayerrotmg.field.Field;
import io.netty.buffer.ByteBuf;

public abstract class BoxEntity extends Entity {

    protected Vector2 position;
    protected Field field;

    private Chunk chunk;

    int id;

    public final int getId(){
        return id;
    }

    public final void setId(int id){
        this.id = id;
    }

    public BoxEntity(Vector2 position){
        super(position);
        this.position = position;
    }


    @Override
    public Vector2 getLayerPosition() {return position; }

    public BoxEntity setField(Field f){this.field = f; return this;}
    public Field getField(){return this.field;}
    public Vector2 getPosition(){return position;}
    public Vector2 getCenterPosition(){return getPosition().cpy();}

    public BoxEntity shiftByCenter(){
        getPosition().sub(getWidth()/2, getHeight()/2);
        return this;
    }

    public abstract void updateBoxEntity();

    public abstract float getWidth();
    public abstract float getHeight();

    public final boolean touchingBox(Vector2 pos, float width, float height){
        return oneDimensionOverlap(pos.x, pos.x+width,getPosition().x, getPosition().x+getWidth(),false)
                && oneDimensionOverlap(pos.y, pos.y+height,getPosition().y, getPosition().y+getHeight(),false);
    }

    public final boolean touchingBox(Vector2 pos, float width, float height, boolean checkEdges){
        return oneDimensionOverlap(pos.x, pos.x+width,getPosition().x, getPosition().x+getWidth(),checkEdges)
                && oneDimensionOverlap(pos.y, pos.y+height,getPosition().y, getPosition().y+getHeight(),checkEdges);
    }

    public static boolean oneDimensionOverlap(double x1, double x2, double y1, double y2, boolean checkEdges){
        double temp;
        if(x1>x2){
            temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if(y1>y2){
            temp = y1;
            y1 = y2;
            y2 = temp;
        }
        if(checkEdges) return x2>=y1 && y2>=x1;
        return  x2>y1 && y2>x1;
    }

    @Override
    public void write(ByteBuf out) {
        out.writeFloat(position.x);
        out.writeFloat(position.y);
    }

    @Override
    public void read(ByteBuf in) {
        position.set(in.readFloat(), in.readFloat());
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
