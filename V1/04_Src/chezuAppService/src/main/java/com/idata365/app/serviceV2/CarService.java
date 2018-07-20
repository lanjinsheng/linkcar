package com.idata365.app.serviceV2;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.DicCarConstant;
import com.idata365.app.entity.CarListResultBean;
import com.idata365.app.entity.Carpool;
import com.idata365.app.entity.CarpoolApprove;
import com.idata365.app.entity.DicCar;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.UserCar;
import com.idata365.app.entity.UserCarLogs;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.CarpoolApproveMapper;
import com.idata365.app.mapper.CarpoolMapper;
import com.idata365.app.mapper.DicCarMapper;
import com.idata365.app.mapper.DicComponentMapper;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.InteractPeccancyMapper;
import com.idata365.app.mapper.UserCarLogsMapper;
import com.idata365.app.mapper.UserCarMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.MessageService;
import com.idata365.app.util.DateTools;

/**
 * 
 * @ClassName: CarService
 * @Description: TODO(车辆业务)
 * @author LanYeYe
 * @date 2018年4月27日
 *
 */
@Service
public class CarService extends BaseService<CarService> {
	private final static Logger LOG = LoggerFactory.getLogger(CarService.class);
    @Autowired
    UserCarMapper userCarMapper;
    @Autowired
    CarpoolMapper carpoolMapper;
    @Autowired
    CarpoolApproveMapper carpoolApproveMapper;
    @Autowired
    UserCarLogsMapper userCarLogsMapper;
    @Autowired
    FamilyMapper familyMapper;
    @Autowired
    UsersAccountMapper UsersAccountMapper;
    @Autowired
    MessageService messageService;
    @Autowired
    DicCarMapper dicCarMapper;
    @Autowired
    InteractPeccancyMapper interactPeccancyMapper;
    @Autowired
    DicComponentMapper dicComponentMapper;
    
