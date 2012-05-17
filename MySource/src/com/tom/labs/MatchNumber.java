package com.tom.labs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class MatchNumber {

	public static boolean match(String number) {
		if (number != null && number.length() > 0) {
			if (Pattern.matches("^[零壹贰叁肆伍陆柒捌玖]*$",number))
				return true;
			if (Pattern.matches("^[0-9]*$",number))
				return true;
		}
		return false;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		InputStreamReader stdin = new InputStreamReader(System.in);
		BufferedReader bufin = new BufferedReader(stdin);
		while (true) {
			System.out.print("请输入(输入quit退出):");
			String number;
			try {
				number = bufin.readLine();
				if ("quit".equals(number)) {
					System.out.println("程序退出......");
					System.exit(0);
				}
				if (match(number)) {
					System.out.println("输入格式正确");
				} else {
					System.out.println("格式不正确");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
