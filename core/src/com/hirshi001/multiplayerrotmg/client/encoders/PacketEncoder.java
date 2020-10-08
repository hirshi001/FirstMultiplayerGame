package com.hirshi001.multiplayerrotmg.client.encoders;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    public static final int PACKET_ID_BYTE_SIZE = 4;
    public static final int PACKET_LENGTH_BYTE_SIZE = 4;

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        byte[] arr = msg.getByteBuf().array();
        out.writeInt(arr.length);
        out.writeInt(msg.getId());
        out.writeBytes(msg.getByteBuf());


    }
}
