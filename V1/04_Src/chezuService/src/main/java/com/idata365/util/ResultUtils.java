package com.idata365.util;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {
	public static final String RETURN_KEY = "return_key";
	
	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAIL = 0;

	public static final String CODE_OK = "0";
	public static final String CODE_REQUEST_ERROR = "1";
	public static final String CODE_PARAM_ERROR = "2";
	public static final String CODE_PARAM_VERIFICATION = "3";

	public static final String CODE_ERROR = "100";

	public static String toJson(Object object) {
		return GsonUtils.toJson(object,false);
	}

	public static void putSuccess(Map<String, Object> m) {
		m.put(RETURN_KEY, STATUS_SUCCESS);
	}
	
	public static String rtJson(int rtState, String rtMsrg, Object rtData){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rtState", rtState);
		map.put("rtMsrg", rtMsrg);
		map.put("rtData", rtData);
		return GsonUtils.toJson(map,false);
	}
	
	public static Map<String, Object> rtSuccess(Object datas) {
		if (datas == null) {
			datas = new HashMap<String, Object>();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", STATUS_SUCCESS);
		result.put("msg", "ok");
		result.put("code", CODE_OK);
		result.put("data", datas);
		return result;
	}
	public static Map<String, Object> rtFail(Object datas) {
		if (datas == null) {
			datas =new HashMap<String, Object>();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", STATUS_FAIL);
		result.put("msg", "系统异常");
		result.put("code", CODE_ERROR);
		result.put("data", datas);
		return result;
	}
	public static Map<String, Object> rtFailVerification(Object datas) {
		if (datas == null) {
			datas =new HashMap<String, Object>();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", STATUS_FAIL);
		result.put("msg", "校验不通过");
		result.put("code", CODE_PARAM_VERIFICATION);
		result.put("data", datas);
		return result;
	}

	public static Map<String, Object> rtFailRequest(Object datas) {
		if (datas == null) {
			datas = new HashMap<String, Object>();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", STATUS_FAIL);
		result.put("msg", "无效请求");
		result.put("code", CODE_REQUEST_ERROR);
		result.put("data", datas);
		return result;
	}

	public static Map<String, Object> rtFailParam(Object datas) {
		if (datas == null) {
			datas =new HashMap<String, Object>();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", STATUS_FAIL);
		result.put("msg", "无效参数");
		result.put("code", CODE_PARAM_ERROR);
		result.put("data", datas);
		return result;
	}

	public static Map<String, Object> rtFail(Object datas, String msg, String code) {
		if (datas == null) {
			datas =new HashMap<String, Object>();
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", STATUS_FAIL);
		result.put("msg", msg);
		result.put("code", code);
		result.put("data", datas);
		return result;
	}
}
