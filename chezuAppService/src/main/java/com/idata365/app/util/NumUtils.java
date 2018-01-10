package com.idata365.app.util;

import java.text.NumberFormat;

public class NumUtils
{
	public static String formattedDecimalToPercentage(double decimal)  
    {  
        //获取格式化对象  
        NumberFormat nt = NumberFormat.getPercentInstance();  
        //设置百分数精确度 即保留一位小数  
        nt.setMinimumFractionDigits(2);  
        return nt.format(decimal);  
    }  
}
