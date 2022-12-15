package com.vergilyn.examples.alipay.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class MessageDigestUtils {

	private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	private MessageDigestUtils() {
	}

	public static String getCheckSum(String requestBody, String appSecret, String time) {

		String md5Digest = md5Digest(requestBody);
		String checksum = sha1Digest(appSecret, md5Digest, time);

		return checksum;
	}

	public static String md5Digest(String input) {
		StringBuilder hexValue = new StringBuilder();

		try {

			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] md5Bytes = md5.digest(input.getBytes(StandardCharsets.UTF_8));

			for (byte md5Byte : md5Bytes) {
				int val = ((int) md5Byte) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
		} catch (Exception e) {

		}

		return hexValue.toString();
	}

	public static String sha1Digest(String appSecret, String nonce, String time) {
		String content = appSecret + nonce + time;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("sha1");
			messageDigest.update(content.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

}
