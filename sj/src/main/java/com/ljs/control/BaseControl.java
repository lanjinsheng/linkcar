package com.ljs.control;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.ljs.util.Constant;
import com.ljs.util.CreateMd5;
import com.yao.module.MsgType;


public abstract class BaseControl {

	Gson gs=new Gson();
	@SuppressWarnings("unchecked")
	public Map<String, Object> getSessionUser(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		return (Map<String, Object>) session.getAttribute("LOGIN_USER");
	}
	
	
	public boolean validParameters(Map<String,Object> map,MsgType type){
		switch(type){
		 case KICK:{
//			 sign=MD5(place_code=888888888888&user_macs=aaaaaaaaaaa,bbbbbbbbb,ddddddddddd&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("place_code="+place_code);
			 String user_macs=map.get("user_macs")==null?"":String.valueOf(map.get("user_macs"));
			 parameters.append("&user_macs="+user_macs);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 parameters.append("&apikey="+Constant.apikey);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			 String validString=CreateMd5.getMd5(parameters.toString());
			 System.out.print("sign="+sign);
			 System.out.print("validString="+validString);
			 if(validString.equalsIgnoreCase(sign)){
				 return true;
			 }
			 
			break; 
		 }
		 case MACBLACKlIST:{
//			 sign=MD5(action=add&place_code=88888888888&&user_macs=aaaaaaaaaaa,bbbbbbbbb,ddddddddddd&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String action=map.get("action")==null?"":String.valueOf(map.get("action"));
			 parameters.append("action="+action); 
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("&place_code="+place_code);
			 String user_macs=map.get("user_macs")==null?"":String.valueOf(map.get("user_macs"));
			 parameters.append("&user_macs="+user_macs);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 parameters.append("&apikey="+Constant.apikey);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			 String validString=CreateMd5.getMd5(parameters.toString());
			 System.out.print("sign="+sign);
			 System.out.print("validString="+validString);
			 if(validString.equalsIgnoreCase(sign)){
				 return true;
			 }
			break; 
		}
		case MACWHILELIST:{
//			 sign=MD5(action=add&place_code=88888888888&&user_macs=aaaaaaaaaaa,bbbbbbbbb,ddddddddddd &attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String action=map.get("action")==null?"":String.valueOf(map.get("action"));
			 parameters.append("action="+action); 
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("&place_code="+place_code);
			 String user_macs=map.get("user_macs")==null?"":String.valueOf(map.get("user_macs"));
			 parameters.append("&user_macs="+user_macs);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 parameters.append("&apikey="+Constant.apikey);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			 String validString=CreateMd5.getMd5(parameters.toString());
			 System.out.print("sign="+sign);
			 System.out.print("validString="+validString);
			 if(validString.equalsIgnoreCase(sign)){
				 return true;
			 }
			break;
		}
		case CERTIFICATE:{
//			sign=MD5(isExemption=0&place_code=8888888&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String isExemption=map.get("isExemption")==null?"":String.valueOf(map.get("isExemption"));
			 parameters.append("isExemption="+isExemption); 
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("&place_code="+place_code);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 parameters.append("&apikey="+Constant.apikey);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			 String validString=CreateMd5.getMd5(parameters.toString());
			 System.out.print("sign="+sign);
			 System.out.print("validString="+validString);
			 if(validString.equalsIgnoreCase(sign)){
				 return true;
			 }
			 break;
		}
		default:
			break;
	}
		
		return false;
		
	}
	
