package com.hirshi001.multiplayerrotmg.client.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import com.hirshi001.multiplayerrotmg.registry.registrations.EntityRegistry;
import io.netty.buffer.ByteBuf;

public class SpawnEntityHandler extends PacketHandler {

    @Override
    public void handlePacket(Packet p) {
        ByteBuf b  = p.getByteBuf();
        int entityTypeId = b.readInt();
        int entityId = b.readInt();
        float x = b.readFloat();
        float y = b.readFloat();
        ProjectileEntity proj = EntityRegistry.ENTITY_REGISTRY.getRegistration(entityTypeId).getObject().get();
        proj.po
        p.getGame().getField().addEntity(proj);
    }
}
