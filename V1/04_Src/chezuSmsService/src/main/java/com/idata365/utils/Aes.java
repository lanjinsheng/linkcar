package com.idata365.utils;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//import com.alibaba.dubbo.common.json.JSONObject;
//import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.RandomStringUtils;


/**
* @Author lanyeye
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
  
          String content = encryptData("24bc67447d986a36fd8bfe631f9adfe3","12");
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