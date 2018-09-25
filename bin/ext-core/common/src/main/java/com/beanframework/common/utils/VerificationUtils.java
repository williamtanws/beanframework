package com.beanframework.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * VerificationUtils 安全验证工具类
 *
 */
public class VerificationUtils extends CommonUtils {

    /**
     * @param length
     * @return
     */
    public static String getRandomNumber(int length) {
        StringBuffer sb = new StringBuffer();
        while (0 < length) {
            length -= 1;
            sb.append(r.nextInt(9));
        }
        return sb.toString();
    }

    /**
     * @param input
     * @return
     */
    public static String encode(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input.getBytes());
            return typeToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            return input;
        }
    }

    /**
     * @param buffer
     * @return
     */
    private static String typeToHex(byte buffer[]) {
        StringBuffer sb = new StringBuffer(buffer.length * 2);
        for (int i = 0; i < buffer.length; i++) {
            sb.append(Character.forDigit((buffer[i] & 240) >> 4, 16));
            sb.append(Character.forDigit(buffer[i] & 15, 16));
        }
        return sb.toString();
    }
}