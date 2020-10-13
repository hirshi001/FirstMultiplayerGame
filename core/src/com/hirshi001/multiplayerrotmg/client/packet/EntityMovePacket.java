package com.hirshi001.multiplayerrotmg.client.packet;

import com.badlogic.gdx.math.Vector2;
import io.netty.buffer.Unpooled;

public class EntityMovePacket extends Packet {

    Vector2 pos;

    public void setMoveToPosition(Vector2 newPos){
        this.pos = newPos;
    }

    @Override
    public void generate() {
        setBytes(Unpooled.buffer(8).writeFloat(pos.x).writeFloat(pos.y));
    }
}
