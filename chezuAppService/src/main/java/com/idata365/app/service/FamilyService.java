package com.idata365.app.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.idata365.app.constant.DateConstant;
import com.idata365.app.constant.FamilyConstant;
import com.idata365.app.constant.RoleConstant;
import com.idata365.app.entity.FamilyHistoryParamBean;
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
import com.idata365.app.entity.FamilyScoreBean;
import com.idata365.app.entity.InviteInfoResultBean;
import com.idata365.app.entity.MainResultBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.MyFamilyInfoResultBean;
import com.idata365.app.entity.ScoreFamilyInfoParamBean;
import com.idata365.app.entity.UserFamilyRoleLogBean;
import com.idata365.app.entity.UserFamilyRoleLogParamBean;
import com.idata365.app.entity.UserRoleLog;
import com.idata365.app.entity.UserScoreDayParamBean;
import com.idata365.app.entity.UsersAccountParamBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.ScoreMapper;
import com.idata365.app.mapper.TaskMapper;
import com.idata365.app.mapper.UserRoleLogMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.util.AdBeanUtils;
import com.idata365.app.util.PhoneUtils;
import com.idata365.app.util.RandUtils;

@Service
public class FamilyService extends BaseService<FamilyService>
{
	private static final Logger LOG = LoggerFactory.getLogger(FamilyService.class);
	
	@Autowired
	private FamilyMapper familyMapper;
	
	@Autowired
	private ScoreMapper scoreMapper;
	
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	UserRoleLogMapper userRoleLogMapper;
	
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
	public Map<String,Object> findLeaderByFamilyId(long family)
	{
		Map<String,Object> rtMap=usersAccountMapper.getLeaderByFamilyId(family);
		if(rtMap==null || rtMap.size()==0) {
			return null;
		}
		return rtMap;
	}
	public int getUsersCount() {
		return this.familyMapper.countUsers();
	}
	
