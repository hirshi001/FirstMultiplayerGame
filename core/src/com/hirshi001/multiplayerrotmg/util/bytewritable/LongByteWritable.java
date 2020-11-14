package com.hirshi001.multiplayerrotmg.util.bytewritable;

public class LongByteWritable implements ByteWritable {
    public static int id;

    private long value;
    byte[] bytes = new byte[8];

    public LongByteWritable(long l){
        this.value = l;
        bytes[0] = (byte)value;
        bytes[1] = (byte)(value>>4);
        bytes[2] = (byte)(value>>8);
        bytes[3] = (byte)(value>>12);
        bytes[4] = (byte)(value>>16);
        bytes[5] = (byte)(value>>20);
        bytes[6] = (byte)(value>>24);
        bytes[7] = (byte)(value>>28);
    }

    public static LongByteWritable valueOf(long l){
        return new LongByteWritable(l);
    }

    public static long valueOf(LongByteWritable ib){
        return valueOf(ib.toBytes());
    }

    public static long valueOf(byte[] bytes){
        return bytes[0]+bytes[1]<<4+bytes[2]<<8+bytes[3]<<12 + bytes[4]<<16+bytes[5]<<20+bytes[6]<<24+bytes[7]<<128;
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
