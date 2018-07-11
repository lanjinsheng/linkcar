package com.idata365.app.serviceV2;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.Carpool;
import com.idata365.app.entity.CarpoolApprove;
import com.idata365.app.entity.UserCarLogs;
import com.idata365.app.mapper.CarpoolApproveMapper;
import com.idata365.app.mapper.CarpoolMapper;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.UserCarLogsMapper;
import com.idata365.app.mapper.UserCarMapper;
import com.idata365.app.service.BaseService;
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
	public CarService() {

	}
	/**
	 * 获取家族成员车辆顺风乘坐情况
	 * @param userId
	 */
	public List<Map<String,Object>> getFamilyMemberCarSeats(Long userId,String imgBase){
		List<Map<String,Object>> rtList=new ArrayList<>();
		List<Map<String, Object>> list=familyMapper.getFamilyByUserId(userId);
		String nowTime=DateTools.getYYYYMMDDMMSS();
		for(Map<String,Object> map:list){//家族循环
			Long familyId =Long.valueOf(map.get("familyId").toString());
			List<Map<String,Object>> users=familyMapper.getFamilyUsersMoreInfo(familyId);
			for(Map<String,Object> user:users){//成员循环
				Map<String,Object> rtMap=new HashMap<>();
				Long memberId=Long.valueOf(user.get("userId").toString());
				if(memberId.longValue()==userId.longValue()){
					//自己就算了
					continue;
				}
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
					return rtList;
				}else{
					//查看是否申请在审核中
					Map<String,Object> m=new HashMap<>();
					m.put("passengerId", userId);
					m.put("userCarLogsId", car.get("id"));
					List<CarpoolApprove> approveList=carpoolApproveMapper.getCarpoolApprove(m);
					if(approveList!=null && approveList.size()>0){
						applyStatus=1;
					}
					rtMap.put("tabType", 1);
					rtList.add(rtMap);
				}
			}
		}
		return rtList;
		
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
					rtList.add(rtMap);
		}
		return rtList;
	}
	@Transactional
	public boolean submitCarpoolApprove(Long userId,Long sharingId){
		UserCarLogs car=userCarLogsMapper.getUserCarLogById(sharingId);
		CarpoolApprove approve=new CarpoolApprove();
		if(car==null)
			return false;
		approve.setCarId(car.getCarId());
		approve.setDriverId(car.getUserId());
		approve.setPassengerId(userId);
		approve.setUserCarLogsId(car.getId());
		carpoolApproveMapper.submitCarpoolApprove(approve);
		return true;
	}
	@Transactional
	public boolean applyCarpoolApprove(Long userId,Long approveId){
		
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
		carpool.setId(sharingId);
		int i=carpoolMapper.offCar(carpool);
		return i==1;
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
	
	public static void main(String []args) {
		System.out.println(2+UUID.randomUUID().toString().replaceAll("-", ""));
	}
}
