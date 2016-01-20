package com.inepex.ineForm.server.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;

import java.nio.charset.Charset;

public class ByteConvertUtil {

    static final String HEXES = "0123456789ABCDEF";
    public static final Charset ASCII = Charset.forName("ASCII");

    public static String getHex(int length, byte... raw) {
        if (raw == null) {
            return null;
        }

        if (raw.length < length)
            length = raw.length;

        final StringBuilder hex = new StringBuilder(2 * raw.length);
        for (int i = 0; i < length; i++) {
            byte b = raw[i];
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }

        return hex.toString();
    }

    public static String getHex(byte... raw) {
        if (raw == null)
            return null;
        return getHex(raw.length, raw);
    }

    /**
     * It converts hex string to byte array. If the input param's length is odd
     * than "0" will concetanated to <b>before<b> input string!
     * 
     * @return Converted byte array. ie.: 0x12, 0xA3, 0x55, 0x9B
     */
    public static byte[] hexStringToByteArray(String s) {
        s = s.replaceAll(" ", "");
        if (s.length() % 2 != 0)
            s = "0" + s;

        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static byte[] base64ToByteArray(String base64Msg) {
        ByteBuf buffer = Base64
            .decode(Unpooled.wrappedBuffer(base64Msg.getBytes(Charset.forName("ASCII"))));
        byte[] msg = new byte[buffer.readableBytes()];
        buffer.readBytes(msg);
        buffer.release();

        return msg;
    }

    public static long getBigEndianValue(byte[] b) {
        long value = 0;
        for (int i = 0; i < b.length; i++) {
            value += ((long) b[i] & 0xff) << (8 * i);
        }
        return value;
    }

    public static boolean byteArrayContains(byte[] container, byte[] contained) {
        if (contained.length == 0)
            return true;
        if (contained.length > container.length)
            return false;
        for (int i = 0; i < container.length; i++) {
            if (contained[0] != container[i])
                continue;

            if (contained.length == 1)
                return true;
            if (contained.length > container.length - i)
                return false;

            boolean equals = true;
            for (int j = 1; j < contained.length; j++) {
                if (container[j + i] != contained[j]) {
                    equals = false;
                    break;
                }
            }
            if (equals)
                return true;

        }

        return false;
    }
}
