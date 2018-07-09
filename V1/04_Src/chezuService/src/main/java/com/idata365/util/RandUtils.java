package com.idata365.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.NumberFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandUtils
{
	private static final Logger LOGGER = LoggerFactory.getLogger(RandUtils.class);
	
	private static Random SECURE_RANDOM;
	
	static
	{
		try
		{
			SECURE_RANDOM = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e)
		{
			LOGGER.error("", e);
		}
	}
	
	public static int generateSecureRand(int min, int max)
	{
		return SECURE_RANDOM.nextInt(max - min + 1) + min;
	}
	
	public static int generateRand()
	{
		return generateRand(1, 8);
	}
	
	/**
	 * 生成随机数，范围[min, max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static int generateRand(int min, int max)
	{
//		int randNum = SECURE_RANDOM.nextInt(100);
		int randNum = ThreadLocalRandom.current().nextInt(max - min + 1) + min;
		return randNum;
	}
	
	/**
	 * 生成随机数，范围[min, max]
	 * @param min
	 * @param max
	 * @return
	 */
	public static double generateRandDouble(int min, int max)
	{
		double randNum = ThreadLocalRandom.current().nextDouble(max - min + 1) + min;
		return randNum;
	}
	
	public static double generateRandDouble()
	{
		double result = ThreadLocalRandom.current().nextDouble();
		return result;
	}
	
	
	public static String formattedDecimalToPercentage(double decimal)  
    {  
        //获取格式化对象  
        NumberFormat nt = NumberFormat.getPercentInstance();  
        //设置百分数精确度 即保留一位小数  
        nt.setMinimumFractionDigits(1);  
        return nt.format(decimal);  
    }  
	
	public static void main(String[] args)
	{
		
//		while (true)
//		{
////			int result = generateRand();
////			double result = generateRandDouble(1, 100);
////			double result = generateRand(1, 100);
//			int result = generateRand();
//			LOGGER.info("gen-------{}", result);
//			if (result == 8)
//				break;
//		}
		
		for (int i = 0; i < 100; i++)
		{
			int result = generateRand();
			LOGGER.info("gen-------{}", result);
		}
	}
}
