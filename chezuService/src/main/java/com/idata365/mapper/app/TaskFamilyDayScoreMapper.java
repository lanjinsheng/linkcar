package com.idata365.mapper.app;

import java.util.List;
import java.util.Map;

import com.idata365.entity.TaskFamilyDayScore;
import com.idata365.entity.UserTravelLottery;
/**
 * 
    * @ClassName: TaskFamilyDayScoreMapper
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2018年1月8日
    *
 */
public interface TaskFamilyDayScoreMapper {
 
	 int  insertTaskFamilyDayScore(TaskFamilyDayScore taskFamilyDayScore);

	 int  insertTaskFamilyDayScoreByTime(Map<String,Object> map);

}
