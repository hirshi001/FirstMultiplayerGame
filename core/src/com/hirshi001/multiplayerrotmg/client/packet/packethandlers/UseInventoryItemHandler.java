package com.hirshi001.multiplayerrotmg.client.packet.packethandlers;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.inventory.InventoryItem;
import com.hirshi001.multiplayerrotmg.gamepieces.projecticles.GameProjectile;
import com.hirshi001.multiplayerrotmg.registry.EntityRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.List;

public class UseInventoryItemHandler extends PacketHandler {

    public static int id;

    @Override
    public void handlePacket(Packet p) {
        ByteBuf b = p.getByteBuf();
        try {
            GameProjectile projectile = EntityRegistry.getProjectileClass(b.readInt()).newInstance();
            projectile.set(b);
            p.getGame().getField().addProjectile(projectile);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    public static Packet generateUseOnePacket(InventoryItem i, Vector2 usePosition) {

        ByteBuf buff = Unpooled.buffer(12);
        buff.writeInt(i.getId());
        buff.writeFloat(usePosition.x);
        buff.writeFloat(usePosition.y);

        return new Packet().setId(id).setBytes(buff);
    }


    public static Packet generateUseTwoPacket(InventoryItem i, Vector2 usePosition) {

        ByteBuf buff = Unpooled.buffer(12);
        buff.writeInt(-i.getId());
        buff.writeFloat(usePosition.x);
        buff.writeFloat(usePosition.y);

        return new Packet().setId(id).setBytes(buff);
    }

}
