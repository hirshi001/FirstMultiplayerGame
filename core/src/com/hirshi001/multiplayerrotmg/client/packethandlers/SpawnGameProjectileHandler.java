package com.hirshi001.multiplayerrotmg.client.packethandlers;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;

public class SpawnGameProjectileHandler extends PacketHandler {

    @Override
    public void handlePacket(Packet p) {
        ByteBuf b  = p.getByteBuf();
        int id = b.readInt();
        float x = b.readFloat();
        float y = b.readFloat();
        ProjectileEntity proj = EntityRegistry.PROJECTILE_ENTITY_REGISTRY.getObject(id).getEntityCreator().create(new Vector2(x, y));
        p.getGame().getField().addProjectile(proj);
    }
}
