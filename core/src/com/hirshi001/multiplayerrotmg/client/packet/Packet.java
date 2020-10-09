package com.hirshi001.multiplayerrotmg.client.packet;

import com.hirshi001.multiplayerrotmg.game.Game;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class Packet {

    private ByteBuf buf;
    private Game game;
    private int id;

    public Packet(){}

    public Packet setBytes(ByteBuf buf) {
        this.buf = buf;
        return this;
    }

    public Packet setGame(Game game) {
        this.game = game;
        return this;
    }

    public Packet setId(int id){
        this.id = id;
        return this;
    }

    public int getId(){return id;}

    public ByteBuf getByteBuf(){
        return buf;
    }

    public Game getGame(){
        return game;
    }

    /**
     * Sets the bytes in the packet pased on the information given
     */
    public void generate(){
        buf = Unpooled.buffer(4);
        buf.writeInt(id);
    }





}
