package com.hirshi001.server;

import com.hirshi001.game.Game;
import com.hirshi001.server.packet.Packet;
import com.hirshi001.server.packet.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;

import java.util.List;

public class PacketServerHandler extends ByteToMessageCodec<Packet> {

    private static final int packetByteSize = 4, packetIdByteSize = 4;

    private Game game;
    public PacketServerHandler(Game game){
        this.game = game;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getId());
        out.writeInt(msg.getBytes().length);
        out.writeBytes(msg.getBytes());

    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        if(in.readableBytes() < packetByteSize + packetIdByteSize)return;

        int packetSize = in.getInt(3);
        if(in.readableBytes() < packetByteSize + packetIdByteSize + packetSize) return;
        Packet packet = new Packet(in.readInt(), in.readInt(), in, game);

        PacketRegistry.handlePacket(packet);
    }
}
