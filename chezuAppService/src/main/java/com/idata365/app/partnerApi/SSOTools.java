package com.idata365.app.partnerApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.springframework.core.io.InputStreamResource;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.util.ZipUtils;

/**
 * This sample demonstrates how to get started with basic requests to Aliyun OSS 
 * using the OSS SDK for Java.
 */
public class SSOTools {
	
      //内网
//  private static String endpoint = "http://oss-cn-hangzhou-internal.aliyuncs.com";
    //外网
    public static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    public static String accessKeyId = "LTAIwwEJMzKOaDtT";
    public static String accessKeySecret = "tFeII3b2Vsk5q3bT3W6kjd10bt3SYC";
    public static String bucketName_userInfo="app-users-imgs-info";
    
   // private static String bucketName = "driveDatas";
    public static String createSSOUsersImgInfoKey(long userId,UserImgsEnum type) {
    	String key=userId+"/"+type+"_"+userId+System.currentTimeMillis();
    	return key;
    }
    public static String getSSOUsersImgInfoKey(long userId,String key) {
    	return userId+"/"+key;
    } 
   
 
    public static void saveOSS(InputStream is,String dir) throws IOException,OSSException,ClientException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            /*
             * Determine whether the bucket exists
             */
            if (!ossClient.doesBucketExist(bucketName_userInfo)) {
                /*
                 * Create a new OSS bucket
                 */
                System.out.println("Creating bucket " + bucketName_userInfo + "\n");
                ossClient.createBucket(bucketName_userInfo);
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName_userInfo);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            /*
             * Upload an object to your bucket
             */
            ossClient.putObject(new PutObjectRequest(bucketName_userInfo,dir, is));
            
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
    }
    public static void getSSOFile(String key, OutputStream os) {
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//  	 String tmpPath="/usr/local/datas/";
	   
	   	InputStream inputStream=null;
	        try {
	            /*
	             * Determine whether the bucket exists
	             */
	            if (!ossClient.doesBucketExist(bucketName_userInfo)) {
	           	 return;
	            }
	            /*
	             */
	            OSSObject object = ossClient.getObject(bucketName_userInfo, key);
	            byte[] buffer = new byte[1024];
	             inputStream=object.getObjectContent();
	            int len = 0 ;
	            // 读取文件的内容,打包到zip文件    
	            while ((len = inputStream.read(buffer)) > 0) {
	                os.write(buffer, 0, len);
	            }
	           
	        } catch (IOException e) {
	  	      e.printStackTrace();
	  	    } catch (Exception e) {
	  	      e.printStackTrace();
	  	    } finally {
	  	      // 完毕，关闭所有链接
	  	      try {
	  	    	 os.flush();
		         os.close();
		         inputStream.close();
	  	      } catch (IOException e) {
	  	        e.printStackTrace();
	  	      }
	  	    ossClient.shutdown();
	  	    } 
   } 
    

}