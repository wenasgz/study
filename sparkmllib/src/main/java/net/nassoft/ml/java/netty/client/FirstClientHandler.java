package net.nassoft.ml.java.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @author chao.yu
 * chao.yu@dianping.com
 * @date 2018/08/04 06:23.
 */
public class FirstClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");

        // 1.获取数据
        ByteBuf buffer = getByteBuf(ctx);

        // 2.写数据
        ctx.channel().writeAndFlush(buffer);
    }

    private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
        byte[] bytes = "我是客户端,我是客户端,over!".getBytes(Charset.forName("utf-8"));
        byte[] bytes2 = "我是客户端2,我是客户端2,over!".getBytes(Charset.forName("utf-8"));
        ByteBuf buffer = ctx.alloc().buffer();

        buffer.writeBytes(bytes);
        buffer.writeBytes(bytes2);

        return buffer;
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf) msg;

        System.out.println(new Date() + ": 客户端读到数据 -> " + byteBuf.toString(Charset.forName("utf-8")));
    }
}
