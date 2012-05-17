package com.tom.labs;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class Transform {
	private static String hexString = "0123456789ABCDEF";

	public static String toUnicode(String str) {
		String[] ss = new String[str.length()];
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < str.length(); i++) {
			ss[i] = Integer.toHexString((int) str.charAt(i) & 0xffff);
			sb.append(ss[i]);
		}
		return sb.toString();
	}

	public static String toHexString(String str) {
		byte[] src;
		if (str == null || str.length() <= 0) {
			return null;
		}
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			src = str.getBytes("UTF-8");

			for (int i = 0; i < src.length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					stringBuilder.append(0);
				}
				stringBuilder.append(hv);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return stringBuilder.toString();
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	static String toHex(String str) {

		return null;
	}

	static String toInteger(String str) {
		return null;
	}

	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	public static String toStringHex(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			s = new String(baKeyword, "GBK");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(toUnicode("壹"));
		System.out.println(toHexString("壹"));
		System.out.println(toStringHex("96f6"));
		String pwd;
		InputStreamReader stdin = new InputStreamReader(System.in);// 键盘输入
		BufferedReader bufin = new BufferedReader(stdin);
		try {
			System.out.print("请输入密码,输入quit退出程序");
			while (true) {
				pwd = bufin.readLine();
				if ("quit".equals(pwd)) {
					System.out.println("程序退出......");
					System.exit(0);
				}

			}
		} catch (IOException E) {
			System.out.println("发生I/O错误!!! ");
		}
	}

}
