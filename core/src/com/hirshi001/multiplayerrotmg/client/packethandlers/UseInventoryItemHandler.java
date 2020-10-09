package com.hirshi001.multiplayerrotmg.client.packethandlers;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.gamepieces.inventory.inventoryitem.InventoryItem;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UseInventoryItemHandler extends PacketHandler {


    /**
     * This packet will never be received by the client, therefore there is no way to handle it.
     * @param p
     */
    @Override
    public void handlePacket(Packet p) {

    }


    public static Packet generateUseOnePacket(InventoryItem i, Vector2 usePosition) {

        ByteBuf buff = Unpooled.buffer(12);
        buff.writeInt(i.getId());
        buff.writeFloat(usePosition.x);
        buff.writeFloat(usePosition.y);

        return new Packet().setId(getId()).setBytes(buff);
    }


    public static Packet generateUseTwoPacket(InventoryItem i, Vector2 usePosition) {

        ByteBuf buff = Unpooled.buffer(12);
        buff.writeInt(-i.getId());
        buff.writeFloat(usePosition.x);
        buff.writeFloat(usePosition.y);

        return new Packet().setId(getId()).setBytes(buff);
    }

}
