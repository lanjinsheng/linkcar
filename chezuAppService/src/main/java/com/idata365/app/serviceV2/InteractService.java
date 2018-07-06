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

import com.idata365.app.entity.InteractPeccancy;
import com.idata365.app.entity.InteractTempCar;
import com.idata365.app.entity.TaskPowerLogs;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.mapper.InteractPeccancyMapper;
import com.idata365.app.mapper.InteractTempCarMapper;
import com.idata365.app.mapper.TaskPowerLogsMapper;
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
 InteractPeccancyMapper interactPeccancyMapper;
 @Autowired 
 ChezuAssetService chezuAssetService;
	public InteractService() {

	}
	/**
	 * 缴纳罚单
	 * @param userId
	 * @param peccancyId
	 * @return
	 */
	@Transactional
	public boolean payPeccancy(Long userId,Long peccancyId){
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
		}else{
			type=1;
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
	public Map<String,Object> stealFromGarage(Long userId,Long  carUserId){
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
	
	

    String jsonValue="{\"userId\":%d,\"stealerId\":%d,\"type\":%d,\"powerNum\":%d,\"effectId\":%d}";
	@Transactional
	public boolean hadGet(String uuid,long userId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("uuid", uuid);
		map.put("stealerId", userId);
		map.put("blackIds", userId+",");
		int hadGet=interactTempCarMapper.updateTempCar(map);
		if(hadGet>0) {
			//
			InteractTempCar r=interactTempCarMapper.getTempCarByUuid(uuid);
			if(r.getType()>0){
				interactTempCarMapper.updateBlackIds(map);
			}
			if(r.getType()==2){//罚单的处理
				InteractPeccancy interactPeccancy=new InteractPeccancy();
				long endLong=System.currentTimeMillis()+(10*3600*1000);
				interactPeccancy.setEndLong(endLong);
				String createTime=DateTools.getCurDate("yyyy-MM-dd HH:mm:ss");
				String endTime=DateTools.getAddMinuteDateTime(createTime, 600);
				interactPeccancy.setEndTimeStr(endTime);
				interactPeccancy.setCreateTimeStr(createTime);
				interactPeccancy.setCarId(r.getCarId());
				interactPeccancy.setPowerNum(r.getPowerNum().intValue());
				interactPeccancy.setLawBreakerId(r.getUserId());
				interactPeccancy.setLawManId(userId);
				interactPeccancyMapper.insertPeccancy(interactPeccancy);
			}else{
				//动力的处理
				TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
		    	taskPowerLogs.setUserId(userId);
		    	taskPowerLogs.setTaskType(PowerEnum.Steal);
		    	int power=r.getPowerNum().intValue();
		    	taskPowerLogs.setJsonValue(String.format(jsonValue, userId,r.getStealerId(),r.getType(),power,r.getId()));
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
	public static void main(String []args) {
		System.out.println(2+UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
