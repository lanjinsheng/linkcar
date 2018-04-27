package com.idata365.app.util;

import org.apache.commons.lang3.StringUtils;

public class PhoneUtils
{
	public static String hidePhone(String phone)
	{
		String prefixPhone = StringUtils.substring(phone, 0, 3);
		String suffixPhone = StringUtils.substring(phone, 7);
		
		String tempNewPhone = prefixPhone + "****" + suffixPhone;
		return tempNewPhone;
	}
}
