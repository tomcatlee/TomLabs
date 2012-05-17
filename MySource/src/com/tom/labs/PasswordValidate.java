package com.tom.labs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidate {

	static boolean validate(String pwd) {

		int lCount = 0;
		int uCount = 0;
		int nCount = 0;
		if (pwd != null && pwd.length() >= 8) {
			Matcher match = Pattern.compile("[a-z]").matcher(pwd);
			while (match.find())
				lCount++;
			match = Pattern.compile("[A-Z]").matcher(pwd);
			while (match.find())
				uCount++;
			match = Pattern.compile("\\d").matcher(pwd);
			while (match.find())
				nCount++;
			if (uCount > 0 && nCount > 2 && (lCount+uCount+nCount)==pwd.length())
				return true;
		}
		return false;
	}

	static String validatePassword() {
		String pwd = "";
		InputStreamReader stdin = new InputStreamReader(System.in);// 键盘输入
		BufferedReader bufin = new BufferedReader(stdin);
		try {
			System.out.print("请输入密码,输入quit退出程序");
			while(true) {
				pwd = bufin.readLine();
				if("quit".equals(pwd)) {
					System.out.println("程序退出......");
					System.exit(0);
				}
				if(validate(pwd)) {
					System.out.println("密码可用");
				} else {
					System.out.println("密码不可用");
				}
			}
		} catch (IOException E) {
			System.out.println("发生I/O错误!!! ");
		}
		return pwd;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		validatePassword();
	}
}
