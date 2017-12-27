package com.idata365.app.mapper;

import com.idata365.app.entity.ViolationStatBean;
import com.idata365.app.entity.ViolationStatParamBean;

public interface GameMapper
{
	public ViolationStatBean queryFamilyDriveDayStat(ViolationStatParamBean bean);
}