	public CarService() {

	}
	/**
	 * 获取家族成员车辆顺风乘坐情况
	 * @param userId
	 */
	public  Map<String,Object>  getFamilyMemberCarSeats(Long userId,String imgBase){
		 Map<String,Object> dataMap=new HashMap<>();
		List<Map<String,Object>> rtList=new ArrayList<>();
		List<Map<String, Object>> list=familyMapper.getFamilyByUserId(userId);
		String nowTime=DateTools.getYYYYMMDDMMSS();
		int sharingMyPoint=0;
		int i=carpoolApproveMapper.getCarpoolApproveNum(userId);
		if(i>0){
			sharingMyPoint=1;
		}
		dataMap.put("sharingMyPoint", sharingMyPoint);
		Map<Long,Integer> usersKeys=new HashMap<>();
		for(Map<String,Object> map:list){//家族循环
			Long familyId =Long.valueOf(map.get("familyId").toString());
			List<Map<String,Object>> users=familyMapper.getFamilyUsersMoreInfo(familyId);
			for(Map<String,Object> user:users){//成员循环
				Map<String,Object> rtMap=new HashMap<>();
				Long memberId=Long.valueOf(user.get("userId").toString());
				if(usersKeys.get(memberId)!=null){
					continue;//重複了
				}
				if(memberId.longValue()==userId.longValue()){
					continue;
				}
				usersKeys.put(memberId, 1);
				//查询车辆信息
				user.put("nowTime", nowTime);
				Map<String,Object> car=userCarLogsMapper.getUserCar(user);
				rtMap.put("sharingId", String.valueOf(car.get("id")));
				rtMap.put("nick", String.valueOf(user.get("nickName")));
				rtMap.put("carName", String.valueOf(car.get("carName")));
				rtMap.put("carImgUrl", String.valueOf(car.get("carUrl")));
				
				
				//查询乘坐信息
				List<Map<String,Object>> passengers=carpoolMapper.getCarPool(Long.valueOf(car.get("id").toString()));
				int sharingStatus=0;
				int applyStatus=0;
				for(Map<String,Object> passenger:passengers){
					if(userId.longValue()==Long.valueOf(passenger.get("userId").toString()).longValue()){
						//自己已搭乘
						sharingStatus=1;
						applyStatus=1;
					}else{
						
					}
					passenger.put("imgUrl", imgBase+passenger.get("imgUrl"));
				}
	
				rtMap.put("sharingStatus", sharingStatus);
				rtMap.put("applyStatus", applyStatus);
				rtMap.put("percentReward", (passengers.size()*10)+"%");
				rtMap.put("sharingLists", passengers);
				rtMap.put("passengerNum", passengers.size());
				rtMap.put("carSeats", car.get("carSeat"));
				if(sharingStatus==1){//自己乘坐了
					//重新构建视图
					rtList.clear();
					rtMap.put("tabType", 2);
					rtList.add(rtMap);
					dataMap.put("carsList", rtList);
					return dataMap;
				}else{
					//查看是否申请在审核中
					Map<String,Object> m=new HashMap<>();
					m.put("passengerId", userId);
					m.put("userCarLogsId", car.get("id"));
					List<CarpoolApprove> approveList=carpoolApproveMapper.getCarpoolApprove(m);
					if(approveList!=null && approveList.size()>0){
						applyStatus=1;
						rtMap.put("applyStatus", applyStatus);
					}
					rtMap.put("tabType", 1);
					rtList.add(rtMap);
				}
			}
		}
		dataMap.put("carsList", rtList);
		return dataMap;
		
	}
	
	
	/**
	 * 获取自己搭乘的顺丰车
	 * @param userId
	 */
	public  Map<String,Object>  getSelfCarpoolTravelBy(Long userId,String userNick,String imgBase){
		 Map<String,Object> dataMap=new HashMap<>();
			List<Map<String,Object>> rtList=new ArrayList<>();
			String nowTime=DateTools.getYYYYMMDDMMSS();
			int sharingMyPoint=0;
			dataMap.put("sharingMyPoint", sharingMyPoint);
             Map<String,Object> user=new HashMap<>();
					Map<String,Object> rtMap=new HashMap<>();
					Map<String,Object> carpool=carpoolMapper.getSelfTravelBy(userId);
					if(carpool==null){
						rtList.add(rtMap);
						dataMap.put("carsList", rtList);
						return dataMap;
					}
					//查询车辆信息
					user.put("userId", carpool.get("driverId"));
					user.put("nowTime", nowTime);
					Map<String,Object> car=userCarLogsMapper.getUserCarHistory(Long.valueOf(carpool.get("userCarLogsId").toString()));
					rtMap.put("sharingId", String.valueOf(car.get("id")));
					rtMap.put("nick", car.get("nickName"));
					rtMap.put("carName", String.valueOf(car.get("carName")));
					rtMap.put("carImgUrl", String.valueOf(car.get("carUrl")));
					
					//查询乘坐信息
					List<Map<String,Object>> passengers=carpoolMapper.getCarPool(Long.valueOf(car.get("id").toString()));
					int sharingStatus=0;
					int applyStatus=0;
					for(Map<String,Object> passenger:passengers){
						if(userId.longValue()==Long.valueOf(passenger.get("userId").toString()).longValue()){
							//自己已搭乘
							sharingStatus=1;
							applyStatus=1;
						}else{
							
						}
						passenger.put("imgUrl", imgBase+passenger.get("imgUrl"));
					}
					rtMap.put("sharingStatus", sharingStatus);
					rtMap.put("applyStatus", applyStatus);
					rtMap.put("percentReward", (passengers.size()*10)+"%");
					rtMap.put("sharingLists", passengers);
					rtMap.put("passengerNum", passengers.size());
					rtMap.put("carSeats", car.get("carSeat"));
						//重新构建视图
						rtMap.put("tabType", 2);
						rtList.add(rtMap);
						dataMap.put("carsList", rtList);
						return dataMap;
		
	}
	
