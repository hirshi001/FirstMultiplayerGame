package com.hirshi001.multiplayerrotmg.client.decoders;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.game.Game;
import com.hirshi001.multiplayerrotmg.registry.registrations.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.hirshi001.multiplayerrotmg.client.encoders.PacketEncoder.PACKET_LENGTH_BYTE_SIZE;
import static com.hirshi001.multiplayerrotmg.client.encoders.PacketEncoder.PACKET_ID_BYTE_SIZE;

public class PacketDecoder extends ByteToMessageDecoder {

    private Game game;

    public PacketDecoder(Game game){
        this.game = game;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        if(in.readableBytes() < PACKET_LENGTH_BYTE_SIZE) return;
        int packetSize = in.getInt(0);
        if(in.readableBytes() < PACKET_LENGTH_BYTE_SIZE + PACKET_ID_BYTE_SIZE + packetSize) return;
        in.readInt();
        int id = in.readInt();
        int size = in.readInt();
        ByteBuf b = Unpooled.copiedBuffer(in.array(), 0, size);
        Packet packet = new Packet().setId(id).setBytes(b).setGame(game);

        PacketRegistry.PACKET_HANDLER_REGISTRY.getRegistration(id).handlePacket(packet);
    }
}
