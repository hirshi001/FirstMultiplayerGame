package com.hirshi001.multiplayerrotmg.client.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.Entity;
import com.hirshi001.multiplayerrotmg.util.bytewritable.ByteByteWritable;
import com.hirshi001.multiplayerrotmg.util.bytewritable.ByteWritable;
import com.hirshi001.multiplayerrotmg.util.bytewritable.DoubleByteWritable;
import com.hirshi001.multiplayerrotmg.util.bytewritable.FloatByteWritable;
import com.hirshi001.multiplayerrotmg.util.bytewritable.IntegerByteWritable;
import com.hirshi001.multiplayerrotmg.util.bytewritable.LongByteWritable;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class UpdateEntityPacketHandler extends PacketHandler {

    @Override
    public void handlePacket(Packet p) {
        ByteBuf b = p.getByteBuf();

        int entityId = b.readInt();
        int tagSize = b.readInt();

        String tag = new String(b.readBytes(tagSize).array());

        int dataType = b.readInt();
        ByteWritable bw = value(dataType, b);

        Map<Integer, Entity> entityMap = p.getGame().getField().getEntitiesMap();
        if(entityMap.containsKey(entityId)){
            entityMap.get(entityId).getData().put(tag, bw); //THIS WILL NOT BE AN ISSUE FOR RESENDING PACKETS SINCE THIS HANDLEPACKET METHOD WILL ONLY BE CALLED ON THE CLIENT SIDE.
        }
    }

    private ByteWritable value(int dataType, ByteBuf b) {
        switch (dataType) {
            case 0: //Byte
                return ByteByteWritable.valueOf(b.readByte());
            case 1: //Boolean
                return ByteByteWritable.valueOf(b.readByte() == 1);
            case 2: //Integer
                return IntegerByteWritable.valueOf(b.readInt());
            case 3: //Long
                return LongByteWritable.valueOf(b.readLong());
            case 4: //Float
                return FloatByteWritable.valueOf(b.readFloat());
            case 5: //Double
                return DoubleByteWritable.valueOf(b.readDouble());
            default:
                return ByteByteWritable.valueOf((byte)0);
        }
    }

}