	/**
	 * 获取自身顺风车车辆情况
	 * @param userId
	 */
	public List<Map<String,Object>>  getSelfCarSeats(Long userId,String imgBase,String nick){
		List<Map<String,Object>> rtList=new ArrayList<>();
		String nowTime=DateTools.getYYYYMMDDMMSS();
				Map<String,Object> rtMap=new HashMap<>();
				//查询车辆信息
				Map<String,Object> param=new HashMap<>();
				param.put("nowTime", nowTime);
				param.put("userId", userId);
				Map<String,Object> car=userCarLogsMapper.getUserCar(param);
				rtMap.put("sharingId", String.valueOf(car.get("id")));
				rtMap.put("nick", String.valueOf(nick));
				rtMap.put("carName", String.valueOf(car.get("carName")));
				rtMap.put("carImgUrl", String.valueOf(car.get("carUrl")));
				//查询乘坐信息
				List<Map<String,Object>> passengers=carpoolMapper.getCarPool(Long.valueOf(car.get("id").toString()));
				int sharingStatus=0;
				int applyStatus=0;
				for(Map<String,Object> passenger:passengers){
					passenger.put("imgUrl", imgBase+passenger.get("imgUrl"));
				}
				rtMap.put("tabType", 3);
				rtMap.put("sharingStatus", sharingStatus);
				rtMap.put("applyStatus", applyStatus);
				rtMap.put("percentReward", (passengers.size()*10)+"%");
				rtMap.put("sharingLists", passengers);
				rtMap.put("passengerNum", passengers.size());
				rtMap.put("carSeats", car.get("carSeat"));
				rtList.add(rtMap);
		return rtList;
	}
	
