package com.idata365.app.util;

import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class SignUtils {
	private final static Logger LOG = LoggerFactory.getLogger(SignUtils.class);
	public static final String key="sj3iUJ1C3lt74qe9LANPmILlsUT4UyX3In7YNRbujlI";
	 public static boolean security(String jsonParam,String pSign){
		 try {
	        String sign= HMAC.encryptHMAC(jsonParam, key);
	        LOG.info("sign:"+sign);
	        if(sign.equalsIgnoreCase(pSign)){
	        	return true;
	        }else{
	        	return false;
	        }
	        
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return false;
	  }
	 public static String getMD5MD5Password(String pwd) {
		  return MD5Encoder.encode(MD5Encoder.encode(pwd.getBytes()).getBytes());
	 }
	 public static void main(String []args) {
		  String sign;
		try {
			sign = HMAC.encryptHMAC("18795860371", key);
			System.out.println(sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
}
