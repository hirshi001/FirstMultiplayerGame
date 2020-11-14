package com.hirshi001.multiplayerrotmg.util.bytewritable;

public class IntegerByteWritable implements ByteWritable {

    private int value;
    byte[] bytes = new byte[4];

    private static final IntegerByteWritable[] CACHE = new IntegerByteWritable[256];

    public IntegerByteWritable(int i){
        this.value = i;
    }

    public static IntegerByteWritable valueOf(int i){
        if(-127<=i && 128>=i) return cache(i);
        return new IntegerByteWritable(i);
    }

    private static IntegerByteWritable cache(int i){
        if(CACHE[i+127]==null) CACHE[i+127]=new IntegerByteWritable(i);
        return CACHE[i+127];
    }

    public static int valueOf(IntegerByteWritable ib){
        return ib.bytes[0]+ib.bytes[1]<<4+ib.bytes[2]<<8+ib.bytes[3]<<12;
    }


    @Override
    public byte[] toBytes() {
        bytes[0] = (byte)value;
        bytes[1] = (byte)(value>>4);
        bytes[2] = (byte)(value>>8);
        bytes[3] = (byte)(value>>12);
        return bytes;
    }

    @Override
    public int getId() {
        return 2;
    }
}
