package com.hirshi001.multiplayerrotmg.client.packet.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UnloadChunkHandler extends PacketHandler {

    public static int id;

    @Override
    public void handlePacket(Packet p) {

    }

    public static Packet generateUnloadPacket(int row, int col){
        ByteBuf b = Unpooled.buffer(8);
        b.writeInt(row);
        b.writeInt(col);
        return new Packet().setId(id).setBytes(b);
    }
}
