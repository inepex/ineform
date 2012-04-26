package com.inepex.ineForm.server.util;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.base64.Base64;


public class ByteConvertUtil {
	
	static final String HEXES = "0123456789ABCDEF";
	static final Charset ASCII = Charset.forName("ASCII");

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
		}
		return hex.toString();
	}
	
	/**
	 * It converts hex string to byte array. If the input param's length is odd than "0" will concetanated to <b>before<b> input string!
	 * @param hexDigits The input hex digits ie.: "12A3559B" or as space spearated "12 A3 55 9B"
	 * @return Converted byte array. ie.: 0x12, 0xA3, 0x55, 0x9B
	 */
	public static byte[] hexStringToByteArray(String s) {
		s = s.replaceAll(" ", "");
		if(s.length()%2 != 0)
			s="0"+s;
		
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	/**
	 * Gives a printable representation of byte array like in the Wireshark
	 * program.
	 * 
	 * Printable ascii except space -> same character 
	 * Other characters -> .
	 */
	public static String wireSharkEscape(byte[] message) {
		if (message == null)
			return null;

		StringBuilder sb = new StringBuilder(message.length);
		for (int i = 0; i < message.length; i++) {

			// first and last printable ASCII chars except space
			if (message[i] >= 33 && message[i] <= 126)
				sb.append((char) message[i]);
			else
				sb.append('.');
		}

		return sb.toString();
	}
	
	/**
	 * Gives a printable representation of the <b>readable begin</b> of {@link ChannelBuffer} like in the Wireshark
	 * program. It buffs size greater then 25 The end of response string will be: ...
	 * 
	 * Printable ascii except space -> same character 
	 * Other characters -> .
	 */
	public static String wireSharkEscapeBeginOf(ChannelBuffer buf) {
		final int cropSize=25;
		int readableBytes = buf.readableBytes();
		int length=Math.min(cropSize, readableBytes);
		
		StringBuilder sb = new StringBuilder(length+3);
		
		for (int i = buf.readerIndex(); i < buf.readerIndex()+length; i++) {

			// first and last printable ASCII chars except space
			byte b = buf.getByte(i);
			if (b >= 33 && b <= 126)
				sb.append((char) b);
			else
				sb.append('.');
		}
		
		if(readableBytes>cropSize)
			sb.append("...");
		
		return sb.toString();
	}
	
	/**
	 * Encodes the content of {@link ChannelBuffer} to Base64. 
	 * WARNING: it read the content of buffer, after this method it will be empty!
	 */
	public static String base64EncodeEatChannelBuffer(ChannelBuffer buf) {
		return Base64.encode(buf, false).toString(ASCII);
	}
	
	public static byte[] base64ToByteArray(String base64Msg) {
		ChannelBuffer buffer = Base64.decode(ChannelBuffers.wrappedBuffer(base64Msg.getBytes(Charset.forName("ASCII"))));
		byte[] msg = new byte[buffer.readableBytes()];
		buffer.readBytes(msg);
		
		return msg;
	}

	/**
	 * Lazy evaluation for logging.
	 */
	public static class WSEscapedMsg {

		private final byte[] message;

		public WSEscapedMsg(byte[] message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return wireSharkEscape(message);
		}
	}
	
	/**
	 * Lazy evaluation for logging.
	 * 
	 * TODO avoid byte array copy
	 */
	public static class Base64Msg{
		
		private final byte[] msg;
		
		public Base64Msg(byte[] msg) {
			this.msg=msg;
		}
		
		@Override
		public String toString() {
			ChannelBuffer ch = ChannelBuffers.wrappedBuffer(Arrays.copyOf(msg, msg.length));
			return base64EncodeEatChannelBuffer(ch);
		}
	}
}
