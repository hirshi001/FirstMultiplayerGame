package com.hirshi001.multiplayerrotmg.client.decoders;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.game.Game;
import com.hirshi001.multiplayerrotmg.registry.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private final int packetIdByteSize = 4;
    private final int packetByteSize = 4;

    private Game game;

    public PacketDecoder(Game game){
        this.game = game;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < packetByteSize + packetIdByteSize)return;

        int packetSize = in.getInt(3);
        if(in.readableBytes() < packetByteSize + packetIdByteSize + packetSize) return;
        int id = in.readInt();
        int size = in.readInt();
        ByteBuf b = Unpooled.copiedBuffer(in);
        Packet packet = new Packet().setId(id).setBytes(b).setGame(game);

        PacketRegistry.getPacketHandler(id).handlePacket(packet);
    }
}
