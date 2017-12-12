package com.test;

import org.apache.commons.lang3.StringUtils;

public class Test1
{

	public static void main(String[] args)
	{
//		Person person = new Person();
//		person.setAge(5);
//		
//		People people = new People();
//		AdBeanUtils.copyOtherPropToStr(people, person);
//		System.out.println(JSON.toJSONString(people));
		
		test1();
	}

	public static void test1()
	{
		String givenTime = "20171221122122";
		String dayStr = StringUtils.substring(givenTime, 0, 8);
		System.out.println(dayStr);
	}
}
