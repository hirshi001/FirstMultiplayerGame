package com.hirshi001.multiplayerrotmg.client.packet;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.gamepieces.inventory.inventoryitem.InventoryItem;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UseInventoryItemPacket extends Packet{

    private InventoryItem item;
    private int inventoryIndex;
    private Vector2 usePosition;

    public UseInventoryItemPacket setItem(InventoryItem item) {
        this.item = item;
        return this;
    }

    public UseInventoryItemPacket setInventoryIndex(int inventoryIndex) {
        this.inventoryIndex = inventoryIndex;
        return this;
    }

    public UseInventoryItemPacket setUsePosition(Vector2 usePosition) {
        this.usePosition = usePosition;
        return this;
    }

    @Override
    public void generate() {
        ByteBuf b = Unpooled.buffer(12);
        b.writeInt(item.getId());
        b.writeInt(inventoryIndex);
        b.writeFloat(usePosition.x);
        b.writeFloat(usePosition.y);
        setBytes(b);
    }
}
