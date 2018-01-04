package com.idata365.app.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.partnerApi.QQSSOTools;
import com.idata365.app.partnerApi.SSOTools;

 
@Controller
public class ImgsInfoController {
	private final static Logger LOG = LoggerFactory.getLogger(ImgsInfoController.class);
    @Autowired
	SystemProperties systemProperties;
	
	@RequestMapping(value = "/userFiles/getImgs")
    public void  getImgs(@RequestParam (required = false) Map<String, String> allRequestParams,HttpServletResponse response){
	    LOG.info("start");
		  String  key=allRequestParams.get("key");
		  LOG.info("key="+key);
//		  key=key.split(".")[0];
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", key+".jpeg"));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
	        FileSystemResource   file = new FileSystemResource(systemProperties.getFileTmpDir()+key+".jpeg");
	        File fileParent = file.getFile().getParentFile();  
			if(!fileParent.exists()){  
			    fileParent.mkdirs();  
			} 
	         try {
	        	 OutputStream os=file.getOutputStream();
	        	 if(key.endsWith("_Q")) {//走qq
	        		 QQSSOTools.getSSOFile(key, os);
	        	 }else {
	        	
	        	  SSOTools.getSSOFile(key,os);
	        	 }
	        	 InputStream inputStream = file.getInputStream();
	             byte[] data = new byte[(int)file.contentLength()];
	             int length = inputStream.read(data);
	             inputStream.close();
	             response.setContentType("image/jpeg");

	             OutputStream stream = response.getOutputStream();
	             stream.write(data);
	             stream.flush();
	             stream.close();
			  }catch(Exception e) {
				  e.printStackTrace();
				  //放张未知图片
			  }finally{
		        
		      }
    }
	
}
