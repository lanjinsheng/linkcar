package com.ljs.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 加载properties文件的方式
 *
 * @author wjzuo
 *
 */
public class ContextInitListener  implements  ServletContextListener {

	@Override
    public void contextInitialized(ServletContextEvent sce) { 
        Properties props = new Properties(); 
        InputStream inputStream = null; 
        try { 
            inputStream = getClass().getResourceAsStream("/common.properties"); 
            props.load(inputStream); 
            String isSocket = (String) props.get("isSocket"); 
            CommentUtil.isSocket=Boolean.valueOf(isSocket);
            CommentUtil.appUrl = (String)props.getProperty("appUrl");
            CommentUtil.shopUrl = (String)props.getProperty("shopUrl");
            CommentUtil.accountUrl = (String)props.getProperty("accountUrl");
            CommentUtil.assetUrl = (String)props.getProperty("assetUrl");
            Constant.colHost=props.getProperty("colHost");
        } catch (IOException ex) { 
            ex.printStackTrace(); 
        } 
    }

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

   
}

  