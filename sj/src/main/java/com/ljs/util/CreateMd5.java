package com.ljs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CreateMd5 {

	// 静态方法，便于作为工具类
	public static String getMd5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			// 32位加密
			return buf.toString();
			// 16位的加密
			// return buf.toString().substring(8, 24);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static void main(String[] args) {
		// 测试
		String d = "AP-RHY-TEST-1001V1701005AP192.168.1.1042017-01-10 02:46:05.847apf465291iuyt352f51e34c893688986";
		String d2= "AP-RHY-TEST-1001V1701005AP192.168.1.1042017-01-10 02:14:44.452apf465291iuyt352f51e34c893688986";
		String d3= "6ee72550-f656-4bf9-98a7-c7afd7a9ad6fV1701005AP192.168.1.1012017-01-10 21:57:19.045apf465291iuyt352f51e34c893688986";
		String d4= "22W853FKD@3~sunF.-0K3*waN[NTxiN123";

//		{
//	        "token":        "6ee72550-f656-4bf9-98a7-c7afd7a9ad6f",
//	        "version":      "V1701005AP",
//	        "ip":   "192.168.1.101",
//	        "reqTime":      "2017-01-10 21:57:19.045",
//	        "sign": "8afc56b158452ff06f68f5f8e0cf2dfc"
//	}
//	sign=8afc56b158452ff06f68f5f8e0cf2dfc
//	validString=c650f97ff5c1e60a5b8cec22ffbac69f
//  1004:1004f667ddfa180f471e5ca3fd349799   1003：fc8558d0914487bee31fd7d5ca26cca9   1002：8db5086a0d639d5c5e5575474c9c2baf 1001:d284e98ec2a3f024bf2dc899f19fd037  
		

//		}
		try {
			System.out
					.println(CreateMd5.getMd5("AP-RHY-TEST-1004Idealapf465291iuyt352f51e34c893688986"));
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
