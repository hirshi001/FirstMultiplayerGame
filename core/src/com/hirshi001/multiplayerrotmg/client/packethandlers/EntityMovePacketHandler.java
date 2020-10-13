package com.hirshi001.multiplayerrotmg.client.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.Entity;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class EntityMovePacketHandler extends PacketHandler {

    @Override
    public void handlePacket(Packet p) {
        Map<Integer, Entity> map = p.getGame().getField().getEntityMap();
        ByteBuf b = p.getByteBuf();
        int entityId = b.readInt();
        if(map.containsKey(entityId)){
            map.get(entityId).getPosition().set(b.readFloat(), b.readFloat());
        }
    }

}
