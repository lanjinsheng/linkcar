package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.entity.FamilyInfoScoreAllBean;
import com.idata365.app.entity.FamilyInfoScoreBean;
import com.idata365.app.entity.FamilyInfoScoreResultBean;
import com.idata365.app.entity.FamilyInviteBean;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyInviteResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandBean;
import com.idata365.app.entity.FamilyRandResultBean;
import com.idata365.app.entity.FamilyRelationBean;
import com.idata365.app.entity.FamilyResultBean;
import com.idata365.app.entity.InviteInfoResultBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.MyFamilyInfoResultBean;
import com.idata365.app.entity.UsersAccountParamBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.RandUtils;

@Service
public class FamilyService extends BaseService<FamilyService>
{
	private static final Logger LOG = LoggerFactory.getLogger(FamilyService.class);
	
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
	public int permitApply(FamilyParamBean bean, UserInfo userInfo)
	{
		Long tempFamilyId = this.familyMapper.queryFamilyIdByUserId(bean);
		if (null != tempFamilyId && tempFamilyId > 0)
		{
			dealtMsg(userInfo, null, bean.getUserId(), MessageEnum.FAIL_FAMILY);
			return 1;
		}
		
		int tempCount = this.familyMapper.countUsersByFamilyId(bean);
		if (8 == tempCount)
		{
			dealtMsg(userInfo, null, bean.getUserId(), MessageEnum.FAIL_FAMILY);
			return 2;
		}
		
		String timeStamp = generateTimeStamp();
		bean.setJoinTime(timeStamp);
		this.familyMapper.saveUserFamily(bean);
		
		dealtMsg(userInfo, null, bean.getUserId(), MessageEnum.PASS_FAMILY);
		
		return 3;
	}
	