	public List<Map<String,Object>> findFamilyRelation(long family){
		List<Map<String,Object>> rtList=usersAccountMapper.findFamilyRelation(family);
		if(rtList==null || rtList.size()==0) {
			return null;
		}
		return rtList;
	}
	
	
	/**
	 * 移出家族
	 * @param bean
	 */
	@Transactional
	public void removeMember(FamilyParamBean bean,UserInfo user)
	{
		this.familyMapper.deleteUserFamilyRelation(bean);
		
		//更新userFamilyRoleLog中的endTime
		String ts = generateTimeStamp();
		String daystamp = getCurrentDayStrWithUnDelimiter();
		bean.setEndTime(ts);
		bean.setDaystamp(daystamp);
		this.familyMapper.updateFamilyRoleLog(bean);
		//更新家族热度
		familyMapper.removeFamilyMemberNum(bean.getFamilyId());
		familyMapper.updateFamilyActiveLevel(bean.getFamilyId());
		
		//发送消息
		String nickName=user.getNickName();
		if(nickName==null) {
			nickName=user.getPhone();
		}
		FamilyParamBean familyParamBean=new FamilyParamBean();
		familyParamBean.setFamilyId(bean.getFamilyId());
		FamilyResultBean familyResultBean =familyMapper.queryFamilyById(familyParamBean);
		Message msg=messageService.buildKickMemberMessage(user.getId(),bean.getUserId(),nickName,familyResultBean.getMyFamilyName());
		messageService.insertMessage(msg, MessageEnum.Kick_Member);
		messageService.pushMessageNotrans(msg, MessageEnum.Kick_Member);
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
			String name = tempBean.getName();
			if (StringUtils.isBlank(name))
			{
				String tempName = PhoneUtils.hidePhone(tempBean.getPhone());
				tempResultBean.setName(tempName);
			}
			
			long userId = tempBean.getUserId();
			long familyId = tempBean.getFamilyId();
			FamilyParamBean familyParamBean = new FamilyParamBean();
			familyParamBean.setUserId(userId);
			familyParamBean.setFamilyId(familyId);
			
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
		List<Long> familyIdList = this.familyMapper.queryFamilyIdByUserId(bean);
		
		if (CollectionUtils.isNotEmpty(familyIdList)
				&& familyIdList.size() > 1)
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
		
		//记录用户、家族关系
		String timeStamp = generateTimeStamp();
		bean.setJoinTime(timeStamp);
		
		//查找用户当前角色
		UserRoleLog role=userRoleLogMapper.getLatestUserRoleLogByUserId(bean.getUserId());
		
		bean.setRole(role.getRole());
		this.familyMapper.saveUserFamily(bean);
		
		FamilyRelationBean familyRelationParam = new FamilyRelationBean();
		familyRelationParam.setFamilyId(bean.getFamilyId());
		familyRelationParam.setDaystamp(getCurrentDayStr());
//		List<Long> relationIds = this.familyMapper.queryFamilyRelationIds(familyRelationParam);
		
//		if (CollectionUtils.isNotEmpty(relationIds))
//		{
			//初始化用户在新家族的角色到userFamilyRelation
			UserFamilyRoleLogParamBean roleLogParamBean = new UserFamilyRoleLogParamBean();
			roleLogParamBean.setUserId(bean.getUserId());
			roleLogParamBean.setFamilyId(bean.getFamilyId());
			roleLogParamBean.setRole(role.getRole());
			this.taskMapper.updateUserRole(roleLogParamBean);
			
			//初始化用户角色、成绩记录表start------------------
			//记录用户在新家族的角色到userFamilyRoleLog
			UserFamilyRoleLogParamBean userFamilyRoleLogParamBean = new UserFamilyRoleLogParamBean();
			userFamilyRoleLogParamBean.setUserId(bean.getUserId());
			userFamilyRoleLogParamBean.setFamilyId(bean.getFamilyId());
			userFamilyRoleLogParamBean.setDaystamp(getCurrentDayStrWithUnDelimiter());
			userFamilyRoleLogParamBean.setRole(role.getRole());
			userFamilyRoleLogParamBean.setStartTime(timeStamp);
			userFamilyRoleLogParamBean.setEndTime(getCurrentDayStr() + " 23:59:59");
			this.taskMapper.saveUserFamilyRole(userFamilyRoleLogParamBean);
			
			//初始化加入新家族后的userScoreDayStat记录
			UserScoreDayParamBean tempScoreDayParamBean = new UserScoreDayParamBean();
			tempScoreDayParamBean.setUserId(bean.getUserId());
			tempScoreDayParamBean.setUserFamilyScoreId(userFamilyRoleLogParamBean.getId());
			tempScoreDayParamBean.setDaystamp(getCurrentDayStr());
			tempScoreDayParamBean.setFamilyId(bean.getFamilyId());
			this.taskMapper.saveOrUpdateUserScoreDay(tempScoreDayParamBean);
			//初始化用户角色、成绩记录表end------------------
//		}
		
		
		FamilyParamBean familyParamStatusBean = new FamilyParamBean();
		familyParamStatusBean.setMsgId(bean.getMsgId());
		familyParamStatusBean.setStatus(1);
		this.familyMapper.updateInviteStatus(familyParamStatusBean);
		
		dealtMsg(userInfo, null, bean.getUserId(), MessageEnum.PASS_FAMILY);
		//增加用户热度
		familyMapper.addFamilyMemberNum(bean.getFamilyId());
		familyMapper.updateFamilyActiveLevel(bean.getFamilyId());
		
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
//		this.familyMapper.delInviteByUserId(bean);
		
		FamilyParamBean familyParamStatusBean = new FamilyParamBean();
		familyParamStatusBean.setMsgId(bean.getMsgId());
		familyParamStatusBean.setStatus(-1);
		this.familyMapper.updateInviteStatus(familyParamStatusBean);
		
		dealtMsg(userInfo, null, bean.getUserId(), MessageEnum.FAIL_FAMILY);
	}
	
	/**
	 * 退出家族
	 * @param bean
	 */
	@Transactional
	public void quitFromFamily(FamilyParamBean bean)
	{
		this.familyMapper.deleteUserFamilyRelation(bean);
		
		//更新userFamilyRoleLog中的endTime
		String ts = generateTimeStamp();
		String daystamp = getCurrentDayStrWithUnDelimiter();
		bean.setEndTime(ts);
		bean.setDaystamp(daystamp);
		this.familyMapper.updateFamilyRoleLog(bean);
	}
	
