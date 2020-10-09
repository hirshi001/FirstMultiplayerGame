package com.hirshi001.multiplayerrotmg.registry;


import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.client.packet.UseInventoryItemPacket;
import com.hirshi001.multiplayerrotmg.client.packethandlers.PacketHandler;
import com.hirshi001.multiplayerrotmg.client.packethandlers.SpawnProjectileHandler;

public class PacketRegistry {

    public static final ExpandableRegistry<Registration<? extends Packet>> PACKET_REGISTRY = new ExpandableRegistry<Registration<? extends Packet>>();

    public static final Registration<UseInventoryItemPacket> USE_ONE_PACKET = registerPacket(UseInventoryItemPacket::new);


    public static final ExpandableRegistry<PacketHandler> PACKET_HANDLER_REGISTRY = new ExpandableRegistry<PacketHandler>();

    public static final SpawnProjectileHandler SPAWN_GAME_PROJECTILE_HANDLER = registerPacketHandler(new SpawnProjectileHandler());



    public static <T extends Packet> Registration<T> registerPacket(Registration.ObjectCreator<T> oc){
        Registration<T> registration = Registration.registerObject(oc);
        registration.setId(PACKET_REGISTRY.register(registration));
        return registration;
    }

    public static <T extends PacketHandler> T registerPacketHandler(T packetHandler){
        packetHandler.setId(PACKET_HANDLER_REGISTRY.register(packetHandler));
        return packetHandler;
    }

    /*
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


     */


}
