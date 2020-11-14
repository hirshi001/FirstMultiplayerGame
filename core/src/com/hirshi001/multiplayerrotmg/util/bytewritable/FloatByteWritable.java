package com.hirshi001.multiplayerrotmg.util.bytewritable;

import java.nio.ByteBuffer;

public class FloatByteWritable implements ByteWritable {

    public static int id;

    private float value;
    byte[] bytes;

    public FloatByteWritable(float f){
        this.value = f;
        bytes = ByteBuffer.allocate(4).putFloat(f).array();
    }

    public static FloatByteWritable valueOf(Float f){
        return new FloatByteWritable(f);
    }

    public static float valueOf(FloatByteWritable ib){
        return valueOf(ib.toBytes());
    }

    public static float valueOf(byte[] bytes){
        return ByteBuffer.wrap(bytes).getFloat();
    }


    @Override
    public byte[] toBytes() {
        return bytes;
    }

    @Override
    public int getId() {
        return id;
    }
}
