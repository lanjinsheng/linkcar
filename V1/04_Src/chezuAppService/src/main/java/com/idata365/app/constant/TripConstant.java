package com.idata365.app.constant;

import java.util.HashMap;
import java.util.Map;

import com.idata365.app.util.RandUtils;

public class TripConstant {
 
public static Map<Integer,String> tipMap=new HashMap<Integer,String>();

static {
		tipMap.put(1, "赶一赶，人生更苦短；让一让，文明风悠长！");
		tipMap.put(2, "这里不是秋名山，过弯还请慢点开！");
		tipMap.put(3, "带十分小心上路，携一份平安回家！");
		tipMap.put(4, "温馨红黄绿，和谐人车路！");
		tipMap.put(5, "出行慢慢哒，回家么么哒！");
		tipMap.put(6, "骚年，记得留下银行卡密码再狂飙！");
		tipMap.put(7, "累了您就歇歇脚，喝杯热水没烦恼！");
		tipMap.put(8, "一路疲乏，欲速则不达！");
		tipMap.put(9, "一日千里不算难，千金难买是安全！");
		tipMap.put(101, "没有比你更稳的了！老司机请带带我！");
	}

public static String getTipByScore(Double score) {
	if(score<80) {
		int i=RandUtils.generateRand(1,9);
		return tipMap.get(i);
	}else {
		return tipMap.get(101);
	}
}

public static void main(String []args) {
	System.out.println(TripConstant.getTipByScore(23d));
}

}
