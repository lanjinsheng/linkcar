package com.idata365.app.service;

import org.springframework.stereotype.Service;

import com.idata365.app.entity.FamilyResultBean;

@Service
public class FamilyService extends BaseService<FamilyService>
{
	public FamilyResultBean findFamily(long userId)
	{
		FamilyResultBean resultBean = new FamilyResultBean();
		resultBean.setMyFamilyId(553);
		resultBean.setMyFamilyName("神车手一族");
		resultBean.setMyFamilyImgUrl("http://cms-bucket.nosdn.127.net/a0876fd3ca0b4ae9a00ffd46f0dec47f20171214074005.jpeg");
		resultBean.setOrderNo(10);
		resultBean.setFamilyType("SILVER");
		resultBean.setUserRole(1);
		resultBean.setPosHoldCounts(10);
		resultBean.setCompetitorId(557);
		resultBean.setCompetitorName("五菱宏光一族");
		resultBean.setCompetitorImgUrl("http://cms-bucket.nosdn.127.net/a0876fd3ca0b4ae9a00ffd46f0dec47f20171214074005.jpeg");
		return resultBean;
	}
}
