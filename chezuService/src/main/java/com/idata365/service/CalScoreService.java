package com.idata365.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.CalDriveTask;
import com.idata365.entity.DriveDataEvent;
import com.idata365.entity.DriveDataMain;
import com.idata365.entity.DriveScore;
import com.idata365.entity.LotteryBean;
import com.idata365.entity.ReadyLotteryBean;
import com.idata365.entity.UserFamilyRoleLog;
import com.idata365.entity.UserTravelHistory;
import com.idata365.entity.bean.FourAlarmBean;
import com.idata365.entity.bean.TiredCalBean;
import com.idata365.mapper.app.LotteryMapper;
import com.idata365.mapper.app.UserFamilyScoreMapper;
import com.idata365.mapper.app.UserTravelHistoryMapper;
import com.idata365.mapper.col.CalDriveTaskMapper;
import com.idata365.mapper.col.DriveDataEventMapper;
import com.idata365.mapper.col.DriveDataMainMapper;
import com.idata365.mapper.col.DriveScoreMapper;
import com.idata365.util.DateTools;

@Service
public class CalScoreService extends BaseService<CalScoreService>{
	private final static Logger LOG = LoggerFactory.getLogger(CalScoreService.class);
	@Autowired
	DriveDataMainMapper driveDataMainMapper;
	@Autowired
	DriveDataEventMapper driveDataEventMapper;
	@Autowired
	DriveScoreMapper driveScoreMapper;
	@Autowired
	UserFamilyScoreMapper userFamilyScoreMapper;
	@Autowired
	CalDriveTaskMapper calDriveTaskMapper;
	
	@Autowired
	private LotteryMapper lotteryMapper;
	@Autowired
	UserTravelHistoryMapper userTravelHistoryMapper;
	 
	/**
	 * 查询装配的道具列表
	 * @param userId
	 * @return
	 */
	public List<LotteryBean> queryReadyLottery(long userId)
	{
		ReadyLotteryBean paramBean = new ReadyLotteryBean();
		paramBean.setUserId(userId);
		paramBean.setDaystamp(getCurrentDayStr());
		
		List<LotteryBean> resultList = this.lotteryMapper.queryReadyLottery(paramBean);
		
		return resultList;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, "yyyyMMdd");
		return dayStr;
	}
	
	
	/**
	 * 消耗已装配的道具接口
	 * @param userId
	 * @param awardId
	 * @param consumeCount
	 * @return	
	 */
	public boolean consumeLottery(List<LotteryBean> paramList,Long userId)
	{
		String currentDayStr = getCurrentDayStr();
		
		for (LotteryBean tempBean : paramList)
		{
			tempBean.setDaystamp(currentDayStr);
			tempBean.setUserId(userId);
			this.lotteryMapper.updateReadyLotteryStatus(tempBean);
		}
		
		return true;
	}
	
	
	public List<DriveScore> getDriveScoreByUH(Long userId,Long habitId) {
		DriveScore driveScore=new DriveScore();
		driveScore.setUserId(userId);
		driveScore.setHabitId(habitId);
		List<DriveScore> dsList=driveScoreMapper.getDriveScoreByUH(driveScore);
		return dsList;
	}
	
