package com.hirshi001.server.packet;

public abstract class PacketHandler {

    private int id;

    public final void setId(int id){
        this.id = id;
    }

    public final int getId(){
        return id;
    }


    public abstract void handlePacket(Packet p);




}
