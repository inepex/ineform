package com.inepex.ineForm.server.util;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;

/**
 * Class to test the changing of {@link io.netty.buffer.ByteBuf}.
 * 
 * Example:
 * <pre>
 * ChannelBufferInfo cbInfo = ChannelBufferInfo.createFromBuff(channelBuffer);
 * 
 * //do something with channel what doesn't have to modify it
 * cb.getByte(cb.readerIndex());
 * 
 * Assert.assertEquals(cbInfo, ChannelBufferInfo.createFromBuff(channelBuffer));
 * </pre>
 */
public class ChannelBufferInfo {
	
	private int readableBytes;
	private int readerIndex;
	private int writerIndex;
	private byte[] bytes;
	
	private ChannelBufferInfo(){
	}
	
	public static ChannelBufferInfo createFromBuff(ByteBuf buff) {
		ChannelBufferInfo i = new ChannelBufferInfo();
		i.readableBytes=buff.readableBytes();
		i.readerIndex=buff.readerIndex();
		i.writerIndex=buff.writerIndex();
		i.bytes=new byte[i.readableBytes];
		buff.getBytes(i.readerIndex, i.bytes);
		return i;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChannelBufferInfo other = (ChannelBufferInfo) obj;
		if (!Arrays.equals(bytes, other.bytes))
			return false;
		if (readableBytes != other.readableBytes)
			return false;
		if (readerIndex != other.readerIndex)
			return false;
		if (writerIndex != other.writerIndex)
			return false;
		return true;
	}
	
	
}