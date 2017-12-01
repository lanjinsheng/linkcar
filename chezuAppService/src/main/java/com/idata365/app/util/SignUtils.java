package com.idata365.app.util;

import java.io.UnsupportedEncodingException;

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
	 
	 public static void main(String []args) {
		  String sign;
		try {
			sign = HMAC.encryptHMAC("小波1000aaabbbccc", "sj3iUJ1C3lt74qe9LANPmILlsUT4UyX3In7YNRbujlI");
			System.out.println(sign);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
}
