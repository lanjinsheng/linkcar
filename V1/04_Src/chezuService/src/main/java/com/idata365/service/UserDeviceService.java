package com.idata365.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.UserDevice;
import com.idata365.mapper.col.UserDeviceMapper;
import com.idata365.util.DateTools;

 

@Service
public class UserDeviceService extends BaseService<UserDeviceService>{
	private final static Logger LOG = LoggerFactory.getLogger(UserDeviceService.class);
 
	@Autowired
	UserDeviceMapper userDeviceMapper;
 
	
	public UserDeviceService() {
		LOG.info("UserDeviceService init");
	}
	
	@Transactional(value="colTransactionManager")
	public boolean addDeviceUserInfo(String deviceToken,long userId){
		 UserDevice dl=new UserDevice();
		 String date=DateTools.getCurDate();
		 String remark= "{%s 用户:%s 设备号:%s}";
		 remark=String.format(remark, date,String.valueOf(userId),deviceToken);
		 dl.setRemark(remark);
		 dl.setDeviceToken(deviceToken);
		 dl.setUserId(userId);
		 userDeviceMapper.insertUserDevice(dl);
		 return true;
	}
	
}
