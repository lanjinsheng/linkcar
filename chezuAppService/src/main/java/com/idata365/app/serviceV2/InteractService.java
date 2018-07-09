package com.idata365.app.serviceV2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.InteractConstant;
import com.idata365.app.entity.InteractLogs;
import com.idata365.app.entity.InteractPeccancy;
import com.idata365.app.entity.InteractTempCar;
import com.idata365.app.entity.TaskPowerLogs;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.mapper.InteractLogsMapper;
import com.idata365.app.mapper.InteractPeccancyMapper;
import com.idata365.app.mapper.InteractTempCarMapper;
import com.idata365.app.mapper.TaskPowerLogsMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.BaseService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.RandUtils;
import com.idata365.app.util.SignUtils;

/**
 * 
 * @ClassName: TempPowerRewardService
 * @Description: TODO(资产随机分配彩蛋)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class InteractService extends BaseService<InteractService> {
	private final static Logger LOG = LoggerFactory.getLogger(InteractService.class);
 @Autowired
 InteractTempCarMapper interactTempCarMapper;
 @Autowired 
 TaskPowerLogsMapper taskPowerLogsMapper;
 @Autowired
 UsersAccountMapper usersAccountMapper;
 
 @Autowired 
 InteractPeccancyMapper interactPeccancyMapper;
 @Autowired 
 InteractLogsMapper interactLogsMapper;
 
 @Autowired 
 ChezuAssetService chezuAssetService;
	public InteractService() {

	}
	/**
	 * 清理锁定的小车任务
	 */
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(2*60*1000);
		interactTempCarMapper.clearLockTask(compareTimes);
	}
	
	
	/**
	 * 缴纳罚单
	 * @param userId
	 * @param peccancyId
	 * @return
	 */
	@Transactional
	public boolean payPeccancy(Long userId,Long peccancyId,String nickName){
		InteractPeccancy peccancy=new InteractPeccancy();
		peccancy.setId(peccancyId);
		peccancy.setPayerId(userId);
		peccancy.setEndLong(System.currentTimeMillis());
		int updateNum=interactPeccancyMapper.updatePeccancy(peccancy);
		if(updateNum<0){
			return false;
		}
		
		InteractPeccancy dbPeccancy=interactPeccancyMapper.getPeccancy(peccancyId);
		int type=1;
		if(dbPeccancy.getLawBreakerId().longValue()==userId.longValue()){//自己付
			type=0;
			InteractLogs log=new InteractLogs();
			log.setEventType(InteractConstant.PAY_PECCANCY_SELF);
			log.setSomeValue(dbPeccancy.getPowerNum());
			log.setUserIdA(userId);
			log.setUserIdB(dbPeccancy.getLawManId());
			log.setUserNameA(nickName);
			UsersAccount account=usersAccountMapper.findAccountById(dbPeccancy.getLawManId());
			log.setUserNameB(account.getNickName());
	    	interactLogsMapper.insertLog(log);
		}else{
			type=1;
			InteractLogs log=new InteractLogs();
			log.setEventType(InteractConstant.PAY_PECCANCY_OTHER);
			log.setSomeValue(dbPeccancy.getPowerNum());
			log.setUserIdA(userId);
			log.setUserIdB(dbPeccancy.getLawManId());
			log.setUserNameA(nickName);
			UsersAccount account=usersAccountMapper.findAccountById(dbPeccancy.getLawManId());
			log.setUserNameB(account.getNickName());
	    	interactLogsMapper.insertLog(log);
	    	
	    	
			InteractLogs log2=new InteractLogs();
			log2.setEventType(InteractConstant.PAY_PECCANCY_OTHER_HELP);
			log2.setSomeValue(dbPeccancy.getPowerNum());
			log2.setUserIdA(userId);
			log2.setUserIdB(dbPeccancy.getLawBreakerId());
			log2.setUserNameA(nickName);
			UsersAccount account2=usersAccountMapper.findAccountById(dbPeccancy.getLawBreakerId());
			log.setUserNameB(account2.getNickName());
	    	interactLogsMapper.insertLog(log2);
	    	
	    	
	    	
	    	
		}
		Map<String, String> map=chezuAssetService.reducePowersByPeccancy(dbPeccancy.getLawManId(), userId, type,
				dbPeccancy.getPowerNum(), peccancyId, SignUtils.encryptHMAC(String.valueOf(dbPeccancy.getLawManId())));
		if(map==null || map.get("flag").equals("0")){
			throw new RuntimeException("无法进行缴纳支付");
		}
		return true;
	}
	
	
	/**
	 * 罚单查询
	 */
	  final String remark1="玩家【%s】给您的爱车贴了一张条";
	  final String remark2="他的爱车被贴了一张条，您是否要帮他缴纳罚金";
	@Transactional
	public List<Map<String,Object>> getPeccancyList(Long userId,int  type){
		 Map<String,Object> pamMap=new HashMap<String,Object>();
		 pamMap.put("lawBreakerId", userId);
		 pamMap.put("endLong", System.currentTimeMillis());
		 List<Map<String,Object>> list=interactPeccancyMapper.getPeccancyList(pamMap);
		 if(list==null || list.size()==0){
			 return new ArrayList<Map<String,Object>>();
		 }
		 if(type==0){//自己的条子
			 for(Map<String,Object> m:list){
				 m.put("remark",String.format(remark1, m.get("nickName")));
			 }
		 }else{//别人的条子
			 for(Map<String,Object> m:list){
				 m.put("remark",remark2);
			 }
		 }
		 return list;
	}
	
	
	@Transactional
	public Map<String,Object> stealFromGarage(Long userId,Long  carUserId,String nickName){
		Map<String,Object> map=new HashMap<String,Object>();
         InteractTempCar interactTempCar=new InteractTempCar();
         interactTempCar.setUserId(carUserId);
         interactTempCar.setDaystamp(DateTools.getYYYYMMDD());
		 List<InteractTempCar> list=interactTempCarMapper.getTempCarByUserId(interactTempCar);
		 String uuid=userId+UUID.randomUUID().toString().replace("-", "");
		 InteractTempCar stealCar=null;
		 for(InteractTempCar car:list){
			 if(car.getBlackIds()!=null && car.getBlackIds().contains(String.valueOf(userId)+",")){
				 continue;
			 }else{
				 car.setLockBatchId(uuid);
				 car.setLockTime(System.currentTimeMillis());
				 car.setStealerId(userId);
				 int i=interactTempCarMapper.lockCarById(car);
				 if(i==1){
					 stealCar=car;
					break; 
				 }
			 }
		 }
		 if(stealCar==null){
			 map.put("powerNum", "0");
			 return map;
		 }
		//动力的处理
			TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
	    	taskPowerLogs.setUserId(carUserId);
	    	taskPowerLogs.setTaskType(PowerEnum.Steal);
	    	int power=stealCar.getPowerNum().intValue();
	    	//type =1  行程动力
	    	taskPowerLogs.setJsonValue(String.format(jsonValue, carUserId,userId,1,power,stealCar.getId()));
	    	int hadAdd=taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
	    	if(hadAdd>0) {
	    		map.put("powerNum", String.valueOf(power));
		    	InteractLogs log=new InteractLogs();
				log.setEventType(InteractConstant.STEAL_POWER);
				log.setSomeValue(power);
				log.setUserIdA(userId);
				log.setUserIdB(carUserId);
				log.setUserNameA(nickName);
				UsersAccount account=usersAccountMapper.findAccountById(carUserId);
				log.setUserNameB(account.getNickName());
		    	interactLogsMapper.insertLog(log);
	    		
	    	}
		 return map;
	}
	private  Map<String,Object>  randReward(long userId,int type,String daystamp){
 
		Map<String,Object> m=new HashMap<String,Object>();
		m.put("carId", String.valueOf(1));
		m.put("power", "0");
		m.put("carType", "1");
		m.put("userId", userId);
		m.put("daystamp", daystamp);
		m.put("uuid",userId+UUID.randomUUID().toString().replaceAll("-", ""));
		m.put("type", type);
			if(type==0){//系统可偷取
				m.put("name", "系统车辆");
				//获取动力
				int r=RandUtils.generateRand(1, 100);
				if(r<=85) {
					m.put("power", String.valueOf(1l));
				}else if(r>85 && r<95) {
					m.put("power", String.valueOf(2l));
				}else {
					m.put("power", String.valueOf(3l));
				}
			}else {//空车
				m.put("name", "不可操作车辆");
			}
			
		return m;
	}
	
 
	/**
	 * 
	    * @Title: getTempPowerReward
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @return    参数
	    * @return List<Map<String,Object>>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public List<Map<String,Object>> getTempPowerReward(long userId) {
//		name :车主
//		type :  -1不可操作车 0:系统车 1:动力车 2:贴条车
		String uuid=UUID.randomUUID().toString().replace("-","");
		long time=System.currentTimeMillis();
		InteractTempCar interactTempCar=new InteractTempCar();
		interactTempCar.setLockBatchId(uuid);
		interactTempCar.setStealerId(userId);
		interactTempCar.setLockTime(System.currentTimeMillis());
		interactTempCar.setDaystamp(DateTools.getYYYYMMDD());
		int lock=interactTempCarMapper.lockCar(interactTempCar);
		List<Map<String,Object>> rtList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> insertCars=new ArrayList<Map<String,Object>>();
		
		List<InteractTempCar> cars=interactTempCarMapper.getTempCar(uuid);
		for(InteractTempCar car:cars){
			if(car.getBlackIds()!=null && car.getBlackIds().contains(String.valueOf(userId)+",")){
				
			}else{
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("carId", String.valueOf(car.getCarId()));
				m.put("power", String.valueOf(car.getPowerNum()));
				m.put("carType", String.valueOf(car.getCarType()));
				m.put("uuid", car.getUuid());
				m.put("type", car.getType());
				m.put("name", car.getDriver());
				m.put("userId", car.getUserId());
				m.put("daystamp", car.getDaystamp());
				rtList.add(m);
			}
		}
		String daystamp=DateTools.getYYYYMMDD();
		int sysCar=5-rtList.size();
		int noRewardCar=5;
		for(int i=0;i<sysCar;i++){
			Map<String,Object> car=randReward(userId,0,daystamp);
			insertCars.add(car);
		}
		rtList.addAll(insertCars);
		//插入insertCars
		interactTempCarMapper.batchInsertCar(insertCars);
		//装饰车辆
		for(int i=0;i<noRewardCar;i++){
			Map<String,Object> car=randReward(userId,-1,daystamp);
			rtList.add(car);
		}
		return rtList;
	}
	@Transactional
	public int insertInteractLogs(InteractLogs log){
		return interactLogsMapper.insertLog(log);
	}
	
	public int hadComeOn(Map<String,Object> map){
		return interactLogsMapper.getHadComeOn(map);
	}
	
	@Transactional
	public List<Map<String,Object>> getInteractLogs(int tabType,Long userId,long maxId){
		List<Map<String,Object>> rtList=new ArrayList<Map<String,Object>>();
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", userId);
		map.put("id", maxId);
		
		if(tabType==1){//自己被互动的记录
			List<InteractLogs> logs=interactLogsMapper.getUserBByUserId(map);
			for(InteractLogs log:logs){
			
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("id", log.getId());
				long time=System.currentTimeMillis()-log.getCreateTime().getTime();
				long rtTime=time/60000;
				if(rtTime<60){
					m.put("time", rtTime+"分钟前");
				}
				else if(rtTime>59 && rtTime<1440){
					rtTime=rtTime/60;
					m.put("time", rtTime+"小时前");
				}else{
					rtTime=rtTime/(60*24);
					m.put("time", rtTime+"天前");
				}
				m.put("createTime", log.getCreateTimeStr());
				
//				1、其他玩家偷取我的动力：
//				最后的晚餐 偷取您的动力 3点
//				2、其他玩家对我贴条：
//				最后的晚餐 给您的车贴了条
//				3、其他玩家帮我缴罚金：
//				最后的晚餐 帮您缴了罚金 20点动力
//				4、其他玩家对我点赞：
//				最后的晚餐给您点了赞
//				5、我贴条的用户向我缴纳罚金：
//				最后的晚餐 给您缴了罚金 20点动力
				int type=log.getEventType();
				if(type==InteractConstant.STEAL_POWER){
					m.put("log", log.getUserNameA()+" 偷取您的动力"+log.getSomeValue()+"点");
				}else if(type==InteractConstant.CLICK_COMEON){
					m.put("log", log.getUserNameA()+" 给您点了赞");
				}else if(type==InteractConstant.STICK_PECCANCY){
					m.put("log", log.getUserNameA()+" 给您的车贴了条");
				}else if(type==InteractConstant.PAY_PECCANCY_SELF || type==InteractConstant.PAY_PECCANCY_OTHER){
					m.put("log", log.getUserNameA()+" 给您缴了罚金"+log.getSomeValue()+"点动力");
				}else if(type==InteractConstant.PAY_PECCANCY_OTHER_HELP){
					m.put("log", log.getUserNameA()+" 帮您交了罚金"+log.getSomeValue()+"点动力");
				}
				rtList.add(m);
			}
		}else if(tabType==2){//自己搞别人的记录
			List<InteractLogs> logs=interactLogsMapper.getUserAByUserId(map);
			for(InteractLogs log:logs){
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("id", log.getId());
				long time=System.currentTimeMillis()-log.getCreateTime().getTime();
				long rtTime=time/60000;
				if(rtTime<60){
					m.put("time", rtTime+"分钟前");
				}
				else if(rtTime>59 && rtTime<1440){
					rtTime=rtTime/60;
					m.put("time", rtTime+"小时前");
				}else{
					rtTime=rtTime/(60*24);
					m.put("time", rtTime+"天前");
				}
				m.put("createTime", log.getCreateTimeStr());
				
//				1、我偷取别人的动力:
//					偷取了 最后的晚餐 20点动力
//					2、我给其他玩家贴了条
//					给 最后的晚餐 贴了罚单
//					3、我帮其他玩家缴罚单
//					帮 最后的晚餐 缴了罚金 20点动力
//					4、我给其他玩家点赞
//					我给 最后的晚餐 点了赞
//					5、我给自己缴罚单
//					我向 最后的晚餐缴了罚金 20点动力
				int type=log.getEventType();
				if(type==InteractConstant.STEAL_POWER){
					m.put("log", "偷取了 "+log.getUserNameB()+" "+log.getSomeValue()+"点动力");
				}else if(type==InteractConstant.CLICK_COMEON){
					m.put("log","给 "+log.getUserNameB()+" 点了赞");
				}else if(type==InteractConstant.STICK_PECCANCY){
					m.put("log", "给 "+log.getUserIdB()+" 贴了罚单");
				}else if(type==InteractConstant.PAY_PECCANCY_SELF || type==InteractConstant.PAY_PECCANCY_OTHER){
					m.put("log", "向 "+log.getUserNameB()+"缴了罚金"+log.getSomeValue()+"点动力");
				}else if(type==InteractConstant.PAY_PECCANCY_OTHER_HELP){
					//有问题
					m.put("log", "帮 "+log.getUserNameB()+"缴了罚金"+log.getSomeValue()+"点动力");
				}
				rtList.add(m);
			}
			
		}else if(tabType==3){//偷窥他人被搞记录
			List<InteractLogs> logs=interactLogsMapper.getUserBByUserId(map);
			for(InteractLogs log:logs){
				Map<String,Object> m=new HashMap<String,Object>();
				m.put("id", log.getId());
				long time=System.currentTimeMillis()-log.getCreateTime().getTime();
				long rtTime=time/60000;
				if(rtTime<60){
					m.put("time", rtTime+"分钟前");
				}
				else if(rtTime>59 && rtTime<1440){
					rtTime=rtTime/60;
					m.put("time", rtTime+"小时前");
				}else{
					rtTime=rtTime/(60*24);
					m.put("time", rtTime+"天前");
				}
				m.put("createTime", log.getCreateTimeStr());
				
//				1、其他玩家偷取我的动力：
//				最后的晚餐 偷取TA的动力 3点
//				2、其他玩家对我贴条：
//				最后的晚餐 给TA的车贴了罚单
//				3、其他玩家帮我缴罚单：
//				最后的晚餐 帮TA缴了罚金 20点动力
//				4、其他玩家对我点赞：
//				最后的晚餐给TA点了赞
//				5、我贴条的用户向我缴纳罚金：
//				最后的晚餐 给TA缴了罚金 20点动力
				int type=log.getEventType();
				if(type==InteractConstant.STEAL_POWER){
					m.put("log", log.getUserNameA() +" 偷取TA的动力 "+log.getSomeValue()+"点");
				}else if(type==InteractConstant.CLICK_COMEON){
					m.put("log",log.getUserNameA() +" 给TA点了赞");
				}else if(type==InteractConstant.STICK_PECCANCY){
					m.put("log", log.getUserNameA() +" 给TA的车贴了罚单");
				}else if(type==InteractConstant.PAY_PECCANCY_SELF || type==InteractConstant.PAY_PECCANCY_OTHER){
					m.put("log", log.getUserNameA()+"向TA缴了罚金"+log.getSomeValue()+"点动力");
				}else if(type==InteractConstant.PAY_PECCANCY_OTHER_HELP){
					//有问题
					m.put("log", log.getUserNameA()+"帮TA缴了罚金"+log.getSomeValue()+"点动力");
				}
				rtList.add(m);
			}
		}
		return rtList;
	}

    String jsonValue="{\"userId\":%d,\"stealerId\":%d,\"type\":%d,\"powerNum\":%d,\"effectId\":%d}";
	@Transactional
	public boolean hadGet(String uuid,long userId,String nickName) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("uuid", uuid);
		//偷取者信息
		map.put("stealerId", userId);
		map.put("blackIds", userId+",");
		int hadGet=interactTempCarMapper.updateTempCar(map);
		if(hadGet>0) {
			//
			InteractTempCar r=interactTempCarMapper.getTempCarByUuid(uuid);
			if(r.getType()>0){
				interactTempCarMapper.updateBlackIds(map);
			}
			if(r.getType()==2){//贴罚单的处理
				InteractPeccancy interactPeccancy=new InteractPeccancy();
				long endLong=System.currentTimeMillis()+(10*3600*1000);
				interactPeccancy.setEndLong(endLong);
				String createTime=DateTools.getCurDate("yyyy-MM-dd HH:mm:ss");
				String endTime=DateTools.getAddMinuteDateTime(createTime, 600);
				interactPeccancy.setEndTimeStr(endTime);
				interactPeccancy.setCreateTimeStr(createTime);
				interactPeccancy.setCarId(r.getCarId());
				interactPeccancy.setPowerNum(r.getPowerNum().intValue());
				interactPeccancy.setLawBreakerId(r.getUserId());//车主
				interactPeccancy.setLawManId(userId);
				interactPeccancyMapper.insertPeccancy(interactPeccancy);
				
		    	InteractLogs log=new InteractLogs();
				log.setEventType(InteractConstant.STICK_PECCANCY);
				log.setSomeValue(1);
				log.setUserIdA(userId);
				log.setUserIdB(r.getUserId());
				log.setUserNameA(nickName);
				UsersAccount account=usersAccountMapper.findAccountById(r.getUserId());
				log.setUserNameB(account.getNickName());
		    	interactLogsMapper.insertLog(log);
				
			}else{
				//动力的处理
				TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
		    	taskPowerLogs.setUserId(userId);
		    	taskPowerLogs.setTaskType(PowerEnum.Steal);
		    	int power=r.getPowerNum().intValue();
		    	taskPowerLogs.setJsonValue(String.format(jsonValue, r.getUserId(),userId,r.getType(),power,r.getId()));
		    	InteractLogs log=new InteractLogs();
				log.setEventType(InteractConstant.STEAL_POWER);
				log.setSomeValue(power);
				log.setUserIdA(userId);
				log.setUserIdB(r.getUserId());
				log.setUserNameA(nickName);
				UsersAccount account=usersAccountMapper.findAccountById(r.getUserId());
				log.setUserNameB(account.getNickName());
		    	interactLogsMapper.insertLog(log);
		    	
		    	int hadAdd=taskPowerLogsMapper.insertTaskPowerLogs(taskPowerLogs);	
		    	if(hadAdd>0) {
		    		return true;
		    	}
			}
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * 查询用户是否能被偷取能量次数
	 * @param userId
	 * @param peccancyId
	 * @return
	 */
	@Transactional
	public int isCanStealPower(Long userId){
		return this.interactTempCarMapper.isCanStealPower(userId);
	}
	
	/**
	 * 查询用户是否能被帮缴罚单次数
	 * @param userId
	 * @param peccancyId
	 * @return
	 */
	@Transactional
	public int isCanPayTicket(Long userId){
		return this.interactPeccancyMapper.isCanPayTicket(userId);
	}
	
	@Transactional
	public int queryLikeCount(Long userId){
		return this.interactLogsMapper.queryLikeCount(userId);
	}
	
	public static void main(String []args) {
		System.out.println(2+UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
