package com.idata365.col;

import java.text.SimpleDateFormat;

public class YingyanTest {
	public static void main(String []args) {
		System.out.println(Date2TimeStamp("2017-11-01 11:15:55.000","yyyy-MM-dd HH:mm:ss.SSS"));
	}
	 public static String Date2TimeStamp(String dateStr, String format) {
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	    }
}
