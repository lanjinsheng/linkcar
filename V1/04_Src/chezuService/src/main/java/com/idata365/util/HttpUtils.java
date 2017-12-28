package com.idata365.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpUtils {
	private final static Logger LOG = LoggerFactory.getLogger(HttpUtils.class);
	private final static String AK="p4Cc8y1pH7DXu1xdPrutibIh3YhGBVWT";
	private final static String SERVICE_ID="156766";
//	{
//	    "ak": "p4Cc8y1pH7DXu1xdPrutibIh3YhGBVWT",
//	    "service_id": 156766,
//	    "entity_name": "17",
//	    "latitude": 31.033004,
//	    "longitude": 121.600263,
//	    "loc_time": "1509506155",
//	    "coord_type_input": "gcj02",
//	    "speed": 120
//	}
	/** 
	* 设置代理 
	* @createDate: Apr 2, 2011 6:03:30 PM 
	* @param client 
	*/  
	private static void setProxy(HttpClient client) {  
	// 设置代理  
	//设置代理服务器的ip地址和端口  
	client.getHostConfiguration().setProxy("119.28.16.217", 8989);  
	//使用抢先认证  
//	 client.getParams().setAuthenticationPreemptive(true);  
	 client.getState().setProxyCredentials(AuthScope.ANY, new UsernamePasswordCredentials("aes-256-cfb","lan20170907!")); 
	}  
	public static String postYingyanEntity(Map<String,String> point) {
		  String csn = Charset.defaultCharset().name();
	        System.out.println("csn====" +csn);
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("http://yingyan.baidu.com/api/v3/track/addpoint");
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");
		method.setParameter("ak", AK);
		method.setParameter("service_id", SERVICE_ID);
		method.setParameter("entity_name", point.get("entity_name"));
		method.setParameter("latitude", point.get("latitude"));
		method.setParameter("longitude", point.get("longitude"));
		method.setParameter("loc_time", point.get("loc_time"));
		method.setParameter("coord_type_input", point.get("coord_type_input"));
		method.setParameter("speed", point.get("speed"));
		method.setParameter("direction", point.get("direction"));
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(method);
		
			if (statusCode == 200) {
				// 更新数据
				String s = method.getResponseBodyAsString();
				LOG.info(s);
				return s;
			} 
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
		 
	}
	public static String postYingyanEntityList(List<Map<String,String>> pointList) {
		HttpClient httpClient = new HttpClient();
		PostMethod method = new PostMethod("http://yingyan.baidu.com/api/v3/track/addpoints");
		method.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded;charset=utf-8");
		method.setParameter("ak", AK);
		method.setParameter("service_id", SERVICE_ID);
		method.setParameter("point_list", GsonUtils.toJson(pointList, false));
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(method);
		
			if (statusCode == 200) {
				// 更新数据
				String s = method.getResponseBodyAsString();
				LOG.info(s);
				return s;
			} 
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
		 
	}
 
	
	public static String getYingyanAnalysis(Map<String,String> param) {
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod("http://yingyan.baidu.com/api/v3/analysis/drivingbehavior?ak="+AK+"&service_id="+SERVICE_ID
				+"&start_time="+param.get("start_time")+"&end_time="+param.get("end_time")+"&entity_name="+param.get("entity_name")+"&coord_type_output="+param.get("coord_type_output"));
//		method.setRequestHeader("Content-Type",
//				"application/x-www-form-urlencoded;charset=utf-8");
//		HttpMethodParams params=new HttpMethodParams();
//		params.setParameter("ak", AK);
//		params.setParameter("service_id", SERVICE_ID);
//		params.setParameter("start_time",param.get("start_time"));
//		params.setParameter("end_time",param.get("end_time"));
//		params.setParameter("entity_name",param.get("entity_name"));
//		method.setParams(params);
		int statusCode;
		try {
			statusCode = httpClient.executeMethod(method);
		
			if (statusCode == 200) {
				// 更新数据
				String s = method.getResponseBodyAsString();
				LOG.info(s);
				return s;
			} 
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOG.error(e.getMessage());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
		 
	}
	public static void main(String []args) {
	}
}
