package com.idata365.app.util;

public class StringTools {
	public static String  getPhoneHidden(String phone) {
		return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2");
	}
}