	/**
	 * 拒绝用户加入
	 * @param bean
	 * @param userInfo
	 */
	public void rejectApply(FamilyParamBean bean, UserInfo userInfo)
	{
		//删除邀请消息
		this.familyMapper.delInviteByUserId(bean);
		dealtMsg(userInfo, null, bean.getUserId(), MessageEnum.FAIL_FAMILY);
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
	public List<FamilyRandResultBean> listRecruFamily(long userId)
	{
		int countStranger = this.familyMapper.countStranger();
		int startPos = RandUtils.generateRand(0, countStranger-1);
		
		FamilyParamBean bean = new FamilyParamBean();
		bean.setStartPos(startPos);
		List<FamilyRandBean> familys = this.familyMapper.queryFamilys(bean);
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
		this.familyMapper.saveFamilyInvite(bean);
		long inviteId = bean.getId();
		
		FamilyParamBean fParamBean = new FamilyParamBean();
		fParamBean.setFamilyId(bean.getFamilyId());
		long toUserId = this.familyMapper.queryCreateUserId(fParamBean);
		
		dealtMsg(userInfo, inviteId, toUserId, MessageEnum.INVITE_FAMILY);
	}

	private void dealtMsg(UserInfo userInfo, Long inviteId, Long toUserId, MessageEnum messageEnum)
	{
		LOGGER.info("userInfo={}", JSON.toJSONString(userInfo));
		LOGGER.info("inviteId={}\ttoUserId={}", inviteId, toUserId);
		LOGGER.info("messageEnum={}", messageEnum);
		
		//构建成员加入消息
  		Message message=messageService.buildMessage(userInfo.getId(), userInfo.getPhone(), userInfo.getNickName(), toUserId, inviteId, messageEnum);
  		//插入消息
  		messageService.insertMessage(message, messageEnum);
  		//推送消息
  		messageService.pushMessage(message, messageEnum);
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
		String timeStamp = generateTimeStamp();
		bean.setJoinTime(timeStamp);
		this.familyMapper.saveUserFamily(bean);
		
		//更新是否通过邀请码加入状态
		boolean inviteCodeFlag = bean.isInviteCodeFlag();
		UsersAccountParamBean usersAccountParamBean = new UsersAccountParamBean();
		usersAccountParamBean.setUserId(bean.getUserId());
		if (inviteCodeFlag)
		{
			//仅通过邀请码加入
			usersAccountParamBean.setEnableStranger(0);
		}
		else
		{
			//可以通过首页推荐加入
			usersAccountParamBean.setEnableStranger(1);
		}
		this.familyMapper.updateUserStraner(usersAccountParamBean);
		
		return familyId;
	}

	private String generateTimeStamp()
	{
		Calendar todayCal = Calendar.getInstance();
		String timeStamp = DateFormatUtils.format(todayCal, DateConstant.SECOND_FORMAT_PATTERN);
		return timeStamp;
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
		
		int tempCount = this.familyMapper.countByCode(bean);
		if (tempCount > 0)
		{
			return generateInviteInfo(bean);
		}
		
		this.familyMapper.updateInviteCode(bean);
		
		FamilyRandBean familyResultBean = this.familyMapper.queryFamilyByCode(bean);
		InviteInfoResultBean resultBean = new InviteInfoResultBean();
		resultBean.setInviteCode(inviteCode);
		resultBean.setOrderNo("100");
		resultBean.setFamilyName(familyResultBean.getName());
		return resultBean;
	}
	
	/**
	 * 查询自己创建的家族
	 * @param reqBean
	 * @return
	 */
	public MyFamilyInfoResultBean findMyFamily(FamilyParamBean reqBean)
	{
		MyFamilyInfoResultBean resultBean = new MyFamilyInfoResultBean();
		
		FamilyResultBean familyResultBean = this.familyMapper.queryFamilyByUserId(reqBean);
		AdBeanUtils.copyOtherPropToStr(resultBean, familyResultBean);
		
		FamilyRelationBean relationBean = new FamilyRelationBean();
		int myFamilyId = familyResultBean.getMyFamilyId();
		relationBean.setFamilyId(myFamilyId);
		FamilyRelationBean relationResultBean = this.familyMapper.queryFamilyIdByCompetitorId(relationBean);
		long familyId1 = relationResultBean.getFamilyId1();
		long familyId2 = relationResultBean.getFamilyId2();
		FamilyParamBean familyParamBean = new FamilyParamBean();
		if (myFamilyId != familyId1)
		{
			familyParamBean.setFamilyId(familyId1);
		}
		else
		{
			familyParamBean.setFamilyId(familyId2);
		}
		FamilyResultBean competitorFamily = this.familyMapper.queryFamilyById(familyParamBean);
		resultBean.setCompetitorId(String.valueOf(competitorFamily.getMyFamilyId()));
		resultBean.setCompetitorName(competitorFamily.getMyFamilyName());
		resultBean.setCompetitorImgUrl(competitorFamily.getCompetitorImgUrl());
		
		return resultBean;
	}
	
	public FamilyInfoScoreAllBean queryFamilyRelationInfo(FamilyParamBean bean)
	{
		FamilyInfoScoreAllBean resultBean = new FamilyInfoScoreAllBean();
		FamilyInfoScoreBean ownFamilyBean = this.familyMapper.queryOwnFamily(bean);
		FamilyInfoScoreBean joinFamilyBean = this.familyMapper.queryJoinFamily(bean);
		
		FamilyInfoScoreResultBean ownResultBean = new FamilyInfoScoreResultBean();
		if (null != ownFamilyBean)
		{
			AdBeanUtils.copyOtherPropToStr(ownResultBean, ownFamilyBean);
		}
		FamilyInfoScoreResultBean joinResultBean = new FamilyInfoScoreResultBean();
		if (null != joinFamilyBean)
		{
			AdBeanUtils.copyOtherPropToStr(joinResultBean, joinFamilyBean);
		}
		
		resultBean.setOrigFamily(ownResultBean);
		resultBean.setJoinFamily(joinResultBean);
		return resultBean;
	}
}
