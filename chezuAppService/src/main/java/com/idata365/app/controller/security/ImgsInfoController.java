package com.idata365.app.controller.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

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
import com.idata365.app.controller.BaseController;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

 
@RestController
public class ImgsInfoController extends BaseController {
	private final static Logger LOG = LoggerFactory.getLogger(ImgsInfoController.class);
    @Autowired
	SystemProperties systemProperties;
	
	@RequestMapping(value = "/userFiles/imgs",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<InputStreamResource> downloadDriveData(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
	    LOG.info("start");
		  String  key=allRequestParams.get("key");
		   
	        HttpHeaders headers = new HttpHeaders();  
	        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");  
	        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", key));  
	        headers.add("Pragma", "no-cache");  
	        headers.add("Expires", "0");  
	        FileSystemResource   file = new FileSystemResource(systemProperties.getFileTmpDir()+key);
	        
	         try {
	        	 OutputStream os=file.getOutputStream();
	        	 SSOTools.getSSOFile(key,os);
	        	 
		        return ResponseEntity  
		                .ok().contentLength(file.contentLength())
		                .headers(headers)  
		                .contentType(MediaType.parseMediaType("application/octet-stream"))  
		                .body(new InputStreamResource(file.getInputStream())); 
			  }catch(Exception e) {
				  e.printStackTrace();
			  }finally{
		        
		      }
	  return null;
    }
    @RequestMapping("/userFiles/test")
    public Map<String,Object> test(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
    	Map<String,Object> rtMap=new HashMap<String,Object>();
    	return ResultUtils.rtSuccess(rtMap);
    }
}
