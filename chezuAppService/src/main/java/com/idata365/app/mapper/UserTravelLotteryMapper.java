package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.UserTravelLottery;
/**
 * 
    * @ClassName: UserTravelHistoryMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */
public interface UserTravelLotteryMapper {
 
	 List<UserTravelLottery>  getUserTravelLotterys(UserTravelLottery userTravelLottery);
     int recievedUserTravelLottery(UserTravelLottery userTravelLottery);
}
