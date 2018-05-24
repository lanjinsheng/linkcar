package com.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * _ooOoo_ o8888888o 88" . "88 (| -_- |) O\ = /O ____/`---'\____ .' \\| |// `. /
 * \\||| : |||// \ / _||||| -:- |||||- \ | | \\\ - /// | | | \_| ''\---/'' | | \
 * .-\__ `-` ___/-. / ___`. .' /--.--\ `. . __ ."" '< `.___\_<|>_/___.' >'"". |
 * | : `- \`.;`\ _ /`;.`/ - ` : | | \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'====== `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ 佛祖保佑 永无BUG
 * 
 */
public class GoodJump {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse("2018-05-24");
		Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-06");

		// 日期相减得到相差的日期
		long day = (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		System.out.println("相差的日期: " + day);

	}
}
