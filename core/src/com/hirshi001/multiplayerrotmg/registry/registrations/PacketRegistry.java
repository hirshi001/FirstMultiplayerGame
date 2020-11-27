package com.hirshi001.multiplayerrotmg.registry.registrations;


import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.client.packet.UpdateEntityPacket;
import com.hirshi001.multiplayerrotmg.client.packet.UseInventoryItemPacket;
import com.hirshi001.multiplayerrotmg.client.packethandlers.PacketHandler;
import com.hirshi001.multiplayerrotmg.client.packethandlers.UpdateEntityPacketHandler;
import com.hirshi001.multiplayerrotmg.client.packethandlers.UseInventoryItemHandler;
import com.hirshi001.multiplayerrotmg.registry.registries.ExpandableRegistry;
import com.hirshi001.multiplayerrotmg.registry.registrysuppliers.RegistrationSupplier;

import java.util.function.Supplier;

public class PacketRegistry {

    public static final ExpandableRegistry<RegistrationSupplier<? extends Packet>> PACKET_REGISTRY = new ExpandableRegistry<>();
    public static final ExpandableRegistry<PacketHandler> PACKET_HANDLER_REGISTRY = new ExpandableRegistry<PacketHandler>();

    public static final RegistrationSupplier<UseInventoryItemPacket> USE_ONE_PACKET = registerPacket(UseInventoryItemPacket::new, new UseInventoryItemHandler());
    public static final RegistrationSupplier<UpdateEntityPacket> UPDATE_ENTITY_PACKET = registerPacket(UpdateEntityPacket::new, new UpdateEntityPacketHandler());

    public static <T extends Packet> RegistrationSupplier<T> registerPacket(Supplier<T> supplier, PacketHandler packetHandler){
        RegistrationSupplier<T> registration = new RegistrationSupplier<>(supplier);
        registration.setId(PACKET_REGISTRY.register(registration));
        packetHandler.setId(PACKET_HANDLER_REGISTRY.register(packetHandler));
        return registration;
    }

    /*
    public static <T extends PacketHandler> T registerPacketHandler(T packetHandler){
        packetHandler.setId(PACKET_HANDLER_REGISTRY.register(packetHandler));
        return packetHandler;
    }

     */

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
