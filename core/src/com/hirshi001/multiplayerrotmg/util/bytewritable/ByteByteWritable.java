package com.hirshi001.multiplayerrotmg.util.bytewritable;

public class ByteByteWritable implements ByteWritable{

    private static final ByteByteWritable[] CACHE = new ByteByteWritable[256];

    private byte[] bytes;

    public static int id;

    public ByteByteWritable(byte b){
        this.bytes = new byte[]{b};
    }

    public static ByteByteWritable valueOf(byte b){
        if(CACHE[b+128]==null) CACHE[b+128] = new ByteByteWritable(b);
        return CACHE[b+128];
    }

    public static ByteByteWritable valueOf(boolean b){
        if(b) return valueOf((byte)1);
        return valueOf((byte)0);
    }

    public static byte valueOf(ByteByteWritable b){
        return b.bytes[0];
    }

    @Override
    public byte[] toBytes() {
        return bytes;
    }

    @Override
    public int getId() {
        return 0;
    }
}
