package com.him188.jpre.network;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;

/**
 * @author Him188
 */
public class NetworkChannelHandler extends ChannelInitializer<Channel> {
	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		//pipeline.addLast("framer", new DelimiterBasedFrameDecoder(1024, Delimiters.lineDelimiter()));
		//pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
		pipeline.addLast("bytesDecoder", new ByteArrayDecoder());

		//pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
		pipeline.addLast("bytesEncoder", new ByteArrayEncoder());

		pipeline.addLast("handler", new NetworkPacketHandler());
	}
}

