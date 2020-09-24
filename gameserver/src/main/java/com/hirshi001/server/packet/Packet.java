package com.hirshi001.server.packet;
import com.hirshi001.game.Game;
import io.netty.buffer.ByteBuf;

public class Packet {

    private int id;
    private byte[] bytes;
    private Game game;

    public Packet(int id, int size, ByteBuf in, Game game){
        this.id = id;
        bytes = new byte[size];
        in.readBytes(bytes);
        this.game =game;
    }

    public int getId(){
        return id;
    }

    public byte[] getBytes(){
        return bytes;
    }

    public Game getGame(){
        return game;
    }



}
