package com.seekon.mars.osgi.util;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
	private static final char HEX[] = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	
	public static String getMD5HashHex16String(String s) {
		return getMD5HashHex32String(s).substring(8, 24);
	}
	
	public static String getMD5HashHex32String(String s) {
		if (s == null) 
			s = "";
		return toHex32String(toMD5HashByte16String(s.getBytes()));
	}

	public static byte[] toMD5HashByte16String(byte[] bytes) {
		if (bytes == null) 
			bytes = new byte[0];
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
		}
		md.update(bytes);
		return md.digest();
	}

	private static String toHex32String(byte bytes[]) {
		int len = bytes.length * 2;
		char[] re = new char[len];
		for (int i=0, end=bytes.length; i < end; i++) {
			re[i*2] = HEX[(bytes[i] & 0xf0) >> 4];
			re[i*2+1] = HEX[bytes[i] & 0x0f];
		}
		return new String(re);
	}
	
	public static void main(String[] args) throws IOException {
//		for (int i=0; i<100; i++) {
//			long one = System.currentTimeMillis();
//			String code = "admin";
//			String md5Encode = getMD5HashHex32String(code);
//			System.out.println(String.format("%s: %s", code, md5Encode));
//			long two = System.currentTimeMillis();
//			System.out.println(two - one);
//			
//			one = System.currentTimeMillis();
//			code = "admin2";
//			md5Encode = getMD5HashHex32String(code);
//			System.out.println(String.format("%s: %s", code, md5Encode));
//			two = System.currentTimeMillis();
//			System.out.println(two - one);
//		}
		
		System.out.println(getMD5HashHex32String(""));
		System.out.println(getMD5HashHex32String(null));
		System.out.println(toHex32String(toMD5HashByte16String(new byte[]{})));
		System.out.println(toHex32String(toMD5HashByte16String(new byte[0])));
		System.out.println(toHex32String(toMD5HashByte16String(null)));
		
		System.out.println(getMD5HashHex16String(""));
		System.out.println(getMD5HashHex16String("admin"));
	}
}
