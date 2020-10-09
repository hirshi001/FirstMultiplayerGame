package com.hirshi001.multiplayerrotmg.client.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.util.identifiable.IdentifiableObject;

public abstract class PacketHandler{
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public abstract void handlePacket(Packet p);
}
