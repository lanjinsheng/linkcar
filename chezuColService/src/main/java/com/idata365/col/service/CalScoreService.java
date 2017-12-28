package com.idata365.col.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.col.entity.DriveDataEvent;
import com.idata365.col.entity.DriveDataMain;
import com.idata365.col.entity.DriveScore;
import com.idata365.col.mapper.DriveDataEventMapper;
import com.idata365.col.mapper.DriveDataMainMapper;
import com.idata365.col.util.DateTools;
@Service
public class CalScoreService extends BaseService<DataService>{
	private final static Logger LOG = LoggerFactory.getLogger(DataService.class);
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
	public DriveScore calScoreByUH(Long userId,Long habitId){
		DriveScore ds=new DriveScore();
		ds.setUserId(userId);
		ds.setHabitId(habitId);
		int jiaScore=5;int jianScore=5;int zhuanScore=5;int chaoScore=6;
		int phoneUserScore=10;int distanceScore=10;
		int nightScore=10;int triedScore=10;int maxSpeedScore=12;
		
		//获取行程main，获取行程event
		DriveDataMain main=new DriveDataMain();
		main.setUserId(userId);
		main.setHabitId(habitId);
		DriveDataMain dm=driveDataMainMapper.getDriveDataMainByUH(main);
		//====最大速度得分计算，3分钟,按180个点来计算====
		int up160=dm.getSpeed160UpTimes();
		int up150=dm.getSpeed160UpTimes()+dm.getSpeed150To159Times();
		int up140=up150+dm.getSpeed140To149Times();
		int up130=up140+dm.getSpeed130To139Times();
		int up120=up130+dm.getSpeed120To129Times();
		if(up160>=180) {
			maxSpeedScore=0;
		}else if(up150>=180) {
			maxSpeedScore=2;
		}else if(up140>=180) {
			maxSpeedScore=4;
		}else if(up130>=180) {
			maxSpeedScore=6;
		}else if(up120>=180) {
			maxSpeedScore=8;
		}
       //====里程数得分计算=====
		//		0-10KM		1
		//		10-15KM		2
		//		15-20KM		3
		//		20-25KM		4
		//		25-30KM		5
		//		30-35KM		6
		//		35-40KM		7
		//		40-45KM		8
		//		45-50KM		9
		//		50KM+		10
        Double driveDistance=dm.getDriveDistance().doubleValue();
		if(driveDistance>=0 && driveDistance<10000) {
			distanceScore=1;
		}else if(driveDistance>=10000 && driveDistance<15000){
			distanceScore=2;
		}else if(driveDistance>=15000 && driveDistance<20000){
			distanceScore=3;
		}else if(driveDistance>=20000 && driveDistance<25000){
			distanceScore=4;
		}else if(driveDistance>=25000 && driveDistance<30000){
			distanceScore=5;
		}else if(driveDistance>=30000 && driveDistance<35000){
			distanceScore=6;
		}else if(driveDistance>=35000 && driveDistance<40000){
			distanceScore=7;
		}else if(driveDistance>=40000 && driveDistance<45000){
			distanceScore=8;
		}else if(driveDistance>=45000 && driveDistance<50000){
			distanceScore=9;
		}
	//====夜间驾驶计算====+====疲劳驾驶计算====
		
		String startTime=dm.getDriveStartTime();
		String endTime=dm.getDriveEndTime();
		Long startLong=DateTools.changeDateTimeToSecond(startTime);
		Long endLong=DateTools.changeDateTimeToSecond(endTime);
		
		String dayStart=startTime.substring(0,10)+" 00:00:00.000";
		String dayEnd=endTime.substring(0,10)+" 00:00:00.000";
		Long dayTime0=DateTools.changeDateTimeToSecond(dayStart);
		Long dayTimeEnd=DateTools.changeDateTimeToSecond(dayEnd);
		
		int tiredSecond=0;
		
		
		if(dayTimeEnd==dayTime0) {
			//同一天
			//凌晨计算一段
			Long dayTime1=dayTime0+3600;
			Long dayTime5=dayTime0+5*3600;
			boolean hadCal=false;
			long smax=max(startLong,dayTime0);
			long emin=min(endLong,dayTime5);
			if(emin>smax) {
				tiredSecond+=(int)(emin-smax);
			}
			if((emin-smax)>=180) {//3分钟
				if(smax>=dayTime1 && emin<=dayTime5) {//1-5之间 10分没了
					nightScore=0;
				}else if(smax>=dayTime0 && emin<=dayTime5) {
					nightScore=2;
				}
				hadCal=true;
			}
			//夜间一段
				Long dayTime22=dayTime0+22*3600;
				Long dayTime23=dayTime0+23*3600;
				Long dayTime24=dayTime0+24*3600;
				smax=max(startLong,dayTime22);
				emin=min(endLong,dayTime24);
				if(emin>smax) {
					tiredSecond+=(int)(emin-smax);
				}
				if(hadCal&& (emin-smax)>=180) {//3分钟
					if(smax>=dayTime23 && emin<=dayTime24) {
						nightScore=4;
					}else if(smax>=dayTime22 && emin<=dayTime24) {
						nightScore=6;
					}
				}
			
		}else if(dayTimeEnd>dayTime0) {//隔天了
			Long dayTime22=dayTime0+22*3600;
			Long dayTime23=dayTime0+23*3600;
			Long dayTime24=dayTime0+24*3600;
			Long dayTime1=dayTimeEnd+3600;
			Long dayTime5=dayTimeEnd+5*3600;
			long smax=max(startLong,dayTime22);
			long emin=min(endLong,dayTime5);
			if(emin>smax) {
				tiredSecond+=(int)(emin-smax);
			}
			if(emin-smax>=180) {//3分钟
				if(smax>=dayTime1 && emin<=dayTime5) {//1-5之间 10分没了
					nightScore=0;
				}else if(smax>=dayTime24 && emin<=dayTime5) {
					nightScore=2;
				}else if(smax>=dayTime23 && emin<=dayTime5) {
					nightScore=4;
				}else if(smax>=dayTime22 && emin<=dayTime5) {
					nightScore=6;
				}
			}
		}
		
		
   //====疲劳驾驶计算,夜间22：00点到4:59 每行驶1分钟算2分钟====
		long allSecond=dayTimeEnd-dayTime0;
		if(tiredSecond>0) {
			allSecond+=tiredSecond;
		}else {
			
		}
		if(allSecond>(5*3600)) {
			triedScore=0;
		}else if(allSecond>(4*3600)) {
			triedScore=2;
		}else if(allSecond>(3*3600)) {
			triedScore=4;
		}else if(allSecond>(2*3600)) {
			triedScore=6;
		}
		
		//====三急超速计算====
		//加速参数
		int jiaTimes=0;
		int jiaLevel=0;
		Long lastJiaTime=0L;
		
		//减速参数
		int jianTimes=0;
		int jianLevel=0;
		Long lastJianTime=0L;
		
		//急转参数
		int zhuanTimes=0;
		int zhuanLevel=0;
		Long lastZhuanTime=0L;
		
		//超速参数
		int chaoTimes=0;
		int chaoTimes_Level1=0;
		int chaoTimes_Level2=0;
		int chaoTimes_Level3=0;
		int chaoTimes_Level4=0;
		
		
		List<DriveDataEvent> events=driveDataEventMapper.listDriveEventByMainId(dm);
		for(DriveDataEvent event:events) {
			int type=event.getAlarmType();
			if(type==1) {
				jiaTimes++;
				if(jiaLevel==2) {
					continue;
				}
				if(lastJiaTime==0) {
					lastJiaTime=DateTools.changeDateTimeToSecond(event.getStartTime());
					jiaLevel=1;
				}else {
					long tempJiaTime=DateTools.changeDateTimeToSecond(event.getStartTime());
					if((tempJiaTime-lastJiaTime)>300) {//超过5分钟，重置
						lastJiaTime=tempJiaTime;
					}else {//小于5分钟，
						jiaLevel=2;
					}
				}
			}else if(type==2) {
				jianTimes++;
				if(jianLevel==2) {
					continue;
				}
				if(lastJianTime==0) {
					lastJianTime=DateTools.changeDateTimeToSecond(event.getStartTime());
					jianLevel=1;
				}else {
					long tempJianTime=DateTools.changeDateTimeToSecond(event.getStartTime());
					if((tempJianTime-lastJianTime)>300) {//超过5分钟，重置
						lastJianTime=tempJianTime;
					}else {//小于5分钟，
						jianLevel=2;
					}
				}
			}else if(type==3) {
				zhuanTimes++;
				if(zhuanLevel==2) {
					continue;
				}
				if(lastZhuanTime==0) {
					lastZhuanTime=DateTools.changeDateTimeToSecond(event.getStartTime());
					zhuanLevel=1;
				}else {
					long tempZhuanTime=DateTools.changeDateTimeToSecond(event.getStartTime());
					if((tempZhuanTime-lastZhuanTime)>300) {//超过5分钟，重置
						lastZhuanTime=tempZhuanTime;
					}else {//小于5分钟，
						zhuanLevel=2;
					}
				}
				
			}else if(type==4){
				chaoTimes++;
				BigDecimal mySpeed=event.getAlarmValue();
				BigDecimal limitSpeed=event.getMaxspeed();
				BigDecimal over=limitSpeed.subtract(mySpeed);
				Double overPecent=over.divide(limitSpeed,3,RoundingMode.HALF_UP).doubleValue();
				if(overPecent>=0.70) {
					chaoTimes_Level4++;
				}else if(overPecent>=0.5 && overPecent<0.7) {
					chaoTimes_Level3++;
				}else if(overPecent>=0.2 && overPecent<0.5) {
					chaoTimes_Level2++;
				}else if(overPecent>=0.1 && overPecent<0.2){
					chaoTimes_Level1++;
				}
			}
		}
		
		if(jiaLevel==2)
		{
			jiaScore=0;
		}else if(jiaLevel==1) {
			jiaScore=3;
		}
		
		if(jianLevel==2)
		{
			jianScore=0;
		}else if(jianLevel==1) {
			jianScore=3;
		}
		
		
		if(zhuanLevel==2)
		{
			zhuanScore=0;
		}else if(zhuanLevel==1) {
			zhuanScore=3;
		}
		
		if(chaoTimes_Level4>0) {
			if(chaoTimes_Level4<=60) {
				chaoScore=1;
			}else {
				chaoScore=0;
			}
		}else if(chaoTimes_Level3>0) {
			if(chaoTimes_Level3<=60) {
				chaoScore=2;
			}else {
				chaoScore=1;
			}
		}else if(chaoTimes_Level2>0) {
			if(chaoTimes_Level2<=60) {
				chaoScore=3;
			}else {
				chaoScore=2;
			}
		}else if(chaoTimes_Level1>0) {
			if(chaoTimes_Level1<=60) {
				chaoScore=4;
			}else {
				chaoScore=3;
			}
		}
		ds.setBrakeScore(BigDecimal.valueOf(jianScore));
		ds.setCalStatus(1);
		ds.setDistanceScore(BigDecimal.valueOf(distanceScore));
		ds.setMaxSpeedScore(BigDecimal.valueOf(maxSpeedScore));
		ds.setNightDrivingScore(BigDecimal.valueOf(nightScore));
		ds.setOverSpeedScore(BigDecimal.valueOf(chaoScore));
		ds.setPhonePlayScore(BigDecimal.valueOf(phoneUserScore));
		ds.setSpeedUpScore(BigDecimal.valueOf(jiaScore));
		ds.setTiredDrivingScore(BigDecimal.valueOf(triedScore));
		ds.setTurnScore(BigDecimal.valueOf(zhuanScore));
		return ds;
	}
	
	private long min(long a,long b) {
		if(a>b) {
			return b;
		}else {
			return a;
		}
	}
	
	private long max(long a,long b) {
		if(a>b) {
			return a;
		}else {
			return b;
		}
	}
	
	public static void main(String []args)
	{
		System.out.println("2017-11-24 10:47:32.336".substring(0,10));
	}
}
