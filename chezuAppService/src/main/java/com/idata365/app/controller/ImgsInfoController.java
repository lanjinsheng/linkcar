package com.idata365.app.controller;

import java.io.OutputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.config.SystemProperties;
import com.idata365.app.partnerApi.SSOTools;

 
@RestController
public class ImgsInfoController {
	private final static Logger LOG = LoggerFactory.getLogger(ImgsInfoController.class);
    @Autowired
	SystemProperties systemProperties;
	
	@RequestMapping(value = "/userFiles/getImgs",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> getImgs(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
		  String  key=allRequestParams.get("key");
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", key));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
	        FileSystemResource   file = new FileSystemResource(systemProperties.getFileTmpDir()+key.replaceAll("/", "")+".jpg");
	        
	         try {
	        	 OutputStream os=file.getOutputStream();
	        	 SSOTools.getSSOFile(key,os);
	        	 
		        return ResponseEntity  
		                .ok().contentLength(file.contentLength())
		                .headers(headers)  
		                .contentType(MediaType.parseMediaType("image/jpeg"))  
		                .body(new InputStreamResource(file.getInputStream())); 
			  }catch(Exception e) {
				  e.printStackTrace();
				  //放张未知图片
				  return ResponseEntity  
			                .ok().contentLength(0)
			                .headers(headers)  
			                .contentType(MediaType.parseMediaType("image/jpeg"))  
			                .body(null); 
			  }finally{
		        
		      }
    }
	public static void main(String []args) {
		System.out.println("12/HEADER_121513230107926".replaceAll("/", ""));
	}
	
}
