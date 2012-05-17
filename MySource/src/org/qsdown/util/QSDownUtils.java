package org.qsdown.util;

public class QSDownUtils {
	public static String changeSecToHMS(int second){
		int min = second / 60;
		int hour = min / 60;
		int sec = second % 60;
		return hour + "H" + min + "M" + sec + "S";
	}
	
	public static String chageSecToHMSForMat(int second, String hour, String min, String sec){
		String timeHMS = changeSecToHMS(second);
		timeHMS.replaceAll("H", hour);
		timeHMS.replaceAll("M", min);
		timeHMS.replaceAll("S", sec);
		return timeHMS;
	}
}
