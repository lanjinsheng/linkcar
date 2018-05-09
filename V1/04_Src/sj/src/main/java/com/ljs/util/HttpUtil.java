package com.ljs.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.yao.module.ConfigMsg;
import com.yao.module.LoginMsg;

public class HttpUtil {
	/** 
     * 以Json方式返回获取最新的资讯，不需要手动解析，JSON自己会进行解析 
     * @return 
     * @throws Exception 
     */  
    public static String getJSONByUrl(String path) throws Exception  
    {  
        //建立一个URL对象  
        URL url = new URL(path);  
        //得到打开的链接对象  
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
        //设置请求超时与请求方式  
        conn.setReadTimeout(5*1000);  
        conn.setRequestMethod("GET");  
        //从链接中获取一个输入流对象  
        InputStream inStream = conn.getInputStream();  
        //调用数据流处理方法  
        byte[] data = readInputStream(inStream);  
        String json = new String(data);  
        return json;  
    }  
    public static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;  
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }  
        inStream.close();  
        return outStream.toByteArray();  
    } 
    public static String post(String urlPath,String param) throws Exception{
//        String urlPath = new String("http://localhost:8080/Test1/HelloWorld"); 
//        String param="name="+URLEncoder.encode("丁丁","UTF-8");
        //建立连接
        URL url=new URL(urlPath);
        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
        //设置参数
        httpConn.setDoOutput(true);   //需要输出
        httpConn.setDoInput(true);   //需要输入
        httpConn.setUseCaches(false);  //不允许缓存
        httpConn.setRequestMethod("POST");   //设置POST方式连接
        //设置请求属性
        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
        httpConn.connect();
        //建立输入流，向指向的URL传入参数
        DataOutputStream dos=new DataOutputStream(httpConn.getOutputStream());
        dos.writeBytes(param);
        dos.flush();
        dos.close();
        //获得响应状态
        int resultCode=httpConn.getResponseCode();
        if(HttpURLConnection.HTTP_OK==resultCode){
          StringBuffer sb=new StringBuffer();
          String readLine=new String();
          BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
          while((readLine=responseReader.readLine())!=null){
            sb.append(readLine).append("\n");
          }
          responseReader.close();
          return sb.toString();
        }
		return null; 
      }
    public static String getBlackWhile(String placeCode){
    	String urlPath=Constant.LixiangBlackWhiteUrl;
    	String param="place_code="+placeCode+"&attr1=&attr2=";
    	String toSign="place_code="+placeCode+"&attr1=&attr2=&apikey="+Constant.apikey;
    	String sign=CreateMd5.getMd5(toSign);
    	param=param+"&sign="+sign;
    	try {
    		return  (HttpUtil.post(urlPath, param));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    public static boolean validateAp(ConfigMsg cfgMsg){
    	 String parameters=cfgMsg.getClientId()+cfgMsg.getVersion()+cfgMsg.getDataIp()+cfgMsg.getReqTime()+Constant.apikey;
		 String sign=cfgMsg.getSign();
		 String validString=CreateMd5.getMd5(parameters);
		 System.out.println("sign="+sign);
		 System.out.println("validString="+validString);
		 if(validString.equalsIgnoreCase(sign)){
				String s1=CommentUtil.addCustomDateTimeS(cfgMsg.getReqTime(),60);
				String s2=CommentUtil.addCustomDateTimeS(cfgMsg.getReqTime(),-60);
				String cur=CommentUtil.getCurDateTimeStr2();
				try {
					if(CommentUtil.compareDateTimeSSS(s1,cur) && CommentUtil.compareDateTimeSSS(cur, s2)){
						if(!Constant.isValidLogin(cfgMsg.getClientId())){
							 cfgMsg.setStatus("202");
							 cfgMsg.setErrorMsg("无效的终端");
							 cfgMsg.setServerTime(CommentUtil.getCurDateTimeStr2());
							 return false;
						}
						
						 cfgMsg.setStatus("200");
						 String token=UUID.randomUUID().toString();
						 cfgMsg.setToken(token);
						 cfgMsg.setDataIp(Constant.dataIp1);
						 cfgMsg.setServerTime(CommentUtil.getCurDateTimeStr2());
						 return true;
					}else{
						 cfgMsg.setStatus("201");
						 cfgMsg.setErrorMsg("时间校验不通过");
						 cfgMsg.setServerTime(CommentUtil.getCurDateTimeStr2());
						 return false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
					 cfgMsg.setStatus("201");
					 cfgMsg.setErrorMsg("时间格式错误不通过");
					 cfgMsg.setServerTime(CommentUtil.getCurDateTimeStr2());
					return false;
				}
				
			
		 }else{
			 cfgMsg.setStatus("201");
			 cfgMsg.setErrorMsg(parameters+"签名不通过,服务器签名结果:"+validString);
			 cfgMsg.setServerTime(CommentUtil.getCurDateTimeStr2());
			 return false;
		 }
    	
    }
    public static boolean validateApLogin(LoginMsg loginMsg){
   	     String parameters=loginMsg.getToken()+loginMsg.getVersion()+loginMsg.getIp()+loginMsg.getReqTime()+Constant.apikey;
		 String sign=loginMsg.getSign();
		 String validString=CreateMd5.getMd5(parameters);
		 System.out.println("sign="+sign);
		 System.out.println("validString="+validString);
		 if(validString.equalsIgnoreCase(sign)){
				String s1=CommentUtil.addCustomDateTimeS(loginMsg.getReqTime(),60);
				String s2=CommentUtil.addCustomDateTimeS(loginMsg.getReqTime(),-60);
				String cur=CommentUtil.getCurDateTimeStr2();
				try {
					if(CommentUtil.compareDateTimeSSS(s1,cur) && CommentUtil.compareDateTimeSSS(cur, s2)){
						loginMsg.setStatus("200");
						 return true;
					}else{
						loginMsg.setStatus("201");
						 return false;
					}
				} catch (ParseException e) {
					e.printStackTrace();
					loginMsg.setStatus("201");
					return false;
				}
				
			
		 }else{
			 loginMsg.setStatus("201");
			 return false;
		 }
   	
   }
    
    public static String getCertify(String placeCode){
    	String urlPath=Constant.LixiangCertificateUrl;
//    	place_code=8888888&company=7&attr1=xxx&attr2=xxxx&sign=ssdadssfsfsfsfsf
    	String param="place_code="+placeCode+"&company=7&attr1=&attr2=";
    	String toSign="place_code="+placeCode+"&company=7&attr1=&attr2=&apikey="+Constant.apikey;
//    	String toSign="RHY-TEST-ZBT-004z2f465291cffd352f51e34c893688549";
    	String sign=CreateMd5.getMd5(toSign);
    	param=param+"&sign="+sign;
    	try {
			return HttpUtil.post(urlPath, param);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    public static void test1(){
    	String urlPath=Constant.LixiangBlackWhiteUrl;
    	String param="place_code=RHY-TEST-ZBT-004&attr1=&attr2=";
    	String toSign="RHY-TEST-ZBT-004z2f465291cffd352f51e34c893688549";
    	String sign=CreateMd5.getMd5(toSign);
    	param=param+"&sign="+sign;
    	try {
			System.out.println(HttpUtil.post(urlPath, param));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void test3(){
    	String urlPath=Constant.LixiangCertificateUrl;
//    	place_code=8888888&company=7&attr1=xxx&attr2=xxxx&sign=ssdadssfsfsfsfsf
    	String param="place_code=RHY-TEST-ZBT-004&company=7&attr1=&attr2=";
    	String toSign="place_code=RHY-TEST-ZBT-004&company=7&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549";
//    	String toSign="RHY-TEST-ZBT-004z2f465291cffd352f51e34c893688549";
    	String sign=CreateMd5.getMd5(toSign);
    	param=param+"&sign="+sign;
    	try {
			System.out.println(HttpUtil.post(urlPath, param));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public static void test2(){
    	String a="place_code=RHY-TEST-ZBT-004&user_macs=5cbbccddeefa&attr1=&attr2=";
//    	String a="isExemption=0&place_code=RHY-TEST-ZBT-004&attr1=&attr2=&apikey=z2f465291cffd352f51e34c893688549";
    	System.out.println(CreateMd5.getMd5(a));
    }
    
    public static void main(String []args){
    	System.out.println(getCertify("RHY-TEST-ZBT-004"));
    	
    	String json="{\"result\":{\"blackMacsList\":[],\"whileMacsList\":[\"aaaa\",\"bbb\",\"ccccc\",\"ddddd\"]},\"code\":\"200\",\"msg\":\"SUCCESS\"}";
    
    	System.out.println(getBlackMacs(json));
     
    }
    public static String getBlackMacs(String json){
    	Gson gs=new Gson();
    	String s="";
     	Map<String,Object> m1=gs.fromJson(json, Map.class);
    	Map m2=	(Map) m1.get("result");
    	List<String> l=(List) m2.get("blackMacsList");
    	for(String b:l){
    		s+=b+",";
    	}
    	if(s.length()>0){
    		s=s.substring(0, s.length()-1);
    	}
    	return s;
    }
}
