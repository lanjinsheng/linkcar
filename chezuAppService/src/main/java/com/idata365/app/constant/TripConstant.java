package com.idata365.app.constant;

import java.util.HashMap;
import java.util.Map;

import com.idata365.app.util.RandUtils;

public class TripConstant {
 
public static Map<Integer,String> tipMap=new HashMap<Integer,String>();

static {
	tipMap.put(1, "You Surely can drive better, can't you?");

	tipMap.put(2, "Score this poorly will tarnish your club's reputation");

	tipMap.put(3, "Let's see if we can improve the score next time!");

	tipMap.put(4, "Keep distance from the car in front of you and your score will be better!");

	tipMap.put(5, "You need to cut down the sudden breaks!");

	tipMap.put(6, "Turning too fast is kind of dangerous!");

	tipMap.put(7, "Spinning too fast will cause dizziness!");

	tipMap.put(8, "This isn't Fast and Furious!");

	tipMap.put(9, "You must be driving a Formula-One!");

	tipMap.put(101, "Perfect! Keep it up with the good driving habits!");
	}

public static String getTipByScore(Double score) {
	if(score<80) {
		int i=RandUtils.generateRand(1,9);
		return tipMap.get(i);
	}else {
		return tipMap.get(101);
	}
}
	public static String getTipByScore(Double score,int br,int tn,int ac) {
		if(score<=60) {
			return tipMap.get(1);
		}else if(score<=70){
			return tipMap.get(2);
		}else if(score<=80){
			return tipMap.get(3);
		}else if(score > 80) {
			if (br == 1) {
				return tipMap.get(4);
			} else if (br > 1){
				return tipMap.get(5);
		    }else if(tn ==1){
				return tipMap.get(6);
			}else if(tn > 1){
				return tipMap.get(7);
			}else if(ac ==1){
				return tipMap.get(8);
			}else if(ac > 1){
				return tipMap.get(9);
			}else{
				return tipMap.get(101);
			}
		}
      return null;
	}
public static void main(String []args) {
	System.out.println(TripConstant.getTipByScore(23d));
}

}
