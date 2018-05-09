package com.ljs.util;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.FastDateFormat;

import com.google.gson.Gson;
import com.ljs.pojo.Menu1;

public class Utility {

	/**
	 * 错误返回
	 */
	public static final String RETURN_ERROR = "1";
	/**
	 * 正确返回
	 */
	public static final String RETURN_OK = "0";
	/**
	 * messageKey
	 */
	public static final String RETURN_KEY = "RK";
	/**
	 * messageResult
	 */
	public static final String RETURN_RESULT = "2";
	
	 
	
	public static Map<String,List<Menu1>> cache = new HashMap<String,List<Menu1>>();
	/**
	 * 判断是否为空串
	 * 
	 * @param str
	 * @return true: null/"" false: 其它
	 */
	public static boolean isNullorEmpty(String str) {
		if (str == null||"null".equals(str)) {
			return true;
		}
		str = str.trim();
		if (str.length() < 1) {
			return true;
		}
		return false;
	}

	/**
	 * 成功消息置位
	 * 
	 * @param 
	 * @return 
	 */
	public static void putSuccess(Map <String,Object>m) {
	    m.put(RETURN_KEY, RETURN_OK);
	}
	/**
	 * 失败消息置位
	 * 
	 * @param str
	 * @return true: null/"" false: 其它
	 */
	public static void putError(Map <String,Object>m) {
	    m.put(RETURN_KEY, RETURN_ERROR);
	}
	/**
	 * 请求返回json格式
	 * 
	 * @param rtState
	 * @param rtMsrg
	 * @param rtData
	 * @return
	 */
	public static String rtJson(String rtState, String rtMsrg, String rtData) {
		if (rtState == null) {
			rtState = "0";
		}
		if (rtMsrg == null) {
			rtMsrg = "";
		} else {
			rtMsrg = rtMsrg.replace("\"", "\\\"").replace("\r", "").replace(
					"\n", "");
		}
		if (rtData == null) {
			rtData = "";
		}else {
			rtData = rtData.replace("\"", "\\\"").replace("\r", "").replace(
					"\n", "");
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{\"rtState\":\"").append(rtState).append("\",\"rtMsrg\":\"")
				.append(rtMsrg).append("\",\"rtData\":\"").append(rtData).append(
						"\"}");
		return sb.toString();
	}

	/**
	 * 转换成Json对象字符串
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Map<String, ?> map) {
		if (map == null) {
			return new StringBuffer("{}").toString();
		}
		Gson gson = new Gson();
		return gson.toJson(map);
//		StringBuffer rtBuf = new StringBuffer("{");
//		for (Entry<String,?> ent:map.entrySet()) {
//			String key = ent.getKey();
//			String value = String.valueOf(map.get(key));
//			if (value == null) {
//				value = "";
//			}
//			rtBuf.append("\"");
//			rtBuf.append(key);
//			rtBuf.append("\":");
//			rtBuf.append("\"" + value + "\",");
//		}
//		if (rtBuf.length() > 2) {
//			rtBuf.delete(rtBuf.length() - 1, rtBuf.length());
//		}
//		rtBuf.append("}");
//		return rtBuf.toString();
	}
	
	public static String lstMaptoJson(List<?> list) {
	    return lstToJson(list);
	}

	public static String toJson(List<Map<String, String>> list) {
		return lstToJson((List<?>)list);
	}

    private static String lstToJson(List<?> list)
    {
        if (list == null || list.size() == 0) {
			return new StringBuffer("[]").toString();
		}
        Gson gson = new Gson();
		return gson.toJson(list);
//		StringBuffer sb = new StringBuffer("[");
//		for (int i = 0; i < list.size(); i++) {
//			if (sb.length() > 1) {
//				sb.append(",");
//			}
//			@SuppressWarnings("unchecked")
//            Map<String, ?> map = (Map<String, ?> )list.get(i);
//			StringBuffer rtBuf = new StringBuffer("{");
//			Iterator<String> iKeys = map.keySet().iterator();
//			while (iKeys.hasNext()) {
//				String key = iKeys.next();
//				String value = String.valueOf(map.get(key));
//				if (value == null) {
//					value = "";
//				}
//				rtBuf.append("\"");
//				rtBuf.append(key);
//				rtBuf.append("\":");
//				rtBuf.append("\"" + value + "\",");
//			}
//			if (rtBuf.length() > 2) {
//				rtBuf.delete(rtBuf.length() - 1, rtBuf.length());
//			}
//			rtBuf.append("}");
//			sb.append(rtBuf);
//		}
//		sb.append("]");
//		return sb.toString();
    }

	/**
	 * 处理空值的字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String null2Empty(String str) {
		if (str == null) {
			return "";
		}
		return str;
	}
	/**
	 * 从Json字符串创建Java对象
	 * 
	 * @param json
	 * @param cls
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> json2Map(String json) {
		Map<String, String> propMap = new HashMap<String, String>();
		List<String> tmpArray = splitJson(json);
		for (String propStr : tmpArray) {
			propStr = propStr.trim();
			int tmpInt = propStr.indexOf(":");
			if (tmpInt < 0) {
				continue;
			}
			String[] tmpArray2 = new String[2];
			tmpArray2[0] = propStr.substring(0, tmpInt);
			tmpArray2[1] = propStr.substring(tmpInt + 1);

			if (tmpArray2.length < 2) {
				continue;
			}
			tmpArray2[0] = tmpArray2[0].trim();
			tmpArray2[1] = tmpArray2[1].trim();
			if (tmpArray2[0].charAt(0) == '\"'
					|| tmpArray2[0].charAt(0) == '\'') {
				tmpArray2[0] = tmpArray2[0].substring(1,
						tmpArray2[0].length() - 1);
			}
			if (tmpArray2[1].charAt(0) == '\"'
					|| tmpArray2[1].charAt(0) == '\'') {
				tmpArray2[1] = tmpArray2[1].substring(1,
						tmpArray2[1].length() - 1);
			}
			propMap.put(tmpArray2[0], tmpArray2[1]);
		}
		return propMap;
	}
	/**
	 * Json串拆串
	 * 
	 * @param srcStr
	 * @return
	 */
	public static List<String> splitJson(String srcStr) {
		if (srcStr == null || srcStr.length() < 1) {
			return new ArrayList<String>();
		}
		srcStr = srcStr.trim();
		if (srcStr.startsWith("{") && srcStr.endsWith("}")) {
			srcStr = srcStr.substring(1, srcStr.length() - 1);
		}
		srcStr = srcStr.trim().replace("\r\n", "").replace("\n", "").replace(
				"\\\"", "{{quote}}").replace("\\\"", "{{squote}}");
		List<String> rtList = getMatchedWords(
				srcStr,
				"(?:(?:\"[^\"]+\")|(?:\'[^\']+\')|(?:[^\":,]+))\\s*:\\s*(?:(?:-?\\d*\\.?\\d+(?:E\\d+)?)|(?:\"[^\"]*\")|(?:\'[^\']*\'))");
		for (int i = 0; i < rtList.size(); i++) {
			String str = (String) rtList.get(i);
			rtList.set(i, str.replace("{{quote}}", "\\\"").replace(
					"{{squote}}", "\\\'"));
		}
		return rtList;
	}
	/**
	 * 取得所有匹配的单词
	 * 
	 * @param srcStr
	 * @param reg
	 * @return
	 */
	public static List<String> getMatchedWords(String srcStr, String reg) {
		List<String> rtList = new ArrayList<String>();
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(srcStr);
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			String varExpr = srcStr.substring(start, end);
			rtList.add(varExpr);
		}

		return rtList;
	}
	 private static FastDateFormat dateFormat =
		  FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
	  /**
	   * 取得当前的时间，格式为 yyyy-MM-dd HH:mm:ss 
	   * @return
	   */
	  public static String getCurDateTimeStr() {
	    return dateFormat.format(new Date());
	  }
	  private static FastDateFormat dateFormat1 =
			  FastDateFormat.getInstance("yyyy-MM-dd");
	  /**
	   * 取得当前的时间，格式为 yyyy-MM-dd
	   * @return
	   */
	  public static String getCurDateTimeStr1() {
		  return dateFormat1.format(new Date());
	  }
	  /**
	   * 根据2个日期求天数
	   * @param time1
	   * @param time2
	   * @return
	   */
	  public static long getDate(String time1, String time2){ 
		  long quot = 0;  SimpleDateFormat ft = new SimpleDateFormat("yyyy/MM/dd");  
		  try {   
			  Date date1 = ft.parse( time1 );  
			  Date date2 = ft.parse( time2 ); 
			  quot = date1.getTime() - date2.getTime();   
			  quot = quot / 1000 / 60 / 60 / 24;  
			  } catch (ParseException e) { 
				  e.printStackTrace(); 
				  }  
		  return quot; 
				  
	  }
	  /**
	   * 一年有多少天
	   * @param year
	   * @return
	   */
	  public static int getMaxDaysOfYear(int year) {		
		  Calendar cal = Calendar.getInstance();	
		  cal.set(Calendar.YEAR, year);		
		  return cal.getActualMaximum(Calendar.DAY_OF_YEAR);	
		  }
}