	public String getSignByType(Map<String,Object> map,MsgType type){
		String validString="";
		switch(type){
		 case KICK:{
//			 sign=MD5(place_code=888888888888&user_macs=aaaaaaaaaaa,bbbbbbbbb,ddddddddddd&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("place_code="+place_code);
			 String user_macs=map.get("user_macs")==null?"":String.valueOf(map.get("user_macs"));
			 parameters.append("&user_macs="+user_macs);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			  validString=CreateMd5.getMd5(parameters.toString());
			
			 
			break; 
		 }
		 case MACBLACKlIST:{
//			 sign=MD5(action=add&place_code=88888888888&&user_macs=aaaaaaaaaaa,bbbbbbbbb,ddddddddddd&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String action=map.get("action")==null?"":String.valueOf(map.get("action"));
			 parameters.append("action="+action); 
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("&place_code="+place_code);
			 String user_macs=map.get("user_macs")==null?"":String.valueOf(map.get("user_macs"));
			 parameters.append("&user_macs="+user_macs);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			  validString=CreateMd5.getMd5(parameters.toString());
			
			break; 
		}
		case MACWHILELIST:{
//			 sign=MD5(action=add&place_code=88888888888&&user_macs=aaaaaaaaaaa,bbbbbbbbb,ddddddddddd &attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String action=map.get("action")==null?"":String.valueOf(map.get("action"));
			 parameters.append("action="+action); 
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("&place_code="+place_code);
			 String user_macs=map.get("user_macs")==null?"":String.valueOf(map.get("user_macs"));
			 parameters.append("&user_macs="+user_macs);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			  validString=CreateMd5.getMd5(parameters.toString());
			
			break;
		}
		case CERTIFICATE:{
//			sign=MD5(isExemption=0&place_code=8888888&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549)
			 StringBuffer parameters=new StringBuffer();
			 String isExemption=map.get("isExemption")==null?"":String.valueOf(map.get("isExemption"));
			 parameters.append("isExemption="+isExemption); 
			 String place_code=map.get("place_code")==null?"":String.valueOf(map.get("place_code"));
			 parameters.append("&place_code="+place_code);
			 String attr1=map.get("attr1")==null?"":String.valueOf(map.get("attr1"));
			 parameters.append("&attr1="+attr1);
			 String attr2=map.get("attr2")==null?"":String.valueOf(map.get("attr2"));
			 parameters.append("&attr2="+attr2);
			 String sign=map.get("sign")==null?"":String.valueOf(map.get("sign"));
			  validString=CreateMd5.getMd5(parameters.toString());
			
			 break;
		}
		default:
			break;
	}
		
		return validString;
		
	}
	
	/**
	 * 将request参数转换成Map
	 * 
	 * @param request
	 * @return
	 */
	public Map<String, Object> requestParameterToMap(HttpServletRequest request) {
		Map<String, Object> m = new HashMap<String, Object>();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			m.put(name, request.getParameter(name).trim());
		}
		return m;
	}
	
	
	public void mapToRequestAttribute(HttpServletRequest request,Map<String,Object> map) {
		Set<String> keys = map.keySet();
		Iterator<String> it=keys.iterator();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			request.setAttribute(key, map.get(key));
		}
	}
	/**
	 * 将request参数转换成String 串
	 * 
	 * @param request
	 * @return
	 */
	public  String requestParameterToString(HttpServletRequest request) {
	    StringBuffer sb=new StringBuffer();
		Enumeration<String> names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			sb.append("&"+name+"="+request.getParameter(name).trim());
		}
		if(sb.toString().length()>1)
		return sb.toString().substring(1,sb.toString().length());
		else 
		return "";
	}
	public  String getIpAddress(HttpServletRequest request) { 
	    String ip = request.getHeader("x-forwarded-for"); 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("WL-Proxy-Client-IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_CLIENT_IP"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
	    } 
	    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
	      ip = request.getRemoteAddr(); 
	    } 
	    return ip; 
	  } 
	/**
	 * 拆除身份信息
	 * 
	 * @param map
	 * @return map
	 */
	public Map<String, Object> getIdentification(Map map) {
		return (Map<String, Object>) map.get("identification");
	}


	@SuppressWarnings("unchecked")
	public Map<String, Object> getPagerMap(HttpServletRequest request) {
		return (Map<String, Object>) request.getAttribute("pageMap");
	}

}