	private String getCurrentDayStrWithUnDelimiter()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN);
		return dayStr;
	}
	
	/**
	 * 显示可以加入的家族
	 * @return
	 */
	public List<FamilyRandResultBean> listRecruFamily(long userId)
	{
		FamilyParamBean familyParamBean = new FamilyParamBean();
		familyParamBean.setUserId(userId);
		int countStranger = this.familyMapper.countStranger(familyParamBean);
		List<FamilyRandResultBean> resultList = new ArrayList<>();
		if (0 == countStranger)
		{
			return resultList;
		}
		int startPos = RandUtils.generateRand(0, countStranger-1);
		
		FamilyParamBean bean = new FamilyParamBean();
		bean.setStartPos(startPos);
		bean.setUserId(userId);
		List<FamilyRandBean> familys = this.familyMapper.queryFamilys(bean);
		for (FamilyRandBean tempBean : familys)
		{
			if(tempBean.getFamilyId()==FamilyConstant.ROBOT_FAMILY_ID){
				continue;
			}
			FamilyRandResultBean tempResultBean = new FamilyRandResultBean();
			AdBeanUtils.copyOtherPropToStr(tempResultBean, tempBean);
			
			FamilyParamBean countParamBean = new FamilyParamBean();
			countParamBean.setFamilyId(tempBean.getFamilyId());
			int tempMemNum = this.familyMapper.countUsersByFamilyId(countParamBean);
			tempResultBean.setNum(String.valueOf(tempMemNum));
			
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
		if (null == tempRandBean)
		{
			return null;
		}
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
  		messageService.pushMessageNotrans(message, messageEnum);
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
	public long createFamily(FamilyParamBean bean,long userId)
	{
		int tempCounts = this.familyMapper.countByCreateUser(bean);
		if (tempCounts > 0)
		{
			return -1;
		}
		
		FamilyParamBean countNameParam = new FamilyParamBean();
		countNameParam.setName(bean.getFamilyName());
		int nameCounts = this.familyMapper.countByName(countNameParam);
		if (nameCounts > 0)
		{
			return -2;
		}
		
		bean.setCreateTimeStr(generateTimeStampUndelimiter());
		bean.setFamilyType(FamilyConstant.BRONZE_TYPE);
		this.familyMapper.save(bean);
		
		long familyId = bean.getId();
		
		FamilyHistoryParamBean tempHistoryParamBean = new FamilyHistoryParamBean();
		tempHistoryParamBean.setFamilyId(familyId);
		tempHistoryParamBean.setRecord("家族(" + bean.getFamilyName() + ")成立");
		this.familyMapper.saveFamilyHistory(tempHistoryParamBean);
		
		UserRoleLog role=userRoleLogMapper.getLatestUserRoleLogByUserId(userId);
		//组长自己绑定新创建的家族
		bean.setFamilyId(familyId);
		Date todayDate = Calendar.getInstance().getTime();
		bean.setJoinTime(generateTimeStamp());
		bean.setRole(role.getRole());
		this.familyMapper.saveUserFamily(bean);
		
		Date tomorrowDate = DateUtils.addDays(todayDate, 1);
		String startTime = DateFormatUtils.format(tomorrowDate, DateConstant.DAY_PATTERN_DELIMIT) + " 00:00:00";
		String endTime = DateFormatUtils.format(tomorrowDate, DateConstant.DAY_PATTERN_DELIMIT) + " 23:59:59";
		String curDayStr=getCurrentDayStr();
		//初始化用户角色、成绩记录表start------------------
		
		//记录用户在新家族的合约
		UserFamilyRoleLogParamBean userFamilyRoleLogParamBean0 = new UserFamilyRoleLogParamBean();
		userFamilyRoleLogParamBean0.setUserId(bean.getUserId());
		userFamilyRoleLogParamBean0.setFamilyId(familyId);
		userFamilyRoleLogParamBean0.setDaystamp(getCurrentDateUndelimiterStr());
		userFamilyRoleLogParamBean0.setRole(RoleConstant.Family_NoRole);
		userFamilyRoleLogParamBean0.setStartTime(curDayStr+ " 00:00:00");
		userFamilyRoleLogParamBean0.setEndTime(curDayStr+ " 23:59:59");
		this.taskMapper.saveUserFamilyRole(userFamilyRoleLogParamBean0);
						
		
		
		//记录用户在新家族的明天的角色
//		UserFamilyRoleLogParamBean userFamilyRoleLogParamBean = new UserFamilyRoleLogParamBean();
//		userFamilyRoleLogParamBean.setUserId(bean.getUserId());
//		userFamilyRoleLogParamBean.setFamilyId(familyId);
//		userFamilyRoleLogParamBean.setDaystamp(getTomorrowDateUndelimiterStr());
//		userFamilyRoleLogParamBean.setRole(RoleConstant.JIANBING_ROLE);
//		userFamilyRoleLogParamBean.setStartTime(startTime);
//		userFamilyRoleLogParamBean.setEndTime(endTime);
//		this.taskMapper.saveUserFamilyRole(userFamilyRoleLogParamBean);
		
		//初始化加入新家族后的userScoreDayStat记录
		UserScoreDayParamBean tempScoreDayParamBean = new UserScoreDayParamBean();
		tempScoreDayParamBean.setUserId(bean.getUserId());
		tempScoreDayParamBean.setUserFamilyScoreId(userFamilyRoleLogParamBean0.getId());
		tempScoreDayParamBean.setFamilyId(familyId);
		tempScoreDayParamBean.setDaystamp(getCurrentDayStr());
		this.taskMapper.saveOrUpdateUserScoreDay(tempScoreDayParamBean);
		//初始化用户角色、成绩记录表end------------------
				
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
	
	public String getTomorrowDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		Date tomorrowDate = DateUtils.addDays(curDate, 1);
		
		String tomorrowDateStr = DateFormatUtils.format(tomorrowDate, "yyyyMMdd");
		LOGGER.info(tomorrowDateStr);
		return tomorrowDateStr;
	}
	public String getCurrentDateUndelimiterStr()
	{
		Date curDate = Calendar.getInstance().getTime();
		String curDateStr = DateFormatUtils.format(curDate, "yyyyMMdd");
		LOGGER.info(curDateStr);
		return curDateStr;
	}
	private String generateTimeStampUndelimiter()
	{
		Calendar todayCal = Calendar.getInstance();
		String timeStamp = DateFormatUtils.format(todayCal, DateConstant.SECOND_PATTERN);
		return timeStamp;
	}

	private String generateTimeStamp()
	{
		Calendar todayCal = Calendar.getInstance();
		String timeStamp = DateFormatUtils.format(todayCal, DateConstant.SECOND_FORMAT_PATTERN);
		return timeStamp;
	}
	
	private String generateTimeStamp(Date date)
	{
		String timeStamp = DateFormatUtils.format(date, DateConstant.SECOND_FORMAT_PATTERN);
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
		resultBean.setFamilyName(familyResultBean.getName());
		
		ScoreFamilyInfoParamBean orderParamBean = new ScoreFamilyInfoParamBean();
		orderParamBean.setFamilyId(familyResultBean.getFamilyId());
		List<FamilyScoreBean> orderList = this.scoreMapper.queryOrderRecords(orderParamBean);
		if (CollectionUtils.isNotEmpty(orderList))
		{
			int yesterdayOrderNo = orderList.get(orderList.size()-1).getYesterdayOrderNo();
			resultBean.setOrderNo(String.valueOf(yesterdayOrderNo));
		}
		else
		{
			resultBean.setOrderNo("-1");
		}
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
		relationBean.setDaystamp(getCurrentDayStr());
		FamilyRelationBean relationResultBean = this.familyMapper.queryFamilyIdByCompetitorId(relationBean).get(0);
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
		FamilyInfoScoreBean ownFamilyBean = this.familyMapper.queryOwnFamily(bean);
		
		FamilyInfoScoreResultBean ownResultBean = new FamilyInfoScoreResultBean();
		if (null != ownFamilyBean)
		{
			AdBeanUtils.copyOtherPropToStr(ownResultBean, ownFamilyBean);
			
			FamilyParamBean ownOrderParamBean = new FamilyParamBean();
			ownOrderParamBean.setFamilyId(ownFamilyBean.getFamilyId());
//			ownOrderParamBean.setMonth(getCurrentMonthStr());
			ownOrderParamBean.setTimeStr(getYesterdayStr());
			Integer ownOrderNo = this.familyMapper.queryOwnFamilyOrderNo(ownOrderParamBean);
			if (null != ownOrderNo && 0 != ownOrderNo)
			{
				ownResultBean.setOrderNo(String.valueOf(ownOrderNo));
			}
		}
		
		List<Long> familyIdList = this.familyMapper.queryFamilyIdByUserId(bean);
		Long joinFamilyId = null;
		if (CollectionUtils.isNotEmpty(familyIdList))
		{
			for (Long tempFamilyId : familyIdList)
			{
				if (null == ownFamilyBean)
				{
					joinFamilyId = tempFamilyId;
					break;
				}
				else if (tempFamilyId == ownFamilyBean.getFamilyId())
				{
					continue;
				}
				else
				{
					joinFamilyId = tempFamilyId;
					break;
				}
			}
		}
		
		FamilyInfoScoreBean joinFamilyBean = null;
		FamilyInfoScoreResultBean joinResultBean = new FamilyInfoScoreResultBean();
		if (joinFamilyId != null)
		{
			FamilyParamBean joinParamBean = new FamilyParamBean();
			joinParamBean.setFamilyId(joinFamilyId);
			joinParamBean.setUserId(bean.getUserId());
			joinFamilyBean = this.familyMapper.queryJoinFamily(joinParamBean);
			
		}
		if (null != joinFamilyBean)
		{
			AdBeanUtils.copyOtherPropToStr(joinResultBean, joinFamilyBean);
			
			FamilyParamBean joinFamilyParamBean = new FamilyParamBean();
			joinFamilyParamBean.setFamilyId(joinFamilyBean.getFamilyId());
//			joinFamilyParamBean.setMonth(getCurrentMonthStr());
			joinFamilyParamBean.setTimeStr(getYesterdayStr());
			Integer joinOrderNo = this.familyMapper.queryOwnFamilyOrderNo(joinFamilyParamBean);
//			Integer joinOrderNo = this.familyMapper.queryJoinFamilyOrderNo(joinFamilyParamBean);
			if (null != joinOrderNo)
			{
				joinResultBean.setOrderNo(String.valueOf(joinOrderNo));
			}
		}
		
		FamilyInfoScoreAllBean resultBean = new FamilyInfoScoreAllBean();
		if (StringUtils.isNotBlank(ownResultBean.getFamilyId()))
		{
			resultBean.setOrigFamily(ownResultBean);
		}
		if (StringUtils.isNotBlank(joinResultBean.getFamilyId()))
		{
			resultBean.setJoinFamily(joinResultBean);
		}
		
		return resultBean;
	}
	
	private String getCurrentMonthStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.MONTH_PATTERN);
		return dayStr;
	}
	
	private String getCurrentDayStr()
	{
		Calendar cal = Calendar.getInstance();
		String dayStr = DateFormatUtils.format(cal, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
	
	private String getYesterdayStr()
	{
		Calendar cal = Calendar.getInstance();
		Date yesterdayDate = DateUtils.addDays(cal.getTime(), -1);
		String dayStr = DateFormatUtils.format(yesterdayDate, DateConstant.DAY_PATTERN_DELIMIT);
		return dayStr;
	}
	
	public List<Map<String,Object>> familyRelationByFamilyId(Long familyId){
		return familyMapper.getFamilyUsers(familyId);
	}
	
	/**
	 * 主页统计字段接口
	 * @param bean
	 * @return
	 */
	public MainResultBean queryMainNum(FamilyParamBean bean)
	{
		MainResultBean resultBean = new MainResultBean();
		
		int countUsers = this.familyMapper.countUsers();
		resultBean.setGamerNum(countUsers);
		
		//统计未读消息
		int countUnRead = this.familyMapper.countUnRead(bean);
		resultBean.setNewsNum(countUnRead);
		
		//统计聊天未读消息
		int countUnReadChats = this.familyMapper.countUnReadChats(bean);
		resultBean.setChatsNum(countUnReadChats);
		
		int taskFlag = this.familyMapper.queryTaskFlag(bean);
		
		String yesterdayStr = getYesterdayStr();
		
		UserFamilyRoleLogParamBean userFamilyRoleLogParamBean = new UserFamilyRoleLogParamBean();
		userFamilyRoleLogParamBean.setUserId(bean.getUserId());
		userFamilyRoleLogParamBean.setFamilyId(bean.getFamilyId());
		
		List<UserFamilyRoleLogBean> startEndList = this.familyMapper.queryStartEnd(userFamilyRoleLogParamBean);
		String startTime = null;
		String endTime = null;
		int countUnReceive = 0;
		
		if (CollectionUtils.isNotEmpty(startEndList))
		{
			for (UserFamilyRoleLogBean tempBean : startEndList)
			{
				String tempEndTime = tempBean.getEndTime();
				if (StringUtils.startsWith(tempEndTime, yesterdayStr))
				{
					startTime = tempBean.getStartTime();
					endTime = tempEndTime;
					
					bean.setStartTime(startTime);
					bean.setEndTime(endTime);
					List<Long> habitList = this.familyMapper.queryHabits(bean);
					
					if (CollectionUtils.isNotEmpty(habitList))
					{
						countUnReceive = this.familyMapper.countUnReceive(habitList);
						break;
					}
					else
					{
						countUnReceive = 0;
					}
				}
			}
		}
		
		
		if (0 == taskFlag && countUnReceive > 0 && 0 != bean.getFamilyId())
		{
			resultBean.setReadFlag(1);
		}
		else
		{
			resultBean.setReadFlag(0);
		}
		
		return resultBean;
	}
	
	/**
	 * 标记新手指导已阅读
	 * @param bean
	 */
	public void updateTaskFlag(FamilyParamBean bean)
	{
		//标记新手指导标记位
		this.familyMapper.updateTaskFlag(bean);
	}
}
