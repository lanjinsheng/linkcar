package com.idata365.app.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.mapper.UsersAccountMapper;

@Service
public class FamilyService extends BaseService<FamilyService>
{	
	 @Autowired
	 UsersAccountMapper usersAccountMapper;
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
	
	/**
	 * 
	    * @Title: findFamilyIdByUserId
	    * @Description: TODO(暂时通过usersAccountMapper来处理，等小明接口完整了转移查询mapper)
	    * @param @param userId
	    * @param @return    参数
	    * @return Long    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Map<String,Object> findFamilyIdByUserId(long userId)
	{
		Map<String,Object> rtMap=usersAccountMapper.getFamilyByUserId(userId);
		if(rtMap==null || rtMap.size()==0) {
			return null;
		}
		return rtMap;
	}
}
