package com.idata365.util;

public class ValidTools {
	public static boolean isBlank(String str) {
		return (str==null || str.equals(""));
	}
	
	public static boolean isNotBlank(String str) {
		return (str!=null && !("").equals(str));
	}
}
