package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyRelationParamBean;
import com.idata365.app.entity.GameFamilyParamBean;
import com.idata365.app.entity.StationBean;
import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;

public interface GameMapper
{
	public ViolationStatBean queryFamilyDriveDayStat(ViolationStatParamBean bean);
	
	public List<Long> queryIdleFamily(GameFamilyParamBean bean);
	
	public void saveFamilyRelation(FamilyRelationParamBean bean);
	
	public List<StationBean> queryStations(FamilyRelationParamBean bean);
	
	public int updateToStopParkStation(GameFamilyParamBean bean);
	
	public int updateToHoldParkStation(GameFamilyParamBean bean);
	
	public List<Long> queryFamilyOtherUserId(GameFamilyParamBean bean);
}
