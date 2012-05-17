package com.tom.labs;
/**
 * 判断是不是回文数
 * @author weli
 *
 */
public class ValidatePalindromeNumber {
	static boolean validate(String str) {
		if (str == null || str.equals(""))
			return false;
		boolean flag = true;
		for (int i = 0; i < str.length()/2 && flag; i++) {
			flag &= (str.charAt(i) == str.charAt(str.length() - i - 1));
		}
		return flag;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(validate("2442"));
	}

}
