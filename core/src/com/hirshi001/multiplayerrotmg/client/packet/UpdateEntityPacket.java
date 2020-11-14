package com.hirshi001.multiplayerrotmg.client.packet;

import com.hirshi001.multiplayerrotmg.util.bytewritable.ByteWritable;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UpdateEntityPacket extends Packet {

    private int entityId;
    private String tag;
    private ByteWritable bw;

    public void setEntityId(int id){
        this.entityId = id;
    }

    public void updateCall(String tag, ByteWritable bw){
        this.tag = tag;
        this.bw = bw;
    }


    @Override
    public void generate() {
        byte[] tagBytes = tag.getBytes();
        byte[] bwBytes = bw.toBytes();
        ByteBuf b = Unpooled.buffer(12 + tagBytes.length + bwBytes.length);
        b.writeInt(entityId);
        b.writeInt(tagBytes.length);
        b.writeBytes(tagBytes);
        b.writeInt(bw.getId());
        b.writeBytes(bwBytes);

        setBytes(b);
    }
}
