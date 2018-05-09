package com.ljs.util;

import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

public class CommentUtil {
	public static boolean isSocket=false;
	public static boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (dir.exists()) {  
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");  
            return false;  
        }  
        if (!destDirName.endsWith(File.separator)) {  
            destDirName = destDirName + File.separator;  
        }  
        //创建目录  
        if (dir.mkdirs()) {  
            System.out.println("创建目录" + destDirName + "成功！");  
            return true;  
        } else {  
            System.out.println("创建目录" + destDirName + "失败！");  
            return false;  
        }  
    }  
	/**
	 * 把yyyy-MM-dd格式的字符串转换成Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static java.util.Date getDateTimeOfStr(String dateTimeStr) {
		FastDateFormat df = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
		java.util.Date da = null;
		try {
			da = df.parse(dateTimeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return da;
		/*
		 * Date date = Date.valueOf(dateStr); return new
		 * Timestamp(date.getTime());
		 */
	}
	public static java.util.Date getDateTimeOfStr(String dateTimeStr,String format) {
		FastDateFormat df = FastDateFormat.getInstance(format);
		java.util.Date da = null;
		try {
			da = df.parse(dateTimeStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return da;
	
	}
	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getCustomDateTime(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60 * 60 * 24;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	public static String getCustomDateTimeSS(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	/**
	 *  
	 * 
	 * @param diffdate
	 *            
	 * @return
	 */
	public static String getCustomDateTimeHH(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60 * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	/**
	 *  
	 * 
	 * @param diffdate
	 *            
	 * @return
	 */
	public static String getCustomDateTimeDay(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60 * 60 * 24;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	
    /**
     * 返回时间差
     * 
     * @param 
     * @return
     */
    public static int getDayDiff(String fromDay,String toDay) {
        FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
        Date dFromDay=null;
        Date dToDay=null;
		try {
			dFromDay = formatter.parse(fromDay);
			 dToDay =formatter.parse(toDay) ;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        long myTime = (dToDay.getTime())-(dFromDay.getTime());
        int day=new BigDecimal(myTime).divide(new BigDecimal(24*60*60*1000)).intValue();
//        int day=(int) myTime/(24*60*60*1000);
        return day;
    }
    public static String getDayStr(String dayStr,String format){
    	FastDateFormat sdf = FastDateFormat.getInstance(format);
		try {
			return sdf.format(sdf.parse(dayStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

    
	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getAddMinuteDateTime(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	public static String getAddMinuteDateTime(String beginDateTime, int diffdate,String format) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance(format);
		Date curdate = getDateTimeOfStr(beginDateTime, format);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}
	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getAddDayDate(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd");
		Date curdate = null;
		try {
			curdate = formatter.parse(beginDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long myTime = (curdate.getTime()) + diffdate *24*60*60*1000;
		curdate.setTime(myTime);
		customDate = formatter.format(curdate);
		return customDate;
	}
	public static String subMonth(String date,int diff)  { 
		String reStr=date;
	
		try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
        Date dt = sdf.parse(date);  
        Calendar rightNow = Calendar.getInstance();  
        rightNow.setTime(dt);  
  
        rightNow.add(Calendar.MONTH, diff);  
        Date dt1 = rightNow.getTime();  
         reStr = sdf.format(dt1);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return reStr;  
    }  

	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String getCustomDate(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		Date curdate = getDateTimeOfStr(beginDateTime);
		long myTime = (curdate.getTime() / 1000) + diffdate * 60 * 60 * 24;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}

	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String addYear(String beginDateTime, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		Date curdate = null;
		try {
			curdate = formatter.parse(beginDateTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		curdate.setYear((curdate.getYear() + diffdate));
		customDate = formatter.format(curdate);
		return customDate;
	}
	
	public static String changeDayFormat(String day,String fFmt,String tFmt){
		try {
		FastDateFormat formatter = FastDateFormat.getInstance(fFmt);
		Date curdate = formatter.parse(day);
		formatter = FastDateFormat.getInstance(tFmt);
		return formatter.format(curdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}

	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String addDate(String beginDate, int diffdate) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		Date curdate = null;
		try {
			curdate = formatter.parse(beginDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long myTime = (curdate.getTime() / 1000) + diffdate * 60 * 60 * 24;
		curdate.setTime(myTime * 1000);
		customDate = formatter.format(curdate);
		return customDate;
	}

	/**
	 * 返回天数差
	 * 
	 * @param 
	 *            于今天相差的天数
	 * @return
	 */
	public static int diffDay(String beginDate, String endDate) {
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy-MM-dd");
		try {
			Date d1 = formatter.parse(beginDate);
			Date d2 = formatter.parse(endDate);
			int diffDate = (int) ((d2.getTime()-d1.getTime())/(1000*60 * 60 * 24));
			return diffDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return 0;
	}
	
	
	/**
	 * 年数比较
	 * 
	 * @param 
	 *            
	 * @return
	 */
	public static int diffYear(String earlyDate, String ssnf) {
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy");
		try {
			Date d1 = formatter.parse(earlyDate);
			Date d2 = formatter.parse(ssnf);
			Calendar c = Calendar.getInstance();
			c.setTime(d1);
			int y1=c.get(Calendar.YEAR);
			c.setTime(d2);
			int y2=c.get(Calendar.YEAR);
			int diffDate = y1-y2;
			return diffDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	     return 0;
	}
	private static FastDateFormat dateTimeFormat = FastDateFormat
			.getInstance("yyyy-MM-dd HH:mm:ss");
	private static FastDateFormat dateFormat = FastDateFormat
			.getInstance("yyyy-MM-dd");
	
	private static FastDateFormat dateFormatDay = FastDateFormat
			.getInstance("yyyyMMdd");
	private static FastDateFormat dateTimeFormat2 = FastDateFormat
			.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static boolean  isInHour(String  sDateTime,String eDateTime){
		try{
			Date s=dateTimeFormat.parse(sDateTime);
		
			Date e=dateTimeFormat.parse(eDateTime);
			long diff=e.getTime()-s.getTime();
			if(0<diff && diff<3600*1000){
				return true;
			}
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static boolean  isIn24Hour(String  sDateTime,String eDateTime){
		try{
			Date s=dateTimeFormat.parse(sDateTime);
		
			Date e=dateTimeFormat.parse(eDateTime);
			long diff=e.getTime()-s.getTime();
			if(0<diff && diff<3600*1000*24){
				return true;
			}
		}catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr() {
		return dateTimeFormat.format(new Date());
	}
	public static String getCurDayStr2() {
		return dateFormatDay.format(new Date());
	}
	public static String getCurDayStr() {
		return dateFormat.format(new Date());
	}
	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss.SSS
	 * 
	 * @return
	 */
	public static String getCurDateTimeStr2() {
		return dateTimeFormat2.format(new Date());
	}

	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurDate() {
		return dateFormat.format(new Date());
	}

	/**
	 * 取得当前的时间，格式为 yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurDate(String dayTime) {
		try {
			return dateFormat.format(dateFormat.parse(dayTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dayTime;
	}

	/**
	 * 时间比较，格式为 yyyy-MM-dd HH:mm:ss dateTime1>=dateTime2 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDateTime(String dateTime1, String dateTime2)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;

		if (dateTime1.equals(dateTime2)) {
			return true;
		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
		flag = d2.before(d1);

		return flag;
	}

	/**
	 * 时间比较，格式为 yyyy-MM-dd HH:mm:ss dateTime1>=dateTime2 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDateTime(String dateTime1, String dateTime2,String format)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance(format);
		boolean flag = false;

//		if (dateTime1.equals(dateTime2)) {
//			return true;
//		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
	
		flag = d2.before(d1);

		return flag;
	}
	public static int compareDateTimeSecond(String dateTime1, String dateTime2,String format)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance(format);
//		if (dateTime1.equals(dateTime2)) {
//			return true;
//		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
	
		int rt=(int)(d2.getTime()-d1.getTime())/1000;

		return rt;
	}
	/**
	 * 时间比较，格式为 yyyy-MM-dd HH:mm:ss dateTime1>=dateTime2 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDateTimeEQ(String dateTime1, String dateTime2,String format)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance(format);
		boolean flag = false;

		if (dateTime1.equals(dateTime2)) {
			return true;
		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
		if(d1.getTime()==d2.getTime()){
			return true;
		}
		flag = d2.before(d1);

		return flag;
	}
 
	/**
	 * 时间比较，格式为 yyyy-MM-dd HH:mm:ss dateTime1-dateTime2 >=ss ms 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDateTime(String dateTime1, String dateTime2,int ms)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		boolean flag = false;

		if (dateTime1.equals(dateTime2)) {
			return false;
		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
		if((d1.getTime()-d2.getTime())>ms)
			return true;
		return flag;
	}
	/**
	 * 返回前天，昨天，明天，后天等
	 * 
	 * @param diffdate
	 *            于今天相差的天数
	 * @return
	 */
	public static String addCustomDateTimeS(String beginDateTime, int diffS) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
		java.util.Date curdate = null;
		try {
			curdate = formatter.parse(beginDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long myTime = (curdate.getTime()) + diffS*1000;
		curdate.setTime(myTime);
		customDate = formatter.format(curdate);
		return customDate;
	}
	public static String addCustomDateTimeH(String beginDateTime, int diffH) {
		String customDate = "";
		FastDateFormat formatter = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss");
		java.util.Date curdate = null;
		try {
			curdate = formatter.parse(beginDateTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		long myTime = (curdate.getTime()) + diffH*3600*1000;
		curdate.setTime(myTime);
		customDate = formatter.format(curdate);
		return customDate;
	}

	/**
	 * 时间比较，格式为 yyyy-MM-dd HH:mm:ss.SSS dateTime1>=dateTime2 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDateTimeSSS(String dateTime1, String dateTime2)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance("yyyy-MM-dd HH:mm:ss.SSS");
		boolean flag = false;

		if (dateTime1.equals(dateTime2)) {
			return true;
		}
		Date d1 = DateUtils.parseDate(dateTime1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(dateTime2, fastDateFormat.getPattern());
		flag = d2.before(d1);

		return flag;
	}

	/**
	 * 时间比较，格式为 yyyy-MM-dd date1>date2 返回true
	 * 
	 * @return
	 * @throws ParseException
	 */
	public static boolean compareDate(String date1, String date2)
			throws ParseException {
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance("yyyy-MM-dd");
		boolean flag = false;

		if (date1.equals(date2)) {
			return false;
		}
		Date d1 = DateUtils.parseDate(date1, fastDateFormat.getPattern());
		Date d2 = DateUtils.parseDate(date2, fastDateFormat.getPattern());
		flag = d2.before(d1);

		return flag;
	}

	public static String getDriveTime(String startTime, String endTime) {
		Date d1 = CommentUtil.getDateTimeOfStr(startTime);
		Date d2 = CommentUtil.getDateTimeOfStr(endTime);
		long diff = d2.getTime() - d1.getTime();
		return String.valueOf((diff / 1000));
	}

	public static int getOverYear(String startTime) {
		Calendar a = Calendar.getInstance();
		int y1 = a.get(Calendar.YEAR);// 得到年
		int y2 = Integer.valueOf(startTime.substring(0, 4));// 得到年
		return (y1 - y2);
	}

//	public static void main(String[] args) {
//		
////		double a=1.67;
//		
//		String s=addCustomDateTimeS("2017-01-09 14:56:22.666",60);
//		try {
//			System.out.println(compareDateTimeSSS(s, getCurDateTimeStr2()) && compareDateTimeSSS(s, getCurDateTimeStr2()));
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		}
	public static int getMonthSpace(String date1, String date2)
		{

		int month = 0;
		int year = 0;
		try{
		FastDateFormat sdf = FastDateFormat.getInstance("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		month = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		year= c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		month=year*12+month;
		}catch(Exception e){
			e.printStackTrace();
		}
		return month;
	}
	public static long stringToLong(String strTime) throws ParseException{
		FastDateFormat formatter = FastDateFormat.getInstance("yyyy年MM月dd日");
	      Date date = null;
	      date = formatter.parse(strTime);
	      return date.getTime();
	}
	public static String longToString(long currentTime) throws ParseException{
		FastDateFormat formatter =  FastDateFormat
				.getInstance("yyyy-MM-dd");
		Date dateOld = new Date(currentTime); 
		return formatter.format(dateOld);
	}
	public static String getRegDay(String date){
		try {
			return longToString(stringToLong(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getMonthByDay(String day){
		FastDateFormat fastDateFormat = FastDateFormat
				.getInstance("yyyy-MM-dd");
		Date date1=null;
		try {
			  date1=fastDateFormat.parse(day);
		    fastDateFormat = FastDateFormat
				.getInstance("yyyy-MM");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fastDateFormat.format(date1);
	}
	
	public static void main(String []args){
//		System.out.println(CommentUtil.getDayDiff("2017-01-01","2017-05-01"));
		try {
				System.out.println(CommentUtil.isInHour("2017-07-01 12:00:00","2017-08-01 12:59:00"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	public static String getBId(String bid){
//		int s=Integer.valueOf(bid);
//	    String str = String.format("%08d", s);      
//	    return str;
//	}
}
