package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.FamilyInviteBean;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyInviteResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandBean;
import com.idata365.app.entity.FamilyRandResultBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.InviteInfoResultBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.UsersAccountParamBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.util.AdBeanUtils;

@Service
public class FamilyService extends BaseService<FamilyService>
{
	@Autowired
	private FamilyMapper familyMapper;
	
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	
	@Autowired
	private MessageService messageService;
	
	public FamilyResultBean findFamily(long userId)
	{
//		FamilyResultBean resultBean = new FamilyResultBean();
//		resultBean.setMyFamilyId(553);
//		resultBean.setMyFamilyName("神车手一族");
//		resultBean.setMyFamilyImgUrl("http://cms-bucket.nosdn.127.net/a0876fd3ca0b4ae9a00ffd46f0dec47f20171214074005.jpeg");
//		resultBean.setOrderNo(10);
//		resultBean.setFamilyType("SILVER");
//		resultBean.setUserRole(1);
//		resultBean.setPosHoldCounts(10);
//		resultBean.setCompetitorId(557);
//		resultBean.setCompetitorName("五菱宏光一族");
//		resultBean.setCompetitorImgUrl("http://cms-bucket.nosdn.127.net/a0876fd3ca0b4ae9a00ffd46f0dec47f20171214074005.jpeg");
//		return resultBean;
		
		FamilyParamBean paramBean = new FamilyParamBean();
		paramBean.setUserId(userId);
		return this.familyMapper.queryFamilyByUserId(paramBean);
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
	public Map<String,Object> findFamilyByFamilyId(long family)
	{
		Map<String,Object> rtMap=usersAccountMapper.getFamilyByFamilyId(family);
		if(rtMap==null || rtMap.size()==0) {
			return null;
		}
		return rtMap;
	}
	
	/**
	 * 移出家族
	 * @param bean
	 */
	public void removeMember(FamilyParamBean bean)
	{
		this.familyMapper.deleteUserFamilyRelation(bean);
	}
	
	/**
	 * 
	 * @param bean
	 * @return
	 */
	public List<FamilyInviteResultBean> getApplyInfo(FamilyInviteParamBean bean)
	{
		List<FamilyInviteBean> inviteList = this.familyMapper.queryApplyInfo(bean);
		
		List<FamilyInviteResultBean> resultList = new ArrayList<>();
		for (FamilyInviteBean tempBean : inviteList)
		{
			FamilyInviteResultBean tempResultBean = new FamilyInviteResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			resultList.add(tempResultBean);
		}
		return resultList;
	}
	
	/**
	 * 审核成员通过	1：玩家已加入其他家族；2：房间已满；3：审核通过，同意加入；
	 * @param bean
	 * @return
	 */
	@Transactional
	public int permitApply(FamilyParamBean bean)
	{
		Long tempFamilyId = this.familyMapper.queryFamilyIdByUserId(bean);
		if (null != tempFamilyId && tempFamilyId > 0)
		{
			return 1;
		}
		
		int tempCount = this.familyMapper.countUsersByFamilyId(bean);
		if (8 == tempCount)
		{
			return 2;
		}
		
		this.familyMapper.saveUserFamily(bean);
		
		return 3;
	}
	
	/**
	 * 退出家族
	 * @param bean
	 */
	public void quitFromFamily(FamilyParamBean bean)
	{
		this.familyMapper.deleteUserFamilyRelation(bean);
	}
	
	/**
	 * 显示可以加入的家族
	 * @return
	 */
	@Transactional
	public List<FamilyRandResultBean> listRecruFamily(long userId)
	{
		//暂时不用随机算法查询家族
		List<FamilyRandBean> familys = this.familyMapper.queryFamilys();
		List<FamilyRandResultBean> resultList = new ArrayList<>();
		for (FamilyRandBean tempBean : familys)
		{
			FamilyRandResultBean tempResultBean = new FamilyRandResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			
			FamilyParamBean fParamBean = new FamilyParamBean();
			fParamBean.setUserId(userId);
			fParamBean.setFamilyId(tempBean.getFamilyId());
			int inviteCount = this.familyMapper.countInviteByUserId(fParamBean);
			if (inviteCount > 0)
			{
				tempResultBean.setIsAlreadyRecommend("1");
			}
			else
			{
				tempResultBean.setIsAlreadyRecommend("0");
			}
			
			resultList.add(tempResultBean);
		}
		return resultList;
	}
	
	/**
	 * 根据邀请码查询家族
	 * @param bean
	 * @param userId
	 * @return
	 */
	@Transactional
	public FamilyRandResultBean findFamilyByCode(FamilyParamBean bean, long userId)
	{
		FamilyRandBean tempRandBean = this.familyMapper.queryFamilyByCode(bean);
		FamilyRandResultBean tempResultBean = new FamilyRandResultBean();
		AdBeanUtils.copyOtherPropToStr(tempResultBean, tempRandBean);
		
		long familyId = tempRandBean.getFamilyId();
		FamilyParamBean paramBean = new FamilyParamBean();
		paramBean.setUserId(userId);
		paramBean.setFamilyId(familyId);
		int inviteCount = this.familyMapper.countInviteByUserId(paramBean);
		if (inviteCount > 0)
		{
			tempResultBean.setIsAlreadyRecommend("1");
		}
		else
		{
			tempResultBean.setIsAlreadyRecommend("0");
		}
		
		return tempResultBean;
	}
	
	/**
	 * 申请加入指定家族
	 * @param bean
	 */
	@Transactional
	public void applyByFamily(FamilyInviteParamBean bean, UserInfo userInfo)
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
		bean.setCreateTime(dayStr);
		long inviteId = this.familyMapper.saveFamilyInvite(bean);
		
		FamilyParamBean fParamBean = new FamilyParamBean();
		fParamBean.setFamilyId(bean.getFamilyId());
		long toUserId = this.familyMapper.queryCreateUserId(fParamBean);
		
		//构建成员加入消息
  		Message message=messageService.buildMessage(userInfo.getId(), userInfo.getPhone(), userInfo.getNickName(), toUserId, inviteId, MessageEnum.INVITE_FAMILY);
  		//插入消息
  		messageService.insertMessage(message, MessageEnum.INVITE_FAMILY);
  		//推送消息
  		messageService.pushMessage(message,MessageEnum.INVITE_FAMILY);
	}
	
