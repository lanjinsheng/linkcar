package com.idata365.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;


public class DateTools
{
	
	private static FastDateFormat yyMMddHHmmss = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	private static FastDateFormat yyMMddHHmmssSSS = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
	private static FastDateFormat yyyyMMdd = FastDateFormat.getInstance("yyyyMMdd");
	private static FastDateFormat yyyy_MM_dd = FastDateFormat.getInstance("yyyy-MM-dd");
	public static String getYYYYMMDD() {
	  String 	customDate = yyyyMMdd.format(new Date());
	  return customDate;
	}
	public static String getYYYY_MM_DD() {
		  String 	customDate = yyyy_MM_dd.format(new Date());
		  return customDate;
		}
			
    public static long getDiffTimeS(String dateTime1,String dateTime2) throws ParseException {
	       
        Date d1 = DateUtils.parseDate(dateTime1,yyMMddHHmmssSSS.getPattern());
        Date d2 = DateUtils.parseDate(dateTime2,yyMMddHHmmssSSS.getPattern());
       return (long)((d1.getTime()-d2.getTime())/1000);
  }

	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getAddMinuteDateTime(String beginDateTime, int diffdate)
	{
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	public static Date getAddMinuteDateTime(Date curdate, int diffdate)
	{
		Date rt=new Date();
		long myTime = (curdate.getTime() / 1000) + diffdate * 60;
		rt.setTime(myTime * 1000);
		return rt;
	}
	public static Date getAddSecondDateTime(Date curdate, int diffdate)
	{
		Date rt=new Date();
		long myTime = (curdate.getTime() / 1000) + diffdate ;
		rt.setTime(myTime * 1000);
		return rt;
	}

	public static long getDateTimeOfLong(String dateTimeStr)
	{
		if (dateTimeStr==null) return 0;
		java.util.Date da = null;
		try
		{
			da = yyMMddHHmmss.parse(dateTimeStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return da.getTime();
		/*
		 * Date date = Date.valueOf(dateStr); return new
		 * Timestamp(date.getTime());
		 */
	}
	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getAddMinuteDateTime(String beginDateTime, int diffdate,String formate)
	{
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance(formate);
		Date curdate = getDateTimeOfStr(beginDateTime,formate);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	
	public static String getTimesLongToStr(long times){
		Date a=new Date(times);
		return yyMMddHHmmss.format(a);
	}
	public static String getTimesLongToStrBySSS(long times){
		Date a=new Date(times);
		return yyMMddHHmmssSSS.format(a);
	}
	
	/**
	 * 时间间隔返回相差秒数
	 * 
	 * @param  
	 * @return
	 */
	public static int rtDiffSecond(Date beginDateTime, Date endDateTime)
	{
		 
		long begin= (beginDateTime.getTime() / 1000) ;
		long end= (endDateTime.getTime() / 1000) ;
		long left=end-begin;
	    if(left<=0){
	    	return 0;
	    }
		return (int)left;
	}
	
	/**
	 * 时间间隔返回相差秒数
	 * 
	 * @param  
	 * @return
	 */
	public static int rtDiffDay(String beginDateTime, String endDateTime)
	{
		try {
			  Date begin = DateUtils.parseDate(beginDateTime,yyyy_MM_dd.getPattern());
		        Date end = DateUtils.parseDate(endDateTime,yyyy_MM_dd.getPattern());
			 
			  long left=end.getTime()-begin.getTime();
	     
			return (int)(left/(3600*1000*24)+1);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getCurDateAddMinute(int diffdate)
	{
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		Date curdate = new Date();
		long myTime = (curdate.getTime() / 1000) + diffdate * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}

	/**
	 * 返回前天，昨天，明天，后天等 格式为天
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getCurDateAddDay(int diffdate)
	{
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		Date curdate = new Date();
		long myTime = (curdate.getTime() / 1000) + diffdate * 24 * 60 * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}

	/**
	 * 返回前天，昨天，明天，后天等 格式为天
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 * @throws ParseException 
	 */
	public static Date getCurDateAddDayToDate(int diffdate) throws ParseException
	{
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		String day=getCurDateAddDay(diffdate);
		return formatter.parse(day);
	}
	public static String getDateByFormat(Date date)
	{
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		Date curdate = new Date();
		long myTime = date.getTime();
		curdate.setTime(myTime);
		customDate = formatter.format(curdate);
		return customDate;
	}

	public static String getCurDate(String format)
	{
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance(format);
		Date curdate = new Date();
		customDate = formatter.format(curdate);
		return customDate;
	}
	
	public static long changeDateTimeToSecond(String dateTimeStr)
	{
		
		java.util.Date da = null;
		try
		{
			da = yyMMddHHmmssSSS.parse(dateTimeStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return da.getTime()/1000;
		/*
		 * Date date = Date.valueOf(dateStr); return new
		 * Timestamp(date.getTime());
		 */
	}
		public static String getCurDate()
		{
			String customDate = "";
			Date curdate = new Date();
			customDate = yyMMddHHmmssSSS.format(curdate);
			return customDate;
		}
		public static String getCurDateYYYYMMddHHmmss()
		{
			String customDate = "";
			Date curdate = new Date();
			customDate = yyMMddHHmmss.format(curdate);
			return customDate;
		}
		
		
		/**
	 * 把yyyy-MM-dd格式的字符串转换成Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.util.Date getDateTimeOfStr(String dateTimeStr)
	{
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		java.util.Date da = null;
		try
		{
			da = df.parse(dateTimeStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return da;
		/*
		 * Date date = Date.valueOf(dateStr); return new
		 * Timestamp(date.getTime());
		 */
	}
	public static java.util.Date getDateTimeOfStr(String dateTimeStr,String formate)
	{
		FastDateFormat df = FastDateFormat.getInstance(formate);
		java.util.Date da = null;
		try
		{
			da = df.parse(dateTimeStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return da;
		/*
		 * Date date = Date.valueOf(dateStr); return new
		 * Timestamp(date.getTime());
		 */
	}
	public static java.util.Date getDateTimeYYYYMMddHHmmssSSS(String dateTimeStr)
	{
		java.util.Date da = null;
		try
		{
			da = yyMMddHHmmssSSS.parse(dateTimeStr);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return da;
		/*
		 * Date date = Date.valueOf(dateStr); return new
		 * Timestamp(date.getTime());
		 */
	}
	
	/**
	 * 将日期转为月-日 -时-分
	 */
	public static String formatDateMD(Date date)
	{
		String dd = null;
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
			dd = sdf.format(date);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return dd;
	}
	/**
	 * 时间比较，格式为 yyyy-MM-dd HH:mm:ss dateTime1>=dateTime2 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDateTime(String dateTime1, String dateTime2,String format) 
		 {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance(format);
		boolean flag = false;

		try{

//		if (dateTime1.equals(dateTime2)) {
//			return true;
//		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
	
		flag = d2.before(d1);
		}catch( ParseException e){
			e.printStackTrace();
		}
		return flag;
	}
	public static void main(String[] arg)
	{
		 try
		{
			System.out.println(rtDiffDay("2018-06-11","2018-06-10"));
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		 getNowTimeAdd();
	}

 
}
