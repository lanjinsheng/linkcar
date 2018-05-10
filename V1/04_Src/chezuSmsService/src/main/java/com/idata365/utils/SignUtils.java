package com.idata365.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class SignUtils {
	private final static Logger LOG = LoggerFactory.getLogger(SignUtils.class);
	public static final String key="sj3iUJ1C3lt74qe9LANPmILlsUT4UyX3In7YNRbujlI";
	public static final String AesKey="24bc67447d986a36fd8bfe631f9adfe3";
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
	 public static String encryptHMAC(String args) {
		  String sign=null;
		try {
			sign = HMAC.encryptHMAC(args, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sign;
		 
	 }
	  /** 
	    * 对字符串md5加密(小写+字母) 
	    * 
	    * @param str 传入要加密的字符串 
	    * @return  MD5加密后的字符串 
	    */  
	   public static String getMD5(String str) {  
	       try {  
	           // 生成一个MD5加密计算摘要  
	           MessageDigest md = MessageDigest.getInstance("MD5");  
	           // 计算md5函数  
	           md.update(str.getBytes());  
	           // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符  
	           // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值  
	           return new BigInteger(1, md.digest()).toString(16);  
	       } catch (Exception e) {  
	          e.printStackTrace();  
	          return null;  
	       }  
	   }  
	     
	     
	   /** 
	    * 对字符串md5加密(大写+数字) 
	    * 
	    * @param str 传入要加密的字符串 
	    * @return  MD5加密后的字符串 
	    */  
	     
	   public static String MD5(String s) {  
	       char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};         
	 
	       try {  
	           byte[] btInput = s.getBytes();  
	           // 获得MD5摘要算法的 MessageDigest 对象  
	           MessageDigest mdInst = MessageDigest.getInstance("MD5");  
	           // 使用指定的字节更新摘要  
	           mdInst.update(btInput);  
	           // 获得密文  
	           byte[] md = mdInst.digest();  
	           // 把密文转换成十六进制的字符串形式  
	           int j = md.length;  
	           char str[] = new char[j * 2];  
	           int k = 0;  
	           for (int i = 0; i < j; i++) {  
	               byte byte0 = md[i];  
	               str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
	               str[k++] = hexDigits[byte0 & 0xf];  
	           }  
	           return new String(str);  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	           return null;  
	       }  
	   } 
	
	public static String encryptDataAes(String data) throws Exception {
		return Aes.encryptData(AesKey,data);
	}
	
	public static String decryptDataAes(String content) throws Exception {
		return Aes.decryptData(AesKey, content.substring(16, content.length()), content.substring(0, 16));
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
