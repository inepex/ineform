package com.inepex.ineForm.server.util;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.junit.Assert;
import org.junit.Test;


public class ByteConvertUtilTest {

	private final byte[] msg0 = new byte[]{-3, -2, -1, 0, 1, 2 ,3};
	
	private final byte[] msg1 = new byte[] { 36, 36, 66, 49, 50, 53, 44, 51, 53, 57, 50, 51, 49, 48, 51, 53, 55, 53,
			53, 48, 51, 48, 44, 65, 65, 65, 44, 50, 57, 44, 52, 55, 46, 53, 48, 54, 51, 49, 54, 44, 49, 57, 46, 48, 52,
			57, 50, 54, 56, 44, 49, 49, 49, 50, 48, 53, 48, 57, 49, 55, 49, 51, 44, 86, 44, 48, 44, 57, 57, 44, 48, 44,
			48, 44, 48, 44, 52, 52, 44, 48, 44, 49, 54, 53, 50, 44, 48, 124, 48, 124, 48, 48, 48, 48, 124, 48, 48, 48,
			48, 44, 48, 48, 48, 48, 44, 48, 48, 48, 55, 124, 48, 48, 48, 56, 124, 124, 48, 50, 68, 55, 124, 48, 49, 48,
			49, 44, 42, 69, 51 };

	private final byte[] msg2 = new byte[] { 36, 36, 0, 125, 16, 0, 31, -1, -1, -1, -1, -103, 85, 49, 52, 49, 48, 50,
			50, 46, 48, 48, 48, 44, 86, 44, 50, 50, 52, 50, 46, 48, 50, 51, 52, 44, 78, 44, 49, 49, 51, 53, 57, 46, 48,
			57, 49, 52, 44, 69, 44, 48, 46, 48, 48, 44, 48, 44, 50, 54, 48, 57, 49, 49, 44, 44, 42, 49, 57, 124, 48,
			46, 48, 124, 49, 50, 55, 124, 48, 48, 48, 48, 124, 48, 48, 48, 56, 44, 48, 48, 48, 54, 124, 48, 48, 68, 56,
			48, 48, 52, 54, 48, 48, 52, 50, 51, 48, 54, 49, 124, 48, 66, 124, 48, 48, 48, 48, 48, 48, 48, 48, 27, -87 };

	private final String msg1WS = "$$B125,359231035755030,AAA,29,47.506316,19.049268,111205091713,V,"
			+ "0,99,0,0,0,44,0,1652,0|0|0000|0000,0000,0007|0008||02D7|0101,*E3";
	
	private final String msg2WS = "$$.}........U141022.000,V,2242.0234,N,11359.0914,E,0.00,0,260911,,*19|0.0|127|0000|0008,"
			+ "0006|00D8004600423061|0B|00000000..";
	
	private final String msg1Hexa = "2424423132352C3335393233313033353735353033302C4141412"
			+ "C32392C34372E3530363331362C31392E3034393236382C3131313230353039313731332C56"
			+ "2C302C39392C302C302C302C34342C302C313635322C307C307C303030307C303030302C3030"
			+ "30302C303030377C303030387C7C303244377C303130312C2A4533";
	
	private final String msg2Hexa = "2424007D10001FFFFFFFFF9955313431303"
			+ "2322E3030302C562C323234322E303233342C4E2C31313335392E3039313"
			+ "42C452C302E30302C302C3236303931312C2C2A31397C302E307C3132377C303"
			+ "030307C303030382C303030367C303044383030343630303432333036317C30427C30303030303030301BA9";
	
	@Test
	public void testHexa() {
		for(int i=0; i<256; i++) {
			Assert.assertEquals("i="+i,
					ByteConvertUtil.HEXES.charAt(i / 16)+""+ByteConvertUtil.HEXES.charAt(i % 16), 
					ByteConvertUtil.getHex(new byte[]{(byte)i}));
		}
		
		Assert.assertEquals(msg1Hexa.length(), msg1.length * 2);
		Assert.assertEquals(msg2Hexa.length(), msg2.length * 2);
		Assert.assertEquals(msg1Hexa, ByteConvertUtil.getHex(msg1));
		Assert.assertEquals(msg2Hexa, ByteConvertUtil.getHex(msg2));
	}
	
