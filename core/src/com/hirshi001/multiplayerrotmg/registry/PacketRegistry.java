package com.hirshi001.multiplayerrotmg.registry;

import com.hirshi001.multiplayerrotmg.client.packet.packethandlers.PacketHandler;

import java.util.ArrayList;
import java.util.List;

public class PacketRegistry {

    public static final List<PacketHandler> packetHandlers = new ArrayList<>();

    public static void registerPacketHandler(PacketHandler packetHandler) throws NoSuchFieldException, IllegalAccessException {
        packetHandler.getClass().getDeclaredField("id").setInt(null, packetHandlers.size());
        packetHandlers.add(packetHandler);
    }

    public static PacketHandler getPacketHandler(int id) {
        return packetHandlers.get(id);
    }

    public static<E extends PacketHandler> E getPacketHandler(Class<E> clss) throws NoSuchFieldException, IllegalAccessException {
        int id = clss.getDeclaredField("id").getInt(null);
        return (E)packetHandlers.get(id);
    }


}
