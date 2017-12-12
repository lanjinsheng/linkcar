package com.idata365.app.util;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @author zxn
 * @version 创建时间：2014-7-2 上午11:40:40
 * 
 */
public class ImageUtils {
    /**
     * 将网络图片进行Base64位编码
     * 
     * @param imgUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(URL imageUrl) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageUrl);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将本地图片进行Base64位编码
     * 
     * @param imgUrl
     *            图片的url路径，如http://.....xx.jpg
     * @return
     */
    public static String encodeImgageToBase64(File imageFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        ByteArrayOutputStream outputStream = null;
        try {
            BufferedImage bufferedImage = ImageIO.read(imageFile);
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(outputStream.toByteArray());// 返回Base64编码过的字节数组字符串
    }

    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     * 
     * @param base64
     *            base64编码的图片信息
     * @return
     */
    public static void decodeBase64ToImage(String base64, String path,
            String imgName) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            FileOutputStream write = new FileOutputStream(new File(path
                    + imgName));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void test() {
        String host = "http://drivercard.aliapi.hanvon.com";
	    String path = "/rt/ws/v1/ocr/drivercard/recg";
	    String method = "POST";
	    String appcode = "4f9695958725417d83a47ac66358fe24";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/json; charset=UTF-8");
	    headers.put("Content-Type", "application/octet-stream");
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("code", "4f9695958725417d83a47ac66358fe24");
	    String bodys = "{\"uid\": \"118.22.0.12\",\"image\": \""+encodeImgageToBase64(new File("C:\\Users\\jinsheng\\Desktop\\行驶证demo.jpg")).replaceAll("\r\n", "")+"\"}";


	    try {
	    	/**
	    	* 重要提示如下:
	    	* HttpUtils请从
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	    	* 下载
	    	*
	    	* 相应的依赖请参照
	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	    	*/
	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
	    	System.out.println(response.toString());
	    	//获取response的body
	    	System.out.println(EntityUtils.toString(response.getEntity()));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
    }
    public static void test2() {
    	 String host = "http://xingshi.market.alicloudapi.com";
 	    String path = "/drivinglicenserecognition/recognize";
 	    String method = "POST";
 	    String appcode = "4f9695958725417d83a47ac66358fe24";
 	    Map<String, String> headers = new HashMap<String, String>();
 	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
 	    headers.put("Authorization", "APPCODE " + appcode);
 	    //根据API的要求，定义相对应的Content-Type
 	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
 	    Map<String, String> querys = new HashMap<String, String>();
 	    Map<String, String> bodys = new HashMap<String, String>();
 	    bodys.put("pic", encodeImgageToBase64(new File("C:\\Users\\jinsheng\\Desktop\\行驶证demo.jpg")).replaceAll("\r\n", ""));


 	    try {
 	    	/**
 	    	* 重要提示如下:
 	    	* HttpUtils请从
 	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
 	    	* 下载
 	    	*
 	    	* 相应的依赖请参照
 	    	* https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
 	    	*/
 	    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
 	    	System.out.println(response.toString());
 	    	//获取response的body
 	    	System.out.println(EntityUtils.toString(response.getEntity()));
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	    }
 	}
    public static void main(String []args) {
    	test();
//    	System.out.print(encodeImgageToBase64(new File("C:\\Users\\jinsheng\\Desktop\\行驶证demo.jpg")).replaceAll("\r\n", ""));
    }
}

