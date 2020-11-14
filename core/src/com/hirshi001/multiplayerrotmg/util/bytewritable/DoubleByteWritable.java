package com.hirshi001.multiplayerrotmg.util.bytewritable;

import java.nio.ByteBuffer;

public class DoubleByteWritable implements ByteWritable {

    public static int id;

    private double value;
    byte[] bytes;

    public DoubleByteWritable(double d){
        this.value = d;
        bytes = ByteBuffer.allocate(8).putDouble(d).array();
    }

    public static DoubleByteWritable valueOf(double f){
        return new DoubleByteWritable(f);
    }

    public static double valueOf(FloatByteWritable ib){
        return valueOf(ib.toBytes());
    }

    public static double valueOf(byte[] bytes){
        return ByteBuffer.wrap(bytes).getDouble();
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
