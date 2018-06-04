package com.idata365.col.api;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.idata365.col.util.ZipUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;

/**
 * This sample demonstrates how to get started with basic requests to Aliyun OSS 
 * using the OSS SDK for Java.
 */
public class QQSSOTools {
     //内网
//  private static String endpoint = "http://oss-cn-hangzhou-internal.aliyuncs.com";
    //外网
//    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "AKIDAbwBJFoY4SdeVC4SHyW3OWRAi4QiBrFf";
    private static String accessKeySecret = "Rhsw9C3h9ORN8h5JOHyXu53rWyPvHVXf";
    private static String bucketName = "softtootest1-1252395822";
   
    public static void saveOSS(File file,String key) throws IOException{
    	// 1 初始化用户身份信息(secretId, secretKey)
    	COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
    	System.out.println(cred.getCOSAppId());
    	// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    	ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
    	// 3 生成cos客户端
    	COSClient cosClient = new COSClient(cred, clientConfig);
    	// bucket的命名规则为{name}-{appid} ，此处填写的存储桶名称必须为此格式
    	try{
//    		ObjectMetadata objectMetadata=new ObjectMetadata();
    		PutObjectResult resutl=cosClient.putObject(bucketName, key, file);
    	}finally {
   	     /*
   	      * Do not forget to shut down the client finally to release all allocated resources.
   	      */
   			cosClient.shutdown();
   	    }
       
    }
    public static boolean getSSOFile(StringBuffer json,String key) {
    	// 1 初始化用户身份信息(secretId, secretKey)
	    	COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
	    	// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
	    	ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
	    	// 3 生成cos客户端
	    	COSClient cosClient = new COSClient(cred, clientConfig);
	    try{
	    	GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
	    File downFile=new File("");
	    COSObject object = cosClient.getObject(getObjectRequest);
	    ZipUtils.uncompressToString(object.getObjectContent(),json);
	    return true;
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			  return false;
		}finally {
	     /*
	      * Do not forget to shut down the client finally to release all allocated resources.
	      */
			cosClient.shutdown();
	    }
    }
  
    public static void getSSOFile(String key,ZipOutputStream os,String name) {
    	// 1 初始化用户身份信息(secretId, secretKey)
    	COSCredentials cred = new BasicCOSCredentials(accessKeyId, accessKeySecret);
    	// 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
    	ClientConfig clientConfig = new ClientConfig(new Region("ap-shanghai"));
    	// 3 生成cos客户端
    	COSClient cosClient = new COSClient(cred, clientConfig);
//   	 String tmpPath="/usr/local/datas/";
	   
	   	InputStream inputStream=null;
	        try {
	            /*
	          
	            /*
	             */
	        	COSObject object = cosClient.getObject(bucketName, key);
	            byte[] buffer = new byte[1024];
	             inputStream=object.getObjectContent();
	            os.putNextEntry(new ZipEntry(name));
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
		         os.closeEntry();
		         inputStream.close();
	  	      } catch (IOException e) {
	  	        e.printStackTrace();
	  	      }
	  	    cosClient.shutdown();
	  	    } 
   }
     
    private static void displayTextInputStream(InputStream input,StringBuffer json) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        while (true) {
            String line = reader.readLine();
            if (line == null) break;
            json.append(line);
        }
        reader.close();
    }
    
    private static void displayTextInputStream2(InputStream input,StringBuffer jsonSB) throws IOException {
    	 
    }
      
	

//    private static void displayTextInputStream(InputStream input) throws IOException {
//        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//        while (true) {
//            String line = reader.readLine();
//            if (line == null) break;
//
//            System.out.println("    " + line);
//        }
//        System.out.println();
//        
//        reader.close();
//    }

}