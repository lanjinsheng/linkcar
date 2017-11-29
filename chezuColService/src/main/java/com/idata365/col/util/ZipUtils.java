package com.idata365.col.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* 压缩文件工具类
*/
public class ZipUtils {
	private final static Logger LOG = LoggerFactory.getLogger(ZipUtils.class);
    
    public static void doCompress(String srcFile, String zipFile) throws Exception {
        doCompress(new File(srcFile), new File(zipFile));
    }
    
    /**
     * 文件压缩
     * @param srcFile  目录或者单个文件
     * @param destFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File destFile) throws Exception {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destFile));
        if(srcFile.isDirectory()){
            File[] files = srcFile.listFiles();
            for(File file : files){
                doCompress(file, out);
            }
        }else {
            doCompress(srcFile, out);
        }
    }
    
    public static void doCompress(String pathname, ZipOutputStream out) throws IOException{
        doCompress(new File(pathname), out);
    }
    
    public static void doCompress(File file, ZipOutputStream out) throws IOException{
        if( file.exists() ){
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(file);
            out.putNextEntry(new ZipEntry(file.getName()));
            int len = 0 ;
            // 读取文件的内容,打包到zip文件    
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
            out.closeEntry();
            fis.close();
        }
    }
    
    
    public static final String GZIP_ENCODE_UTF_8 = "UTF-8";  
    
    public static final String GZIP_ENCODE_ISO_8859_1 = "ISO-8859-1";  
  
    /** 
     * 字符串压缩为GZIP字节数组 
     *  
     * @param str 
     * @return 
     */  
    public static byte[] compress(String str) {  
        return compress(str, GZIP_ENCODE_UTF_8);  
    }  
  
    /** 
     * 字符串压缩为GZIP字节数组 
     *  
     * @param str 
     * @param encoding 
     * @return 
     */  
    public static byte[] compress(String str, String encoding) {  
        if (str == null || str.length() == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        GZIPOutputStream gzip;  
        try {  
            gzip = new GZIPOutputStream(out);  
            gzip.write(str.getBytes(encoding));  
            gzip.close();  
        } catch (IOException e) {  
        	LOG.error("gzip compress error.", e);  
        }  
        return out.toByteArray();  
    }  
  
    /** 
     * GZIP解压缩 
     *  
     * @param bytes 
     * @return 
     */  
    public static byte[] uncompress(byte[] bytes) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
        try {  
            GZIPInputStream ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
        } catch (IOException e) {  
        	LOG.error("gzip uncompress error.", e);  
        }  
  
        return out.toByteArray();  
    }  
  
    /** 
     *  
     * @param bytes 
     * @return 
     */  
    public static String uncompressToString(byte[] bytes) {  
        return uncompressToString(bytes, GZIP_ENCODE_UTF_8);  
    }  
    public static void uncompressToString(InputStream inputStream,StringBuffer sb) {  
    	  if (inputStream == null) {  
              return ;  
          }  
          ByteArrayOutputStream out = new ByteArrayOutputStream();  
          try {  
              GZIPInputStream ungzip = new GZIPInputStream(inputStream);  
              byte[] buffer = new byte[256];  
              int n;  
              while ((n = ungzip.read(buffer)) >= 0) {  
                  out.write(buffer, 0, n);  
              }  
               sb.append(out.toString(GZIP_ENCODE_UTF_8));  
          } catch (IOException e) {  
          	LOG.error("gzip uncompress to string error.", e);  
          }  
    }  
    /** 
     *  
     * @param bytes 
     * @param encoding 
     * @return 
     */  
    public static String uncompressToString(byte[] bytes, String encoding) {  
        if (bytes == null || bytes.length == 0) {  
            return null;  
        }  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);  
        try {  
            GZIPInputStream ungzip = new GZIPInputStream(in);  
            byte[] buffer = new byte[256];  
            int n;  
            while ((n = ungzip.read(buffer)) >= 0) {  
                out.write(buffer, 0, n);  
            }  
            return out.toString(encoding);  
        } catch (IOException e) {  
        	LOG.error("gzip uncompress to string error.", e);  
        }  
        return null;  
    }  
  
    public static void main(String[] args) {  
    }  
    
}
