package com.hirshi001.server.packet;

import java.util.ArrayList;

public class PacketRegistry {

    public static final ArrayList<PacketHandler> packetHandlers = new ArrayList<>();

    public static void handlePacket(Packet p){
        packetHandlers.get(p.getId()).handlePacket(p);
    }

    public static void registerPacketHandler(PacketHandler ph, int id){
        ph.setId(id);
        packetHandlers.add(id, ph);
    }





}
