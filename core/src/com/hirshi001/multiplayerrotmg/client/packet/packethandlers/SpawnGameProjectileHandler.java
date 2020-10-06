package com.hirshi001.multiplayerrotmg.client.packet.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.entityloaders.gameprojectileloaders.GameProjectileLoader;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;

public class SpawnGameProjectileHandler extends PacketHandler {

    @Override
    public void handlePacket(Packet p) {
        ByteBuf b  = p.getByteBuf();
        int id = b.readInt();
        GameProjectileLoader loader = EntityRegistry.getProjectileLoader(id);
        ProjectileEntity proj = loader.spawn(b);
        p.getGame().getField().addProjectile(proj);
    }
}
