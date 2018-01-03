package com.idata365.partnerApi;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.idata365.util.ZipUtils;

/**
 * This sample demonstrates how to get started with basic requests to Aliyun OSS 
 * using the OSS SDK for Java.
 */
public class SSOTools {
      //内网
//  private static String endpoint = "http://oss-cn-hangzhou-internal.aliyuncs.com";
    //外网
    private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIwwEJMzKOaDtT";
    private static String accessKeySecret = "tFeII3b2Vsk5q3bT3W6kjd10bt3SYC";
    private static String bucketName = "softtootest1";
   // private static String bucketName = "driveDatas";
//    public static void saveOSS(File file) throws IOException {
//        /*
//         * Constructs a client instance with your account for accessing OSS
//         */
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        
//        System.out.println("Getting Started with OSS SDK for Java\n");
//        
//        try {
//
//            /*
//             * Determine whether the bucket exists
//             */
//            if (!ossClient.doesBucketExist(bucketName)) {
//                /*
//                 * Create a new OSS bucket
//                 */
//                System.out.println("Creating bucket " + bucketName + "\n");
//                ossClient.createBucket(bucketName);
//                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName);
//                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
//                ossClient.createBucket(createBucketRequest);
//            }
//
//       
//            /*
//             * Upload an object to your bucket
//             */
//            String key=String.valueOf(System.currentTimeMillis());
//            System.out.println("Uploading a new object to OSS from a file\n");
//            ossClient.putObject(new PutObjectRequest(bucketName, key, file));
//            
//        } catch (OSSException oe) {
//        	oe.printStackTrace();
//        } catch (ClientException ce) {
//           ce.printStackTrace();
//        } finally {
//            /*
//             * Do not forget to shut down the client finally to release all allocated resources.
//             */
//            ossClient.shutdown();
//        }
//    }
    
 
    public static void saveOSS(InputStream is,String dir) throws IOException,OSSException,ClientException {
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            /*
             * Determine whether the bucket exists
             */
            if (!ossClient.doesBucketExist(bucketName)) {
                /*
                 * Create a new OSS bucket
                 */
                System.out.println("Creating bucket " + bucketName + "\n");
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest= new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            /*
             * Upload an object to your bucket
             */
            ossClient.putObject(new PutObjectRequest(bucketName,dir, is));
            
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            ossClient.shutdown();
        }
    }
    public static void  addOSSTest(String path) {
    	File f=new File(path);
    	OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    	  ossClient.putObject(new PutObjectRequest(bucketName, "234242/20171128/B1511863350788", f));
    	  ossClient.shutdown();
    }
    public static void main(String []args) {
    	addOSSTest("C:\\Users\\jinsheng\\Desktop\\1509497329859\\1509497329859.gz");
    }
    public static boolean getSSOFile(StringBuffer json,String key) {
    	 OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
         try {
             /*
              * Determine whether the bucket exists
              */
             if (!ossClient.doesBucketExist(bucketName)) {
            	 return false;
             }
             /*
              */
             OSSObject object = ossClient.getObject(bucketName, key);
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
             ossClient.shutdown();
         }
    }
    public static void getSSOFile(String key,ZipOutputStream os,String name) {
   	 OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//   	 String tmpPath="/usr/local/datas/";
	   
	   	InputStream inputStream=null;
	        try {
	            /*
	             * Determine whether the bucket exists
	             */
	            if (!ossClient.doesBucketExist(bucketName)) {
	           	 return;
	            }
	            /*
	             */
	            OSSObject object = ossClient.getObject(bucketName, key);
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
	  	    ossClient.shutdown();
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