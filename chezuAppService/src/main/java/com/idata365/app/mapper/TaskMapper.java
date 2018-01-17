package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.ParkStationParamBean;

public interface TaskMapper
{
	public List<FamilyRelationBean> queryFamilyRelations(FamilyRelationBean bean);
	
	public int countByFamily(FamilyRelationBean bean);
	
	public void delStations(List<Long> paramList);
	
	public void saveStations(List<ParkStationParamBean> paramList);
}