	public List<Map<String,Object>>  getCarpoolApprove(Long userId,String baseUrl){
		List<Map<String,Object>> rtList=new ArrayList<>();
		Map<String,Object> m=new HashMap<>();
		m.put("driverId", userId);
		List<Map<String,Object>> approveList=carpoolApproveMapper.getCarpoolApproveByDriver(m);
		for(Map<String,Object> approve:approveList){
			Map<String,Object> rtMap=new HashMap<>();
					rtMap.put("nick", approve.get("nickName"));
					rtMap.put("imgUrl", baseUrl+approve.get("imgUrl"));
					rtMap.put("approveId", approve.get("id"));
					rtMap.put("userId", approve.get("passengerId"));
					rtList.add(rtMap);
		}
		return rtList;
	}
	@Transactional
	public int  submitCarpoolApprove(Long userId,Long sharingId,String nickName){
		UserCarLogs car=userCarLogsMapper.getUserCarLogById(sharingId);
		CarpoolApprove approve=new CarpoolApprove();
		if(car==null)
			return 2;//无车
		
		//判断自己是否已经是顺风车司机了
//		int isDriver=carpoolMapper.isDriver(userId);
//		if(isDriver>0){
//			return 3;
//		}
		approve.setCarId(car.getCarId());
		approve.setDriverId(car.getUserId());
		approve.setPassengerId(userId);
		approve.setUserCarLogsId(car.getId());
		carpoolApproveMapper.submitCarpoolApprove(approve);
		DicCar dicCar=DicCarConstant.getDicCar(car.getCarId());
		Message msg=messageService.buildCarpoolApplyMessage(null, car.getUserId(), nickName, dicCar.getCarName());
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.CARPOOL_APPLY);
 		//用定时器推送
        messageService.pushMessageNotrans(msg,MessageEnum.CARPOOL_APPLY);
		return 1;
	}
	@Transactional
	public boolean applyCarpoolApprove(Long userId,Long approveId,String nickName){
		
		CarpoolApprove carpoolApprove=carpoolApproveMapper.getCarpoolApproveById(approveId);
		//查看车位还够不够
		
		String nowTime=DateTools.getYYYYMMDDMMSS();
		Map<String,Object> map=new HashMap<>();
				//查询车辆信息
		map.put("userId", userId);
	    map.put("nowTime", nowTime);
		Map<String,Object> car=userCarLogsMapper.getUserCar(map);
		int  carSeat=Integer.valueOf(car.get("carSeat").toString());
        int  passengers=	carpoolMapper.getPassengersNum(carpoolApprove.getUserCarLogsId());
		if(carSeat<=passengers){
			LOG.info("用户过多");
			return false;
		}
		int i=carpoolApproveMapper.applyCarpoolApprove(carpoolApprove);
		carpoolApproveMapper.clearOtherCarpoolApprove(carpoolApprove);
		//添加carpool
		Carpool carpool=new Carpool();
		carpool.setCarId(carpoolApprove.getCarId());
		carpool.setDriverId(carpoolApprove.getDriverId());
		carpool.setPassengerId(carpoolApprove.getPassengerId());
		carpool.setUserCarLogsId(carpoolApprove.getUserCarLogsId());
		carpoolMapper.insertCarpool(carpool);
		Message msg=messageService.buildCarpoolApplyPassMessage(null, carpoolApprove.getPassengerId(), nickName);
    	//插入消息
 		messageService.insertMessage(msg, MessageEnum.CARPOOL_APPLY_PASS);
 		//用定时器推送
        messageService.pushMessageNotrans(msg,MessageEnum.CARPOOL_APPLY_PASS);
		return i==1;
	}
	
	@Transactional
	public boolean rejectCarpoolApprove(Long userId,Long approveId){
		int i=carpoolApproveMapper.rejectCarpoolApprove(approveId);
		//添加carpool
		return i==1;
	}
	@Transactional
	public boolean offCar(Long userId,Long sharingId){
		Carpool carpool=new Carpool();
		carpool.setUserCarLogsId(sharingId);
		carpool.setPassengerId(userId);
		int i=carpoolMapper.offCar(carpool);
		return i==1;
	}
	
	@Transactional
	public List<Map<String,Object>> getCarpoolRecords(Long userId,Long maxId){
		List<Map<String,Object>>  rtList=new ArrayList<>();
		Map<String,Object> paramMap=new HashMap<>();
		paramMap.put("userId", userId);
		paramMap.put("maxId", maxId);
		List<Map<String,Object>> list=carpoolMapper.getCarpoolRecords(paramMap);
		for(Map<String,Object> m:list){
			Map<String,Object> rtMap=new HashMap<>();
			Long driverId=Long.valueOf(m.get("driverId").toString());
			String num=String.valueOf(m.get("num"));
			String remark="";
			String power="0";
			if(driverId.longValue()==userId.longValue()){
				remark=num+"位好友搭车，动力加成+"+(Integer.valueOf(num)*10)+"%";
				power=String.valueOf(m.get("driverPower"));
			}else{
				power=String.valueOf(m.get("passengerPower"));
				String userCarLogsId=String.valueOf(m.get("userCarLogsId"));
				Map<String,Object> car=userCarLogsMapper.getUserCarHistory(Long.valueOf(userCarLogsId));
				remark="搭乘【"+car.get("nickName")+"】"+car.get("carName");
			}
		   String getOffTime=String.valueOf(m.get("getOffTime"));
		   //计算时间
		   long time=System.currentTimeMillis()-DateTools.getDateTimeOfLong(getOffTime);
			long rtTime=time/60000;
			if(rtTime<60){
				rtMap.put("time", rtTime+"分钟前");
			}
			else if(rtTime>59 && rtTime<1440){
				rtTime=rtTime/60;
				rtMap.put("time", rtTime+"小时前");
			}else{
				rtTime=rtTime/(60*24);
				rtMap.put("time", rtTime+"天前");
			}
			rtMap.put("remark", remark)	;
			rtMap.put("power", power);
			rtMap.put("recordId", m.get("id"));
			rtList.add(rtMap);
		}
		return rtList;
	}
	
	//查询车辆信息
	@Transactional
	public Map<String,Object> getUserCar(Long userId){
		String nowTime=DateTools.getYYYYMMDDMMSS();
		Map<String,Object> map=new HashMap<>();
		map.put("userId", userId);
	    map.put("nowTime", nowTime);
		Map<String,Object> car=userCarLogsMapper.getUserCar(map);
		return car;
	}
	
	/**
	 * 
	    * @Title: initUserCar
	    * @Description: TODO(初始化用户车辆信息)
	    * @param  参数
	    * @return void 返回类型
	    * @throws
	    * @author LiXing
	 */
	public void initUserCar(long userId) {
		this.userCarMapper.initUserCar(userId);
		this.userCarLogsMapper.initUserCar(userId);
	}
	
	/**
	 * 
	 * @Title: queryUsersCar
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param userId
	 * @param @return 参数
	 * @return Map<String,Object> 返回类型
	 * @throws Exception 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws
	 * @author LiXing
	 */
	public Map<String, Object> queryUsersCar(long userId) throws Exception {
		Map<String, Object> rtMap = new HashMap<>();
		List<CarListResultBean> CarListResultBeanList = new ArrayList<>();
		List<DicCar> carList = this.dicCarMapper.getDicCar(null);
		List<UserCar> userCarList = this.userCarMapper.getUserCarById(userId);
		Map<String, Object> userCurCar = this.getUserCar(userId);
		int index = 0;
		for (int i = 0; i < carList.size(); i++) {
			CarListResultBean carListResultBean = new CarListResultBean();
			carListResultBean.setIsUnlock("0");
			carListResultBean.setIsDriving("0");
			carListResultBean.setPowerConvertDesc("有效里程=行程评分×实际里程数/100");
			BeanUtils.copyProperties(carListResultBean, carList.get(i));
			for (UserCar userCar : userCarList) {
				Integer carId = carList.get(i).getCarId();
				if (carList.get(i).getCarId() == userCar.getCarId()) {
					carListResultBean.setIsUnlock("1");
					if (carId == Integer.valueOf(userCurCar.get("carId").toString())) {
						carListResultBean.setIsDriving("1");
						index = i;
					}
					
					// 动力加成部分
					Map<String, String> powerUpInfo = this.getPowerUpInfo(userId, carId);
					
					carListResultBean.setPowerUpInfo(powerUpInfo);
				}
			}
			CarListResultBeanList.add(carListResultBean);
		}
		rtMap.put("carList", CarListResultBeanList);
		rtMap.put("index", index);
		return rtMap;
	}
	
	// 动力加成部分
	public Map<String, String> getPowerUpInfo(long userId,int carId){
		Map<String, String> powerUpInfo = new HashMap<>();
		// 配件加成
		long partsUpPercent = Math.round(dicComponentMapper.getCurComponentByUserIdCarId(userId, carId));
		powerUpInfo.put("partsUpPercent", "配件加成：" + partsUpPercent * 10 + "%");
		// 搭车加成
		int sitsCount = this.carpoolMapper.querySitsNumById(userId, carId);
		powerUpInfo.put("rideUpPercent", "搭车加成：" + sitsCount * 10 + "%");
		// 贴条减益
		Map<String, Object> pamMap = new HashMap<String, Object>();
		pamMap.put("lawBreakerId", userId);
		pamMap.put("carId", carId);
		pamMap.put("endLong", System.currentTimeMillis());
		int iPCount = interactPeccancyMapper.getUserPeccancyCount(pamMap);
		powerUpInfo.put("ticketDebuffPercent", "贴条减益-：" + iPCount * 10 + "%");
		// 动力加成
		powerUpInfo.put("powerUpPercent", "动力加成：" + (partsUpPercent + sitsCount - iPCount) * 10 + "%");
		return powerUpInfo;
	}
	
	/**
	 * 
        * @Title: changeCar
        * @Description: TODO(换车操作)
        * @param @param userId
        * @param @param carId
        * @param @return 参数
        * @return Map<String,Object> 返回类型
        * @throws
        * @author LiXing
	 */
	public int changeCar(long userId, Integer carId) {
		Map<String, Object> car = this.getUserCar(userId);
		long carLogsId = Long.valueOf(car.get("id").toString());
		//查询当前车辆是否有顺风车乘客
		int passengersNum = this.carpoolMapper.getPassengersNum(carLogsId);
		if(passengersNum > 0) {
			return 0;
		}
		
		Date now = Calendar.getInstance().getTime();
		
		UserCarLogs logs1 = new UserCarLogs();
		logs1.setId(carLogsId);
		logs1.setEndTime(now);
		
		UserCarLogs logs2 = new UserCarLogs();
		logs2.setCarId(carId);
		logs2.setUserId(userId);
		logs2.setStartTime(now);
		//更新旧车
		this.userCarLogsMapper.updateEndTimeById(logs1);
		//插入新车
		this.userCarLogsMapper.insertUserCarLogs(logs2);
		
		return 1;
	}
	
	public static void main(String []args) {
		System.out.println(2+UUID.randomUUID().toString().replaceAll("-", ""));
	}
	
}
