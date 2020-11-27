package com.hirshi001.multiplayerrotmg.client.packet;

import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.ProjectileEntity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class SpawnProjectilePacket extends Packet{

    private ProjectileEntity pe;
    private int entityTypeId;

    public void setProjectileEntity(ProjectileEntity pe, int entityTypeId){
        this.pe = pe;
        this.entityTypeId = entityTypeId;
    }

    @Override
    public void generate() {
        ByteBuf buf = Unpooled.buffer(16);
        buf.writeFloat(entityTypeId);
        buf.writeInt(pe.getId());
        buf.writeFloat(pe.getPosition().x);
        buf.writeFloat(pe.getPosition().y);
    }
}
