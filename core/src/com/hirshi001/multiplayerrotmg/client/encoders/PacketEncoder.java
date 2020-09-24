package com.hirshi001.multiplayerrotmg.client.encoders;

import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getId());
        byte[] arr = out.array();
        out.writeInt(arr.length);
        out.writeBytes(arr);
    }
}