	/**
	 * 检查家族名称
	 * @param bean
	 */
	public String checkFamilyName(FamilyParamBean bean)
	{
		int tempCounts = this.familyMapper.countByName(bean);
		if (tempCounts > 0)
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
	
	/**
	 * 创建家族
	 * @param bean
	 * @return
	 */
	@Transactional
	public long createFamily(FamilyParamBean bean)
	{
		this.familyMapper.save(bean);
		long familyId = bean.getId();
		
		//组长自己绑定新创建的家族
		bean.setFamilyId(familyId);
		this.familyMapper.saveUserFamily(bean);
		
		//更新是否通过邀请码加入状态
		boolean inviteCodeFlag = bean.isInviteCodeFlag();
		UsersAccountParamBean usersAccountParamBean = new UsersAccountParamBean();
		usersAccountParamBean.setUserId(bean.getUserId());
		if (inviteCodeFlag)
		{
			usersAccountParamBean.setEnableStranger(1);
		}
		else
		{
			usersAccountParamBean.setEnableStranger(0);
		}
		this.familyMapper.updateUserStraner(usersAccountParamBean);
		
		return familyId;
	}
	
	/**
	 * 生成邀请码
	 * @param bean
	 * @return
	 */
	@Transactional
	public InviteInfoResultBean generateInviteInfo(FamilyParamBean bean)
	{
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		String inviteCode = StringUtils.substring(uuidStr, 0, 8).toUpperCase();
		bean.setInviteCode(inviteCode);
		this.familyMapper.updateInviteCode(bean);
		
		FamilyRandBean familyResultBean = this.familyMapper.queryFamilyByCode(bean);
		InviteInfoResultBean resultBean = new InviteInfoResultBean();
		resultBean.setInviteCode(inviteCode);
		resultBean.setOrderNo("100");
		resultBean.setFamilyName(familyResultBean.getName());
		return resultBean;
	}
}
