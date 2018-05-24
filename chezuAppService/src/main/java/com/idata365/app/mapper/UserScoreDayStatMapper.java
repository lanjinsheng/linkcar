package com.idata365.app.mapper;

import java.util.List;


import com.idata365.app.entity.UserScoreDayStat;

 
public interface  UserScoreDayStatMapper {
	
	List<UserScoreDayStat> getUsersDayScoreByFamily(UserScoreDayStat userScoreDayStat);
	
}
