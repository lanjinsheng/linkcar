package com.idata365.appService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志记录工具类
 * @author Administrator
 *
 */
public class MobileChbLogUtils {
	
	//报文日志接口
	private static Logger LOG = LoggerFactory.getLogger("EVENTLOG");
	
	public static final void info(String userId, String uri,String token)
	{
		LOG.info("{}={}={}",userId, uri, token);
	}
	
	 
	public static void main(String []args){
	}
}
