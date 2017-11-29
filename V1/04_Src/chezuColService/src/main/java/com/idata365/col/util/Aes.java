package com.idata365.col.util;
import java.net.URLEncoder;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import com.alibaba.dubbo.common.json.JSONObject;
//import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.RandomStringUtils;

import net.sf.json.JSONObject;


/**
* @Author mingzhenbing671
* @Date 2016/11/23 16:09
* @Desc ASE加密解密类
*/
public class Aes {
	

  /**
  * @Author mingzhenbing671
  * @Date 2016/11/23 16:01
  * @1.构造密钥生成器
  * @2.根据ecnodeRules规则初始化密钥生成器
  * @3.产生密钥
  * @4.创建和初始化密码器
  * @5.内容加密
  * @6.返回字符串
  */
  private static byte[] getRawKey(byte[] seed) throws Exception {
      KeyGenerator kgen = KeyGenerator.getInstance("AES");
      SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
      sr.setSeed(seed);
      kgen.init(128, sr); // 192 and 256 bits may not be available
      SecretKey skey = kgen.generateKey();
      byte[] raw = skey.getEncoded();

      return raw;
  }

  public static String encryptData(String seed, String cleartext) throws Exception {

      byte[] rawKey = getRawKey(seed.getBytes());

      String randomData = RandomStringUtils.randomNumeric(16);
      byte[] iv = randomData.getBytes();
      System.out.println("randomData = " + randomData);
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
      Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
      cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
      byte[] encrypted = cipher.doFinal(cleartext.getBytes());

      return randomData + toHex(encrypted);
  }

  public static String decryptData(String seed, String encrypted, String ivStr) throws Exception {

      byte[] rawKey = getRawKey(seed.getBytes());
      byte[] enc = toByte(encrypted);

      byte[] iv = ivStr.getBytes();
      IvParameterSpec ivSpec = new IvParameterSpec(iv);

      SecretKeySpec skeySpec = new SecretKeySpec(rawKey, "AES");
      Cipher cipher = Cipher.getInstance("AES/CFB/NoPadding");
      cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
      byte[] decrypted = cipher.doFinal(enc);

      return new String(decrypted);
  }

  public static byte[] toByte(String hexString) {
      int len = hexString.length() / 2;
      byte[] result = new byte[len];
      for (int i = 0; i < len; i++) {
          result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
      }

      return result;
  }

  public static String toHex(byte[] buf) {

      if ( null == buf ) {
          return "";
      }

      StringBuffer result = new StringBuffer(2 * buf.length);
      for (int i = 0; i < buf.length; i++) {
          appendHex(result, buf[i]);
      }

      return result.toString();
  }

  private final static String HEX = "0123456789ABCDEF";

  private static void appendHex(StringBuffer sb, byte b) {
      sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
  }

  public static void main(String[] args) {
      try {
//    	  车牌  苏A-U0S57   车架 LS5W3ABE4FB004286    发动机F4MC01592  身份证  320105199212100058   

          JSONObject res = new JSONObject();
          

          
          
          String carNos = "粤B-91459";
          res.put("frameNo", "45334537239536779");
          res.put("carNo", URLEncoder.encode(carNos, "UTF-8"));
          //res.put("carNo", carNos);
          res.put("engineNo", "87541388797889756473");
          res.put("phone", "15851850572");
          res.put("reportNo", "");

//    	     粤B-91459
//    	  Frame：45334537239536779
//    	  Engine：87541388797889756473
//    	  Certificate：511602197312180433   
        
          
          
          
 
//        String carNos = "粤B-5949N";
//        res.put("frameNo", "LSVUH25N1C2079887");
//        res.put("carNo", URLEncoder.encode(carNos, "UTF-8"));
//        //res.put("carNo", carNos);
//        res.put("engineNo", "790746");
//        res.put("phone", "15851850572");
//        res.put("reportNo", "");    
       
//         粤B-5949N ()--
//        Frame：LSVUH25N1C2079887
//        Engine：790746
//        Certificate：642101197305170065
        
        
//        String carNos = "粤B-VBN55";
//        res.put("frameNo", "LFV2A11GWV3825545");
//        res.put("carNo", URLEncoder.encode(carNos, "UTF-8"));
//        //res.put("carNo", carNos);
//        res.put("engineNo", "27482401");
//        res.put("phone", "15851850572");
//        res.put("reportNo", "");  
          
//        粤B-VBN55 ()
//        Frame：LFV2A11GWV3825545
//        Engine：27482401
//        Certificate：826547196211173306
//         
          
        
//        String carNos = "粤A-89776";
//        res.put("frameNo", "GF65GFGFGFGFGFGFF");
//        res.put("carNo", URLEncoder.encode(carNos, "UTF-8"));
//        //res.put("carNo", carNos);
//        res.put("engineNo", "FGGHHG56GHGH");
//        res.put("phone", "15851850572");
//        res.put("reportNo", ""); 
        
//         粤A-89776
//        Frame：GF65GFGFGFGFGFGFF
//        Engine：FGGHHG56GHGH
//        Certificate：350101199202207337
        
          
//          String carNos = "粤B-HGF52";
//          res.put("frameNo", "32452342424344444");
//          res.put("carNo", URLEncoder.encode(carNos, "UTF-8"));
//          //res.put("carNo", carNos);
//          res.put("engineNo", "244234234");
//          res.put("phone", "15851850572");
//          res.put("reportNo", "");  
          
//        粤B-HGF52()  [b]
//        Frame：32452342424344444
//        Engine：244234234
//        Certificate：230601198001018631
        
        
        
//        String carNos = "粤B-HGHK3";
//        res.put("frameNo", "34643655435345444");
//        res.put("carNo", URLEncoder.encode(carNos, "UTF-8"));
//        //res.put("carNo", carNos);
//        res.put("engineNo", "346456456");
//        res.put("phone", "15851850572");
//        res.put("reportNo", ""); 
//        粤B-HGHK3 [b]
//        Frame：34643655435345444
//        Engine：346456456
//        Certificate：320101198001010212
          
          
          
          
          String content = encryptData("24bc67447d986a36fd8bfe631f9adfe3", res.toString());
          System.out.println(content.length());
          System.out.println("content = " + content);
          System.out.println("0---16  " + content.substring(0, 16));
          System.out.println("6---other  " + content.substring(16, content.length()));
          System.out.println("decrypt data = " + decryptData("24bc67447d986a36fd8bfe631f9adfe3", content.substring(16, content.length()), content.substring(0, 16)));

      } catch (Exception e) {
          e.printStackTrace();
      }
  }
}