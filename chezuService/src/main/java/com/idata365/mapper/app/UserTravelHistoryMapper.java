package com.idata365.mapper.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.idata365.entity.UserTravelHistory;
/**
 * 
    * @ClassName: UserTravelHistoryMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface UserTravelHistoryMapper {
 
	 void  batchInsert(List<UserTravelHistory> list);
	 void  updateTravelHistory(UserTravelHistory userTravelHistory);
	 
	 
		void lockTravelTask(UserTravelHistory userTravelHistory);
		
		List<UserTravelHistory> getTravelTask(UserTravelHistory userTravelHistory);
		
		void updateTravelSuccTask(UserTravelHistory userTravelHistory);
		
		void updateTravelFailTask(UserTravelHistory userTravelHistory);
		
		void clearLockTask(@Param("compareTimes") Long compareTimes);
		

}