	@Test
	public void testWSEscape() {
		Assert.assertEquals(msg1WS.length(), msg1.length);
		Assert.assertEquals(msg2WS.length(), msg2.length);
		
		Assert.assertEquals(msg1WS, ByteConvertUtil.wireSharkEscape(msg1));
		Assert.assertEquals(msg1WS, new ByteConvertUtil.WSEscapedMsg(msg1).toString());
		Assert.assertEquals(msg2WS, ByteConvertUtil.wireSharkEscape(msg2));
		Assert.assertEquals(msg2WS, new ByteConvertUtil.WSEscapedMsg(msg2).toString());
	}
	
	@Test
	public void testWireSharkEscapeBeginOf() {
		ChannelBuffer cb = ChannelBuffers.wrappedBuffer(msg0);
		ChannelBufferInfo cbInfo = ChannelBufferInfo.createFromBuff(cb);
		Assert.assertEquals(ByteConvertUtil.wireSharkEscape(msg0), ByteConvertUtil.wireSharkEscapeBeginOf(cb));
		Assert.assertEquals(cbInfo, ChannelBufferInfo.createFromBuff(cb));
		
		ChannelBuffer cb1 = ChannelBuffers.wrappedBuffer(msg1);
		ChannelBufferInfo cb1Info = ChannelBufferInfo.createFromBuff(cb1);
		String res = ByteConvertUtil.wireSharkEscapeBeginOf(cb1);
		String ws = ByteConvertUtil.wireSharkEscape(msg1);
		Assert.assertEquals(cb1Info, ChannelBufferInfo.createFromBuff(cb1));
		Assert.assertEquals(28, res.length());
		Assert.assertEquals(ws.substring(0, 25), res.subSequence(0, 25));
		Assert.assertEquals("...", res.subSequence(25, res.length()));
	}
	
	@Test
	public void hexStringToByteArrayTest() {
		Assert.assertArrayEquals(new byte[]{0x04}, ByteConvertUtil.hexStringToByteArray("4"));
		Assert.assertArrayEquals(new byte[]{0x24}, ByteConvertUtil.hexStringToByteArray("24"));
		Assert.assertArrayEquals(new byte[]{0x24, 0x24, 0x00}, ByteConvertUtil.hexStringToByteArray("24 24 00"));
		Assert.assertArrayEquals(msg1, ByteConvertUtil.hexStringToByteArray(msg1Hexa));
		Assert.assertArrayEquals(msg2, ByteConvertUtil.hexStringToByteArray(msg2Hexa));
	}
	
	@Test
	public void getHexDigitsAsByteArrayTest() {
		for(int i=0; i<=0xff; i++) {
			byte b = (byte) i;
			Assert.assertArrayEquals("current byte="+b, new byte[]{b}, ByteConvertUtil.hexStringToByteArray(
					ByteConvertUtil.getHex(new byte[]{b})));
			
			Assert.assertArrayEquals("current double byte="+b, new byte[]{b,b}, ByteConvertUtil.hexStringToByteArray(
					ByteConvertUtil.getHex(new byte[]{b,b})));
		}
	}
	
	
	@Test 
	public void testChannelBufferInfo(){
		//unmod test
		ChannelBuffer cb = ChannelBuffers.wrappedBuffer(msg0);
		ChannelBufferInfo cbInfo = ChannelBufferInfo.createFromBuff(cb);
		cb.getByte(cb.readerIndex()+1);
		cb.getByte(cb.readerIndex()+2);
		cb.getByte(cb.readerIndex()+3);
		Assert.assertEquals(cbInfo, ChannelBufferInfo.createFromBuff(cb));
		
		//mod test
		ChannelBuffer cb2 = ChannelBuffers.wrappedBuffer(msg0);
		ChannelBufferInfo cb2Info = ChannelBufferInfo.createFromBuff(cb2);
		cb2.readByte();
		Assert.assertNotSame(cb2Info, ChannelBufferInfo.createFromBuff(cb2));
	}
}


