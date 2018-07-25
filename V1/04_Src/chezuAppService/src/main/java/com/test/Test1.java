package com.test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.idata365.app.util.DateTools;
import com.idata365.app.util.RandUtils;

public class Test1 {
	private static final Logger LOGGER = LoggerFactory.getLogger(Test1.class);

	public static void main(String[] args) {
		// Person person = new Person();
		// person.setAge(5);
		//
		// People people = new People();
		// AdBeanUtils.copyOtherPropToStr(people, person);
		// System.out.println(JSON.toJSONString(people));

		// test2UUID();

		// test1();

		// BigDecimal.valueOf(100).divide(null, 0, 0)

		// calBatch(100, 3);

		// int generateRand = RandUtils.generateRand(1, 200);

//		hidePhone();
		String s = "5520动力";
		System.out.println(s.replaceAll("动力", ""));
	}

	public static String hidePhone() {
		String phone = "18795860371";
		String prefixPhone = StringUtils.substring(phone, 0, 3);
		String suffixPhone = StringUtils.substring(phone, 7);

		String tempNewPhone = prefixPhone + "****" + suffixPhone;
		return tempNewPhone;
		// LOGGER.info(tempUserName);
	}

	public static void calBatch(int totalNum, int batchNUm) {
		BigDecimal totalNumBd = BigDecimal.valueOf(totalNum);
		BigDecimal batchNUmBd = BigDecimal.valueOf(batchNUm);
		BigDecimal resultBd = totalNumBd.divide(batchNUmBd, 0, BigDecimal.ROUND_HALF_UP);
		System.out.println("--------------" + resultBd.toString());
	}

	public static void test2UUID() {
		Set<String> inviteSet = new HashSet<>();

		for (int i = 0; i < 100; i++) {
			UUID uuid = UUID.randomUUID();
			String uuidStr = uuid.toString();
			String inviteCode = StringUtils.substring(uuidStr, 0, 8).toUpperCase();
			inviteSet.add(inviteCode);
		}

		LOGGER.info("size==={}", inviteSet.size());
		LOGGER.info(inviteSet.toString());
	}

	public static void test1() {
		String givenTime = "20171221122122";
		String dayStr = StringUtils.substring(givenTime, 0, 8);
		System.out.println(dayStr);
	}
}