//	
//	public void insertScore(DriveScore driveScore) {
//		driveScoreMapper.insertScore(driveScore);
//	}
//	
//	
	
	public List<UserFamilyRoleLog> getRolesByUserIdTime(Long userId,String driveEndTime){
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("userId", userId);
		m.put("driveEndTime", driveEndTime);
		return userFamilyScoreMapper.getUserRoles(m);
	}
	
	private int useFaDongJi(DriveDataMain dm,int fadongjiSecond) {
		if(fadongjiSecond>0) {
			fadongjiSecond=fadongjiSecond-dm.getSpeed160UpTimes();
			if(fadongjiSecond>0) {
				dm.setSpeed160UpTimes(0);
				fadongjiSecond=fadongjiSecond-dm.getSpeed150To159Times();
				if(fadongjiSecond>0) {
					dm.setSpeed150To159Times(0);
					fadongjiSecond=fadongjiSecond-dm.getSpeed140To149Times();
					if(fadongjiSecond>0) {
						dm.setSpeed140To149Times(0);
						fadongjiSecond=fadongjiSecond-dm.getSpeed130To139Times();
						if(fadongjiSecond>0) {
							dm.setSpeed130To139Times(0);
							fadongjiSecond=fadongjiSecond-dm.getSpeed120To129Times();
							if(fadongjiSecond>0) {
								dm.setSpeed120To129Times(0);
							}else {
								dm.setSpeed120To129Times(-(fadongjiSecond));
							}
						}else {
							dm.setSpeed130To139Times(-(fadongjiSecond));
						}
					}else {
						dm.setSpeed140To149Times(-(fadongjiSecond));
					}
				}else {
					dm.setSpeed150To159Times(-(fadongjiSecond));
				}
			}else {
				dm.setSpeed160UpTimes(-(fadongjiSecond));
			}
			
		} 
		return fadongjiSecond;
	}
	
	final static int FADONGJI=3;
	final static int SHACHEPIAN=1;
	final static int CHELUNTAI=2;
	final static int HONGNIU=4;
	final static int YESHIJING=5;
	final static int ZENGYAQI=6;
 
	/**
	 * 
	    * @Title: calMaxSpeedScore
	    * @Description: TODO(最高速算分)
	    * @param @param ds
	    * @param @param loteryBeans
	    * @param @param fadongji
	    * @param @param dm
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private int calMaxSpeedScore(UserTravelHistory userTravelHistory,List<LotteryBean> loteryBeans,int fadongji,DriveDataMain dm) {
		int maxSpeedScore=10;
		//====最大速度得分计算，3分钟,按180个点来计算====
		int useFadongji=0;//使用发动机
		int highSpeedTimesOffset=0;//抵消点数
		//使用道具fadongji
		if(fadongji>0) {
			int fadongjiSecond=fadongji*60;
			int leftFadongjiSecond=useFaDongJi(dm,fadongjiSecond);
			if(leftFadongjiSecond>0) {
				 int leftFadongji=BigDecimal.valueOf(fadongjiSecond).divide(BigDecimal.valueOf(60),0,RoundingMode.DOWN).intValue();
				 useFadongji=fadongji-leftFadongji;
				 highSpeedTimesOffset=fadongjiSecond-leftFadongjiSecond;
			}else {
				  useFadongji=fadongji;
				  highSpeedTimesOffset=fadongjiSecond;
			}
			if(useFadongji>0){
				LotteryBean loteryBean=new LotteryBean();
				loteryBean.setAwardCount(useFadongji);
				loteryBean.setAwardId(FADONGJI);
				loteryBeans.add(loteryBean);
			}
		}
		
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
			userTravelHistory.setHighSpeedTimesOffset(highSpeedTimesOffset);
			userTravelHistory.setUseFadongji(useFadongji);
			return maxSpeedScore;
	}
	/**
	 * 
	    * @Title: calDistanceScore
	    * @Description: TODO(行程得分)
	    * @param @param userTravelHistory
	    * @param @param dm
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private int calDistanceScore(UserTravelHistory userTravelHistory,DriveDataMain dm) {
		   int distanceScore=10;
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
				return distanceScore;
	}
	/**
	 * 
	    * @Title: calTriedScore
	    * @Description: TODO(疲劳驾驶计算)
	    * @param @param userTravelHistory
	    * @param @param dm
	    * @param @param hongniu
	    * @param @param tiredCalBean
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private int calTriedScore(UserTravelHistory userTravelHistory,DriveDataMain dm,List<LotteryBean> loteryBeans,int hongniu,TiredCalBean tiredCalBean) {
		int triedScore=10;
		int useHongniu=0;//使用红牛
		int tiredDriveOffset=0;
		 //====疲劳驾驶计算,夜间22：00点到4:59 每行驶1分钟算2分钟====
		long allSecond=tiredCalBean.getAllSecond();
		int tiredSecond=tiredCalBean.getTiredSecond();
		if(tiredSecond>0) {
			allSecond+=tiredSecond;
		}else {
			
		}
		//道具使用，红牛抵消
		long leftAllSecond=hongniu*3600-allSecond;
		 if(leftAllSecond>0) {
			 tiredDriveOffset=(int)allSecond;
			 allSecond=0;
			 int leftHongniu=BigDecimal.valueOf(leftAllSecond).divide(BigDecimal.valueOf(3600),0,RoundingMode.DOWN).intValue();
			 useHongniu=hongniu-leftHongniu;
		 }else {
			 useHongniu =hongniu;
			 tiredDriveOffset=hongniu*3600;
		 }
		 if(useHongniu>0) {
		 LotteryBean loteryBean=new LotteryBean();
			loteryBean.setAwardCount(useHongniu);
			loteryBean.setAwardId(HONGNIU);
			loteryBeans.add(loteryBean);
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
		userTravelHistory.setTiredDrive(allSecond);
		userTravelHistory.setTiredDriveOffset(tiredDriveOffset);
		userTravelHistory.setUseHongniu(useHongniu);
		return triedScore;
	}
	/**
	 * 
	    * @Title: calNightScore
	    * @Description: TODO(夜间行驶)
	    * @param @param userTravelHistory
	    * @param @param dm
	    * @param @param yeshijing
	    * @param @param tiredCalBean
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	private int calNightScore(UserTravelHistory userTravelHistory,DriveDataMain dm,List<LotteryBean> loteryBeans,int yeshijing,TiredCalBean tiredCalBean) {
		int nightScore=10;
		int useYeshijing=0;
		int nightDriveOffset=0;
		//====夜间驾驶计算====+====疲劳驾驶计算====
		
				String startTime=dm.getDriveStartTime();
				String endTime=dm.getDriveEndTime();
				Long startLong=DateTools.changeDateTimeToSecond(startTime);
				Long endLong=DateTools.changeDateTimeToSecond(endTime);
				
				String dayStart=startTime.substring(0,10)+" 00:00:00.000";
				String dayEnd=endTime.substring(0,10)+" 00:00:00.000";
				Long dayTime0=DateTools.changeDateTimeToSecond(dayStart);
				Long dayTimeEnd=DateTools.changeDateTimeToSecond(dayEnd);
				int nightTimeSeconds=0;
				int leftYeshijingSecond=yeshijing*3600;
				
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
						nightTimeSeconds=(int)(emin-smax);
					}
					//使用道具夜视镜
					if(leftYeshijingSecond>0 && nightTimeSeconds>0) {
						leftYeshijingSecond=(leftYeshijingSecond-nightTimeSeconds);
						if(leftYeshijingSecond>=0) {
							nightTimeSeconds=0;
						}else {
							nightTimeSeconds=-(leftYeshijingSecond);
						}
					}
					
					if(nightTimeSeconds>=180) {//3分钟
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
							nightTimeSeconds=(int)(emin-smax);
						}
						//使用道具夜视镜
						if(leftYeshijingSecond>0 && nightTimeSeconds>0) {
							leftYeshijingSecond=(leftYeshijingSecond-nightTimeSeconds);
							if(leftYeshijingSecond>=0) {
								nightTimeSeconds=0;
							}else {
								nightTimeSeconds=-(leftYeshijingSecond);
							}
						}
						if(!hadCal && nightTimeSeconds>=180) {//3分钟
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
						nightTimeSeconds=(int)(emin-smax);
					}
					//使用道具夜视镜
					if(leftYeshijingSecond>0 && nightTimeSeconds>0) {
						leftYeshijingSecond=(leftYeshijingSecond-nightTimeSeconds);
						if(leftYeshijingSecond>=0) {
							nightTimeSeconds=0;
						}else {
							nightTimeSeconds=-(leftYeshijingSecond);
						}
					}
					if(nightTimeSeconds>=180) {//3分钟
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
				if(leftYeshijingSecond>0) {
					 int leftFadongji=BigDecimal.valueOf(leftYeshijingSecond).divide(BigDecimal.valueOf(3600),0,RoundingMode.DOWN).intValue();
					 useYeshijing=yeshijing-leftFadongji;
					 nightDriveOffset=(yeshijing*3600)-leftYeshijingSecond;
				}else {
					  useYeshijing =yeshijing;
					  nightDriveOffset=yeshijing*3600;
				}
				if(useYeshijing>0) {
				 LotteryBean loteryBean=new LotteryBean();
				 loteryBean.setAwardCount(useYeshijing);
				 loteryBean.setAwardId(YESHIJING);
				 loteryBeans.add(loteryBean);
				}
				long allSecond=dayTimeEnd-dayTime0;
				tiredCalBean.setAllSecond(allSecond);
				tiredCalBean.setTiredSecond(tiredSecond);
				userTravelHistory.setNightDrive(tiredSecond);
				userTravelHistory.setNightDriveOffset(nightDriveOffset);
				userTravelHistory.setUseYeshijing(useYeshijing);
				return nightScore;
	}
/**
 * 
    * @Title: calFourAlarm
    * @Description: TODO(三急与超速)
    * @param @param dm
    * @param @param fourAlarmBean
    * @param @param shachepian
    * @param @param cheluntai
    * @param @param zengyaqi    参数
    * @return void    返回类型
    * @throws
    * @author LanYeYe
 */
	
	private void calFourAlarm(UserTravelHistory userTravelHistory,DriveDataMain dm,FourAlarmBean fourAlarmBean,List<LotteryBean> loteryBeans,int shachepian,int cheluntai,int zengyaqi ) {
		int jiaScore=5;int jianScore=5;int zhuanScore=5;int chaoScore=6;
		int brakeTimesOffset=0;
		int useShachepian=0;//使用刹车片
		
		int useCheluntai=0;//使用车轮胎
		int turnTimesOffset=0;
		
		int overspeedTimesOffset=0;
		int useZengyaqi=0;
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
				
				int leftShachepian=shachepian;
				int leftCheluntai=cheluntai;
				int leftZengyaqi=zengyaqi*60;
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
						if(leftShachepian>0) {
							leftShachepian--;
							brakeTimesOffset++;
							continue;
						}
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
						if(leftCheluntai>0) {
							leftCheluntai--;
							turnTimesOffset++;
							continue;
						}
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
						if(leftZengyaqi>0) {
							leftZengyaqi--;
							overspeedTimesOffset++;
							continue;
						}
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
				
				useCheluntai=cheluntai-leftCheluntai;
				useShachepian=shachepian-leftShachepian;
				useZengyaqi=BigDecimal.valueOf(zengyaqi).subtract(BigDecimal.valueOf(leftZengyaqi).divide(BigDecimal.valueOf(60),0,RoundingMode.DOWN)).intValue();
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
				if(useCheluntai>0) {
					 LotteryBean loteryBean=new LotteryBean();
					 loteryBean.setAwardCount(useCheluntai);
					 loteryBean.setAwardId(CHELUNTAI);
					 loteryBeans.add(loteryBean);
				}
				if(useShachepian>0) {
					 LotteryBean loteryBean=new LotteryBean();
					 loteryBean.setAwardCount(useShachepian);
					 loteryBean.setAwardId(SHACHEPIAN);
					 loteryBeans.add(loteryBean);
				}
				if(useZengyaqi>0) {
					 LotteryBean loteryBean=new LotteryBean();
					 loteryBean.setAwardCount(useZengyaqi);
					 loteryBean.setAwardId(ZENGYAQI);
					 loteryBeans.add(loteryBean);
				}
				userTravelHistory.setBrakeTimesOffset(brakeTimesOffset);
				userTravelHistory.setTurnTimesOffset(turnTimesOffset);
				userTravelHistory.setOverspeedTimesOffset(overspeedTimesOffset);
				userTravelHistory.setUseShachepian(useShachepian);
				userTravelHistory.setUseCheluntai(useCheluntai);
				userTravelHistory.setUseZengyaqi(useZengyaqi);
				fourAlarmBean.setChaoScore(chaoScore);
				fourAlarmBean.setJianScore(jianScore);
				fourAlarmBean.setJiaScore(jiaScore);
				fourAlarmBean.setZhuanScore(zhuanScore);
				
				
	}
	/**
	 * 
	    * @Title: calScoreByUH
	    * @Description: TODO(计算行程业务分)
	    * @param @param userId
	    * @param @param habitId
	    * @param @return    参数
	    * @return List<DriveScore>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public List<DriveScore> calScoreByUHInsertDb(Long userId,Long habitId){
		List<DriveScore> driveScoreList=new ArrayList<DriveScore>();
		List<LotteryBean> loteryBeans=new ArrayList<LotteryBean>();
		DriveScore ds=new DriveScore();
		ds.setUserId(userId);
		ds.setHabitId(habitId);
		UserTravelHistory userTravelHistory=new UserTravelHistory();
		userTravelHistory.setUserId(userId);
		userTravelHistory.setHabitId(habitId);
		userTravelHistory.setSpeedTimesOffset(0);
		int phoneUserScore=10;
		//获取行程main，获取行程event
		DriveDataMain main=new DriveDataMain();
		main.setUserId(userId);
		main.setHabitId(habitId);
		DriveDataMain dm=driveDataMainMapper.getDriveDataMainByUH(main);
		String driveEndTime=dm.getDriveEndTime().substring(0, 19);
		//通过driveEndTime 获取用户的角色与familyId
		List<UserFamilyRoleLog> roles=this.getRolesByUserIdTime(userId, driveEndTime);
		//通过userId,获取装配的道具
		List<LotteryBean> lotterys=queryReadyLottery(userId);
		int shachepian=0;//急刹
		int cheluntai=0;//急转
		int zengyaqi=0;//超速点
		int fadongji=0;//高速点
		int hongniu=0;//疲劳
		int yeshijing=0;//夜间驾驶
		
		for(LotteryBean lottery:lotterys) {
			if(lottery.getAwardId()==1) {
				shachepian=lottery.getAwardCount();
			}else if(lottery.getAwardId()==2) {
				cheluntai=lottery.getAwardCount();
			}else if(lottery.getAwardId()==FADONGJI) {
				fadongji=lottery.getAwardCount();
			}else if(lottery.getAwardId()==4) {
				hongniu=lottery.getAwardCount();
			}else if(lottery.getAwardId()==5) {
				yeshijing=lottery.getAwardCount();
			}else if(lottery.getAwardId()==6) {
				zengyaqi=lottery.getAwardCount();
			}
			
		}
		int maxSpeedScore=calMaxSpeedScore(userTravelHistory, loteryBeans, fadongji, dm);
		int distanceScore=calDistanceScore(userTravelHistory,dm);
		
		TiredCalBean tiredCalBean=new TiredCalBean();
		int nightScore=calNightScore(userTravelHistory, dm,loteryBeans, yeshijing,tiredCalBean);
		int triedScore=calTriedScore(userTravelHistory, dm, loteryBeans,hongniu, tiredCalBean);
		FourAlarmBean fourAlarmBean=new FourAlarmBean();
		
		calFourAlarm(userTravelHistory,dm,fourAlarmBean,loteryBeans,shachepian,cheluntai,zengyaqi);
		int jiaScore=fourAlarmBean.getJiaScore();int jianScore=fourAlarmBean.getJianScore();
		int zhuanScore=fourAlarmBean.getZhuanScore();int chaoScore=fourAlarmBean.getChaoScore();
//		1	老司机	急刹车	急刹车惩罚翻倍	刹车片	10
//		2	漂移手	急转弯	急加速、急转弯惩罚翻倍	车轮胎	10
//		3	渣土车手机	最高时速	最高时速惩罚翻倍	发动机	10
//		4	点头司机	疲劳驾驶	疲劳驾驶惩罚翻倍	红牛	10
//		5	红眼司机	夜间驾驶	夜间驾驶惩罚翻倍	夜视镜	10
//		6	飙车党	超速	超速惩罚翻倍	增压器	10
//		7	煎饼侠	无（不开车）	无需开车，可以抢车位、贴条	小马扎	5
		ds.setBrakeScore(BigDecimal.valueOf(jianScore));
		ds.setDistanceScore(BigDecimal.valueOf(distanceScore));
		ds.setMaxSpeedScore(BigDecimal.valueOf(maxSpeedScore));
		ds.setNightDrivingScore(BigDecimal.valueOf(nightScore));
		ds.setOverSpeedScore(BigDecimal.valueOf(chaoScore));
		ds.setPhonePlayScore(BigDecimal.valueOf(phoneUserScore));
		ds.setSpeedUpScore(BigDecimal.valueOf(jiaScore));
		ds.setTiredDrivingScore(BigDecimal.valueOf(triedScore));
		ds.setTurnScore(BigDecimal.valueOf(zhuanScore));
		for(UserFamilyRoleLog role:roles) {
			DriveScore nDs=(DriveScore) ds.clone();
			int roleId=role.getRole();
			if(roleId==1) {
				int tempJianScore=10-2*(10-jianScore);
				tempJianScore=(tempJianScore>0?tempJianScore:0);
				nDs.setBrakeScore(BigDecimal.valueOf(tempJianScore));
			}else if(roleId==2) {
				int tempZhuanScore=10-2*(10-zhuanScore);
				tempZhuanScore=(tempZhuanScore>0?tempZhuanScore:0);
				nDs.setTurnScore(BigDecimal.valueOf(tempZhuanScore));
			}else if(roleId==3) {
				int tempMaxSpeedScore=10-2*(10-maxSpeedScore);
				tempMaxSpeedScore=(tempMaxSpeedScore>0?tempMaxSpeedScore:0);
				nDs.setMaxSpeedScore(BigDecimal.valueOf(tempMaxSpeedScore));
			}else if(roleId==4) {
				int tempTiredScore=10-2*(10-triedScore);
				tempTiredScore=(tempTiredScore>0?tempTiredScore:0);
				nDs.setTiredDrivingScore(BigDecimal.valueOf(tempTiredScore));
			}else if(roleId==5) {
				int tempNightScore=10-2*(10-nightScore);
				tempNightScore=(tempNightScore>0?tempNightScore:0);
				nDs.setNightDrivingScore(BigDecimal.valueOf(tempNightScore));
			}else if(roleId==6) {
				int tempChaoScore=10-2*(10-chaoScore);
				tempChaoScore=(tempChaoScore>0?tempChaoScore:0);
				nDs.setOverSpeedScore(BigDecimal.valueOf(tempChaoScore));				
			}else if(roleId==7) {
				
			}
			nDs.setRole(role.getRole());
			nDs.setFamilyId(role.getFamilyId());
			nDs.setUserFamilyRoleLogId(role.getId());
			driveScoreMapper.insertScore(nDs);
			driveScoreList.add(nDs);
		}
		if(roles==null || roles.size()==0) {
			ds.setFamilyId(0L);
			ds.setRole(0);
			ds.setUserFamilyRoleLogId(0L);
		    driveScoreMapper.insertScore(ds);
		}
		
		//更新travel
		userTravelHistoryMapper.updateTravelHistory(userTravelHistory);
		//更新道具
		consumeLottery(loteryBeans, userId);
		
		return driveScoreList;
	}
	
	
	/**
	 * 
	    * @Title: calScoreByUH
	    * @Description: TODO(计算行程业务分)
	    * @param @param userId
	    * @param @param habitId
	    * @param @return    参数
	    * @return List<DriveScore>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Map<String,Object> calScoreByUH(Long userId,Long habitId){
		Map<String,Object> rtMap=new HashMap<String,Object>();
		List<DriveScore> driveScoreList=new ArrayList<DriveScore>();
		List<LotteryBean> loteryBeans=new ArrayList<LotteryBean>();
		DriveScore ds=new DriveScore();
		ds.setUserId(userId);
		ds.setHabitId(habitId);
		UserTravelHistory userTravelHistory=new UserTravelHistory();
		userTravelHistory.setUserId(userId);
		userTravelHistory.setHabitId(habitId);
		userTravelHistory.setSpeedTimesOffset(0);
		int phoneUserScore=10;
		//获取行程main，获取行程event
		DriveDataMain main=new DriveDataMain();
		main.setUserId(userId);
		main.setHabitId(habitId);
		DriveDataMain dm=driveDataMainMapper.getDriveDataMainByUH(main);
		String driveEndTime=dm.getDriveEndTime().substring(0, 19);
		//通过driveEndTime 获取用户的角色与familyId
		List<UserFamilyRoleLog> roles=this.getRolesByUserIdTime(userId, driveEndTime);
		//通过userId,获取装配的道具
		List<LotteryBean> lotterys=queryReadyLottery(userId);
		int shachepian=0;//急刹
		int cheluntai=0;//急转
		int zengyaqi=0;//超速点
		int fadongji=0;//高速点
		int hongniu=0;//疲劳
		int yeshijing=0;//夜间驾驶
		
		for(LotteryBean lottery:lotterys) {
			if(lottery.getAwardId()==1) {
				shachepian=lottery.getAwardCount();
			}else if(lottery.getAwardId()==2) {
				cheluntai=lottery.getAwardCount();
			}else if(lottery.getAwardId()==FADONGJI) {
				fadongji=lottery.getAwardCount();
			}else if(lottery.getAwardId()==4) {
				hongniu=lottery.getAwardCount();
			}else if(lottery.getAwardId()==5) {
				yeshijing=lottery.getAwardCount();
			}else if(lottery.getAwardId()==6) {
				zengyaqi=lottery.getAwardCount();
			}
			
		}
		int maxSpeedScore=calMaxSpeedScore(userTravelHistory, loteryBeans, fadongji, dm);
		int distanceScore=calDistanceScore(userTravelHistory,dm);
		
		TiredCalBean tiredCalBean=new TiredCalBean();
		int nightScore=calNightScore(userTravelHistory, dm,loteryBeans, yeshijing,tiredCalBean);
		int triedScore=calTriedScore(userTravelHistory, dm, loteryBeans,hongniu, tiredCalBean);
		FourAlarmBean fourAlarmBean=new FourAlarmBean();
		
		calFourAlarm(userTravelHistory,dm,fourAlarmBean,loteryBeans,shachepian,cheluntai,zengyaqi);
		int jiaScore=fourAlarmBean.getJiaScore();int jianScore=fourAlarmBean.getJianScore();
		int zhuanScore=fourAlarmBean.getZhuanScore();int chaoScore=fourAlarmBean.getChaoScore();
//		1	老司机	急刹车	急刹车惩罚翻倍	刹车片	10
//		2	漂移手	急转弯	急加速、急转弯惩罚翻倍	车轮胎	10
//		3	渣土车手机	最高时速	最高时速惩罚翻倍	发动机	10
//		4	点头司机	疲劳驾驶	疲劳驾驶惩罚翻倍	红牛	10
//		5	红眼司机	夜间驾驶	夜间驾驶惩罚翻倍	夜视镜	10
//		6	飙车党	超速	超速惩罚翻倍	增压器	10
//		7	煎饼侠	无（不开车）	无需开车，可以抢车位、贴条	小马扎	5
		ds.setBrakeScore(BigDecimal.valueOf(jianScore));
		ds.setDistanceScore(BigDecimal.valueOf(distanceScore));
		ds.setMaxSpeedScore(BigDecimal.valueOf(maxSpeedScore));
		ds.setNightDrivingScore(BigDecimal.valueOf(nightScore));
		ds.setOverSpeedScore(BigDecimal.valueOf(chaoScore));
		ds.setPhonePlayScore(BigDecimal.valueOf(phoneUserScore));
		ds.setSpeedUpScore(BigDecimal.valueOf(jiaScore));
		ds.setTiredDrivingScore(BigDecimal.valueOf(triedScore));
		ds.setTurnScore(BigDecimal.valueOf(zhuanScore));
		for(UserFamilyRoleLog role:roles) {
			DriveScore nDs=(DriveScore) ds.clone();
			int roleId=role.getRole();
			if(roleId==1) {
				int tempJianScore=10-2*(10-jianScore);
				tempJianScore=(tempJianScore>0?tempJianScore:0);
				nDs.setBrakeScore(BigDecimal.valueOf(tempJianScore));
			}else if(roleId==2) {
				int tempZhuanScore=10-2*(10-zhuanScore);
				tempZhuanScore=(tempZhuanScore>0?tempZhuanScore:0);
				nDs.setTurnScore(BigDecimal.valueOf(tempZhuanScore));
			}else if(roleId==3) {
				int tempMaxSpeedScore=10-2*(10-maxSpeedScore);
				tempMaxSpeedScore=(tempMaxSpeedScore>0?tempMaxSpeedScore:0);
				nDs.setMaxSpeedScore(BigDecimal.valueOf(tempMaxSpeedScore));
			}else if(roleId==4) {
				int tempTiredScore=10-2*(10-triedScore);
				tempTiredScore=(tempTiredScore>0?tempTiredScore:0);
				nDs.setTiredDrivingScore(BigDecimal.valueOf(tempTiredScore));
			}else if(roleId==5) {
				int tempNightScore=10-2*(10-nightScore);
				tempNightScore=(tempNightScore>0?tempNightScore:0);
				nDs.setNightDrivingScore(BigDecimal.valueOf(tempNightScore));
			}else if(roleId==6) {
				int tempChaoScore=10-2*(10-chaoScore);
				tempChaoScore=(tempChaoScore>0?tempChaoScore:0);
				nDs.setOverSpeedScore(BigDecimal.valueOf(tempChaoScore));				
			}else if(roleId==7) {
				
			}
			nDs.setRole(role.getRole());
			nDs.setFamilyId(role.getFamilyId());
			driveScoreList.add(nDs);
		}
		if(roles==null || roles.size()==0) {
			ds.setFamilyId(0L);
			ds.setRole(0);
			driveScoreList.add(ds);
		}
		
		rtMap.put("scoreList", driveScoreList);
		rtMap.put("lottorys", loteryBeans);
		rtMap.put("userTravelHistory", userTravelHistory);
		
		return rtMap;
	}
	
	
	
	//比较
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
	
 //任务执行
//	void lockCalScoreTask(CalDriveTask driveScore);
	
	public List<CalDriveTask> getCalScoreTask(CalDriveTask driveScore){
		//先锁定任务
		calDriveTaskMapper.lockCalScoreTask(driveScore);
		//返回任务列表
		return calDriveTaskMapper.getCalScoreTask(driveScore);
	}
	
	public	void updateSuccCalScoreTask(CalDriveTask driveScore) {
		calDriveTaskMapper.updateSuccCalScoreTask(driveScore);
	}
//	
	public void updateFailCalScoreTask(CalDriveTask driveScore) {
		calDriveTaskMapper.updateFailCalScoreTask(driveScore);
	}
//	
	@Transactional(value="colTransactionManager")
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		calDriveTaskMapper.clearLockTask(compareTimes);
	}
//	
	
	
	public static void main(String []args)
	{
		System.out.println("2017-11-24 10:47:32.336".substring(0,19));
	}
}
