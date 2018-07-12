package com.idata365.app.service;
/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.UserConfig;
import com.idata365.app.mapper.FamilyInviteMapper;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.UserConfigMapper;

@Service
public class FamilyInviteService extends BaseService<FamilyInviteService> {
	private final static Logger LOG = LoggerFactory.getLogger(FamilyInviteService.class);
	@Autowired
	FamilyInviteMapper familyInviteMapper;
	@Autowired
	FamilyMapper familyMapper;
	@Autowired
	UserConfigMapper userConfigMapper;

	public FamilyInviteService() {
	}

	/**
	 * 
	 * @Title: insertInviteFamily
	 * @Description: TODO(网页插入家族邀请表)
	 * @param @param
	 *            familyInvite 参数
	 * @return void 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	public Long insertInviteFamily(FamilyInvite familyInvite) {
		if (familyInvite.getMemberUserId() > 0) {
			familyInviteMapper.insertFamilyInviteHadReg(familyInvite);
		} else {
			familyInviteMapper.insertFamilyInviteNoReg(familyInvite);
		}
		return familyInvite.getId();
	}

	public List<FamilyInvite> getFamilyInviteByPhone(String phone) {
		return familyInviteMapper.getFamilyInviteByPhone(phone);
	}
	
	public int queryIsApplied(long userId, long familyId) {
		int i = this.familyInviteMapper.queryIsApplied(userId, familyId);
		if (i > 0) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public int queryIsCanApply(long familyId) {
		FamilyParamBean bean = new FamilyParamBean();
		bean.setFamilyId(familyId);
		Long userId = this.familyMapper.queryCreateUserId(bean);
		// 获取用户配置
		UserConfig userConfig = userConfigMapper.getUserJoinClubConfigById(userId);
		if (userConfig != null && userConfig.getUserConfigValue() == 0) {
			return 0;
		}
		return 1;
	}
}
