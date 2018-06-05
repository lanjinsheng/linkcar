package com.idata365.app.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

public class ServerUtil {
	/**
	 * 错误返回
	 */
	public static final int RETURN_ERROR = 0;
	/**
	 * 正确返回
	 */
	public static final int RETURN_OK = 1;

	public static final String ERROR_CODE_1 = "01";
	public static final String ERROR_MSG_1 = "url无效";

	public static final String ERROR_CODE_2 = "02";
	public static final String ERROR_MSG_2 = "用户为空";

	public static final String ERROR_CODE_3 = "03";
	public static final String ERROR_MSG_3 = "入参错误";

	public static final String ERROR_CODE_4 = "04";
	public static final String ERROR_MSG_4 = "接口异常";

	public static final String ERROR_CODE_10 = "10";

	public static final String RETURN_KEY = "return_key";

	public static Map<String,String> datas=new ConcurrentHashMap<String, String>();
	
	public static final int RETURN_OK_LIXIANG=200;
	public static final int RETURN_ERROR_LIXIANG=201;
	
	public static String rtJson(int rtState, String rtMsrg, Object rtData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rtState", rtState);
		map.put("rtMsrg", rtMsrg);
		map.put("rtData", rtData);
		return gson.toJson(map);
	}
	public static String rtLiXiangSuccessJson( String rtMsrg, Object rtData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", RETURN_OK_LIXIANG);
		map.put("msg", rtMsrg);
		map.put("result", rtData);
		return gson.toJson(map);
	}
	public static String rtLiXiangErrorJson(String rtMsrg, Object rtData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", RETURN_ERROR_LIXIANG);
		map.put("msg", rtMsrg);
		map.put("result", rtData);
		return gson.toJson(map);
	}

	public static String rtJson(int rtState, String errorMsg, String errorCode,
			Object rtData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", rtState);
		map.put("errorMsg", errorMsg);
		map.put("errorCode", errorCode);
		map.put("result", rtData);
		return gson.toJson(map);
	}
	static Gson gson = new Gson();
	public static String rtJsonSuccess(Object rtData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RETURN_OK);
		map.put("errorMsg", "成功");
		map.put("errorCode", "");
		map.put("result", rtData);
		return gson.toJson(map);
	}
    public static String rtJsonSuccessRoute(String tel){
//    	成功 ：200 { “code”:” 200”, “ msg”: 15937488732 is passed ”, “pass_time ”:” 1425956214241” } 
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", "200");
    	map.put("msg", tel+" is passed");
    	map.put("pass_time", String.valueOf(new Date().getTime()));
    	return gson.toJson(map);
    }

	public static String rtJsonFailRoute(String tel) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", "201");
    	map.put("msg", tel+" message send error");
    	map.put("pass_time", String.valueOf(new Date().getTime()));
    	return gson.toJson(map);
	}
	
	public static String rtJsonFailRoute(String tel,String msg) {
		Map<String, Object> map = new HashMap<String, Object>();
    	map.put("code", "201");
    	map.put("msg", tel+" "+msg);
    	map.put("pass_time", String.valueOf(new Date().getTime()));
    	return gson.toJson(map);
	}
	
	public static String rtJsonFail(String errorCode, String rtMsrg,
			Object rtData) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RETURN_ERROR);
		map.put("errorMsg", rtMsrg);
		map.put("errorCode", errorCode);
		map.put("result", rtData);
		return gson.toJson(map);
	}

	public static String rtJsonFailErroCode4(Object rtData) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RETURN_ERROR);
		map.put("errorMsg", ERROR_MSG_4);
		map.put("errorCode", ERROR_CODE_4);
		map.put("result", rtData);
		return gson.toJson(map);
	}

	public static String rtJsonFailErroCode4(Object rtData, String errorMsg) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RETURN_ERROR);
		map.put("errorMsg", errorMsg);
		map.put("errorCode", ERROR_CODE_4);
		map.put("result", rtData);
		return gson.toJson(map);
	}

	public static String rtJsonFailErroCode3(Object rtData, String errorMsg) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RETURN_ERROR);
		map.put("errorMsg", errorMsg);
		map.put("errorCode", ERROR_CODE_3);
		map.put("result", rtData);
		return gson.toJson(map);
	}

	public static String rtJsonFailErroCode10(Object rtData, String errorMsg) {
		Gson gson = new Gson();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", RETURN_ERROR);
		map.put("errorMsg", errorMsg);
		map.put("errorCode", ERROR_CODE_1);
		map.put("result", rtData);
		return gson.toJson(map);
	}

	public static String toJson(Object object) {
		Gson gson = new Gson();
		return gson.toJson(object);
	}

	public static void putSuccess(Map<String, Object> m) {
		m.put(RETURN_KEY, RETURN_OK);
	}

	public static Map<String, Object> box_cache = new HashMap<String, Object>();

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (null == ip || 0 == ip.length() || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
