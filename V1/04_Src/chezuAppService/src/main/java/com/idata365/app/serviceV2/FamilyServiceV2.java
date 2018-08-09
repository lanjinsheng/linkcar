package com.idata365.app.serviceV2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.ImNotify;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.service.BaseService;
import com.idata365.app.service.ImService;
import com.idata365.app.service.UserInfoService;

@Service
public class FamilyServiceV2 extends BaseService<FamilyServiceV2> {
	private static final Logger LOG = LoggerFactory.getLogger(FamilyServiceV2.class);

	@Autowired
	private FamilyMapper familyMapper;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private ImService imService;

	/**
	 * 修改俱乐部资料
	 * 
	 * @param bean
	 * @return
	 */
	public int updateFamily(Map<String, Object> entity, Long userId) {
		LOG.info("param=============" + JSON.toJSONString(entity));
		String familyName = entity.get("familyName").toString();
		String familyNotify = entity.get("familyNotify").toString();
		String imgUrl = entity.get("imgUrl").toString();
		FamilyParamBean countNameParam = new FamilyParamBean();
		countNameParam.setName(entity.get("familyName").toString());
		FamilyParamBean bean = new FamilyParamBean();
		bean.setUserId(userId);
		FamilyResultBean familyInfo = this.familyMapper.queryFamilyByUserId(bean);
		int nameCounts = this.familyMapper.countByName(countNameParam);
		if (nameCounts > 0 && !familyName.equals(familyInfo.getMyFamilyName())) {
			return -1;
		}
		long familyId = familyMapper.queryCreateFamilyId(userId);
		int i = this.familyMapper.updateFamilyInfo(familyId, familyName, imgUrl);
		if (i != 1) {
			return -1;
		}
		// 保存公告信息
		UsersAccount account = userInfoService.getUsersAccount(userId);
		ImNotify notify = new ImNotify();
		notify.setFamilyId(familyId);
		notify.setFamilyName(familyName);
		notify.setLeaderName(account.getNickName());
		notify.setLeaderId(account.getId());
		notify.setLeaderPic(account.getImgUrl());
		notify.setInUse(1);
		notify.setNotifyMsg(familyNotify);
		imService.insertNotify(notify);
		return 0;
	}

}
