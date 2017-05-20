package net.mamoe.jpre.network;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.mamoe.jpre.Frame;
import net.mamoe.jpre.JPREMain;
import net.mamoe.jpre.utils.Utils;
import net.mamoe.jpre.binary.BinaryStream;
import net.mamoe.jpre.event.frame.FrameConnectionEvent;
import net.mamoe.jpre.network.packet.Protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络数据包接收器. 该类属于网络层, 插件一般不需要使用
 *
 * @author Him188 @ JPRE Project */
public class NetworkPacketHandler extends SimpleChannelInboundHandler<byte[]> {
    private JPREMain jpre;

    public JPREMain getJPREMain() {
        return jpre;
    }

    NetworkPacketHandler(JPREMain jpre) {
        this.jpre = jpre;
    }


    private static List<MPQClient> clients = new ArrayList<>();

    public static List<MPQClient> getClients() {
        return clients;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, byte[] data) throws Exception {
        synchronized (this) {
            handlePacket(ctx, data);
        }
    }

    private byte[] temp = new byte[0];


    /**
     * Synchronized by {@code synchronized (this)} in {@link #channelRead0}
     */
    private void handlePacket(ChannelHandlerContext ctx, byte[] data) {
        try {
            temp = Utils.arrayAppend(temp, data);
            while (temp.length != 0) {
                int position = Utils.arraySearch(temp, Protocol.SIGNATURE);
                if (position < 0) {
                    return;//收到的是子包, 数据未结尾
                }

                byte[] d = Utils.arrayGetCenter(temp, 0, position);
                temp = Utils.arrayDelete(temp, position + Protocol.SIGNATURE.length);

                JPREMain.getServerScheduler().scheduleTask(() -> processPacket(ctx, d));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    //TODO 改为 public, 并将 ctx 改为插件可扩展的消息源以实现多源化
    private void processPacket(ChannelHandlerContext ctx, byte[] data) {
        if (data.length == 0 || Utils.isZeroArray(data)) {
            return;
        }
        processPacket(ctx, new BinaryStream(data));
    }

    private void processPacket(ChannelHandlerContext ctx, BinaryStream stream) {
        //System.out.println(stream);
        for (MPQClient client : clients) {
            if (client.is((InetSocketAddress) ctx.channel().remoteAddress())) {
                client.getFrame().getScheduler().scheduleTask(null, () -> {
                    try {
                        client.dataReceive(stream);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                });
                return;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) { //远程主机强迫关闭了一个现有的连接
            return;
        }

        super.exceptionCaught(ctx, cause);

        // TODO: 2017/3/22  配置是否显示错误信息.
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        FrameConnectionEvent event = null;
        for (MPQClient client : clients) {
            if (client.is((InetSocketAddress) ctx.channel().remoteAddress())) {
                event = new FrameConnectionEvent(client.getFrame());
                client.setAddress((InetSocketAddress) ctx.channel().remoteAddress());
                client.setCtx(ctx);
                break;
            }
        }

        if (event == null) {
            Frame frame = new Frame(getJPREMain());
            MPQClient client = new MPQClient(frame, (InetSocketAddress) ctx.channel().remoteAddress(), ctx);
            clients.add(client);
            event = new FrameConnectionEvent(frame);
        }

        event.getFrame().getPluginManager().callEvent(event);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[Network] RemoteClient: " + ctx.channel().remoteAddress() + " disconnected.");
        super.channelInactive(ctx);
    }
}