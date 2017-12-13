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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idata365.app.controller.security.UserInfoController;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 
    * @ClassName: ImageUtils
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年12月13日
    *
 */
public class ImageUtils {
	private final static Logger LOG = LoggerFactory.getLogger(ImageUtils.class);
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
 	    bodys.put("pic", encodeImgageToBase64(new File("C:\\Users\\jinsheng\\Desktop\\行驶证demo.jpg")));


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
    /**
     * 
        * @Title: dealImg
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param file
        * @param @return    参数
        * @return String    返回类型
        * {
			    "status": "0",
			    "msg": "ok",
			    "result": {
			        "realname": "",
			        "engineno": "605911334FG",
			        "cartype": "京牌SAJAA22H",
			        "issuedate": "2013-01-21",
			        "regdate": "2012-05-10",
			        "frameno": "9AJAA22H6CFV34381",
			        "lstypename": "",
			        "lsprefix": "粤",
			        "lsnum": "AU3F61",
			        "lstype": ""
			    }
			}
        * @throws
        * @author LanYeYe
     */
    public static void dealImgXSZ(File file,Map<String,Object> map) {
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
 	    bodys.put("pic", encodeImgageToBase64(file));
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
 	    	//获取response的body
 	    	String rtStr=(EntityUtils.toString(response.getEntity()));
 	    	LOG.info(rtStr);
 	    	Map<String,Object> jsonMap=GsonUtils.fromJson(rtStr);
 	    	if(jsonMap.get("status").toString().equals("1")) {
 	    		Map<String,Object> result=(Map<String,Object>)(jsonMap.get("result"));
 	    		if(result!=null) {
 	    			map.put("engineNo", result.get("engineno"));
// 	    			map.put("carType", "");
// 	    			map.put("useType", "");
 	    			map.put("ownerName", result.get("realname"));
 	    			map.put("modelType", result.get("cartype"));
 	    			map.put("issueDate",result.get("issuedate"));
 	    			map.put("regDate",result.get("regdate"));
 	    			map.put("vin",result.get("frameno"));
 	    			map.put("plateNo", String.valueOf(result.get("lsprefix"))+result.get("lsnum"));
 	    			map.put("remark", rtStr);
 	    		}
 	    	}
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	    }
 	}
    /**
     *     
        * @Title: dealImgJSZ
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param     参数
        * @return void    返回类型
        * {
		    {
	    "status": "0",
	    "msg": "ok",
	    "result": {
        "realname": "郭敏",
        "licensenumber": "342221199005032081",
        "startdate": "2014-12-25",
        "enddate": "2020-12-25",
        "type": "C2",
        "sex": "女",
        "birth": "1990-05-03"
    }
}
		}
        * @throws
        * @author LanYeYe
     */
    public static void dealImgJSZ(File file,Map<String,Object> map) {
    	 String host = "http://jiashi.market.alicloudapi.com";
 	    String path = "/driverlicenserecognition/recognize";
 	    String method = "POST";
 	    String appcode = "4f9695958725417d83a47ac66358fe24";
 	    Map<String, String> headers = new HashMap<String, String>();
 	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
 	    headers.put("Authorization", "APPCODE " + appcode);
 	    //根据API的要求，定义相对应的Content-Type
 	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
 	    Map<String, String> querys = new HashMap<String, String>();
 	    Map<String, String> bodys = new HashMap<String, String>();
 	    bodys.put("pic",encodeImgageToBase64(file));
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
 	    	//获取response的body
 	    	String rtStr=(EntityUtils.toString(response.getEntity()));
 	    	LOG.info(rtStr);
 	    	Map<String,Object> jsonMap=GsonUtils.fromJson(rtStr);
 	    	if(jsonMap.get("status").toString().equals("1")) {
 	    		Map<String,Object> result=(Map<String,Object>)(jsonMap.get("result"));
 	    		if(result!=null) {
 	    			map.put("userName", result.get("realname"));
 	    			map.put("licenseNumber", result.get("licensenumber"));
 	    			String sd=result.get("startdate").toString();
 	    			String ed=result.get("enddate").toString();
 	    			map.put("validDay", result.get("startdate"));
 	    			map.put("validYears", getValidYears(sd,ed));
 	    			map.put("driveCardType", result.get("type"));
 	    			map.put("gender", result.get("sex").equals("女")?"F":"M");
 	    			map.put("birthday", result.get("birth"));
 	    			map.put("remark", rtStr);
 	    		}
 	    	}
 	    } catch (Exception e) {
 	    	e.printStackTrace();
 	    }
    }
    private static int getValidYears(String sd,String ed) {
    	try {
    	int sdInt=Integer.valueOf(sd.substring(0, 4));
    	int edInt=Integer.valueOf(ed.substring(0, 4));
    	return (edInt-sdInt);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return 0;
    }
    public static void main(String []args) {
//    	dealImgJSZ();
//    	System.out.print(encodeImgageToBase64(new File("C:\\Users\\jinsheng\\Desktop\\行驶证demo.jpg")).replaceAll("\r\n", ""));
    }
}

