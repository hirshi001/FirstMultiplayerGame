package com.hirshi001.multiplayerrotmg.client.packet.packethandlers;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.util.identifiable.IdentifiableObject;

public abstract class PacketHandler extends IdentifiableObject {
    public abstract void handlePacket(Packet p);
}
