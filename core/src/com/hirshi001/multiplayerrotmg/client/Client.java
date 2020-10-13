package com.hirshi001.multiplayerrotmg.client;

import com.badlogic.gdx.math.Vector2;
import com.hirshi001.multiplayerrotmg.client.decoders.PacketDecoder;
import com.hirshi001.multiplayerrotmg.client.encoders.PacketEncoder;
import com.hirshi001.multiplayerrotmg.client.packet.Packet;
import com.hirshi001.multiplayerrotmg.game.Game;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;



public class Client{

    private static int port = 8080;
    private static String host = "2601:646:8401:ad50:14c4:ffd6:dd6:4a37";
    private static Game game;

    private static SocketChannel ch;
    private static volatile boolean isReady = false;


    public static void setClient(Game game, String host, int port) throws Exception {
        Client.game = game;
        Client.port = port;
        Client.host = host;
    }

    public static void setGame(Game game){
        Client.game = game;
    }

    public static void sendPacket(Packet p){
        ch.writeAndFlush(p);
    }

    public static void run(){

        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) {
                    ch.pipeline().addLast(
                            new PacketDecoder(game),
                            new PacketEncoder()
                    );
                    Client.ch = ch;
                }
            });

            ChannelFuture f = b.connect(host, port);
            isReady = true;
            try {
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            workerGroup.shutdownGracefully();
        }

    }

    public static boolean isReady(){
        return isReady;
    }

}
