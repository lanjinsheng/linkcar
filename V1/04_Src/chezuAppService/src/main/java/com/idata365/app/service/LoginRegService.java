package com.idata365.app.service;

/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.constant.RoleConstant;
import com.idata365.app.entity.FamilyInvite;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.Message;
import com.idata365.app.entity.UserDeviceLogs;
import com.idata365.app.entity.UserLoginSession;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.VerifyCode;
import com.idata365.app.enums.AchieveEnum;
import com.idata365.app.enums.MessageEnum;
import com.idata365.app.mapper.FamilyInviteMapper;
import com.idata365.app.mapper.FamilyMapper;
import com.idata365.app.mapper.UserDeviceLogsMapper;
import com.idata365.app.mapper.UserLoginSessionMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.mapper.VerifyCodeMapper;
import com.idata365.app.partnerApi.PhoneMsgTools;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.remote.ChezuService;
import com.idata365.app.service.common.AchieveCommService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.RandUtils;
import com.idata365.app.util.SignUtils;

@Service
public class LoginRegService extends BaseService<LoginRegService>
{
	private final static Logger LOG = LoggerFactory.getLogger(LoginRegService.class);
	@Autowired
	UsersAccountMapper usersAccountMapper;
	@Autowired
	UserLoginSessionMapper userLoginSessionMapper;
	@Autowired
	VerifyCodeMapper verifyCodeMapper;
	@Autowired
	UserDeviceLogsMapper userDeviceLogsMapper;
	@Autowired
	ChezuService chezuService;
	@Autowired
	ChezuAssetService chezuAssetService;
	@Autowired
	FamilyInviteMapper familyInviteMapper;
	@Autowired
	MessageService messageService;
	@Autowired
	AchieveCommService achieveCommService;
	@Autowired
	FamilyMapper familyMapper;
	@Autowired
	UserRoleLogService userRoleLogService;

	

	public LoginRegService()
	{
		LOG.info("LoginRegService LoginRegService LoginRegService");
	}

	/**
	 * 
	    * @Title: getVerifyCode
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param phone
	    * @param @param codeType
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	public Map<String, Object> getVerifyCode(String phone, int codeType)
	{
		VerifyCode verifyCode = new VerifyCode();
		verifyCode.setCodeType(codeType);
		String code = getVerifyRoundValue();
		verifyCode.setVerifyCode(code);
		verifyCode.setCreateTimeLong(System.currentTimeMillis());
		verifyCode.setPhone(phone);
		PhoneMsgTools.sendCodeMsg(phone,codeType, code);// 发送短信
		verifyCodeMapper.insertVerifyCode(verifyCode);
		return null;
	}

	/**
	 * 
	    * @Title: addDeviceUserInfo
	    * @Description: TODO(添加用户设备信息)
	    * @param @param phone
	    * @param @param codeType
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public String addDeviceUserInfo(String deviceToken, long userId)
	{
		UserDeviceLogs dl = new UserDeviceLogs();
		String date = DateTools.getCurDate();
		String remark = "{%s 用户:%s 设备号:%s 别名:%s}";
		String alias = "";
		if (userId > 0)
		{
			alias = userId + "_" + 0;
		}
		else
		{
			alias = 0 + "_" + System.currentTimeMillis();
		}
		remark = String.format(remark, date, String.valueOf(userId), deviceToken, alias);
		dl.setAlias(alias);
		dl.setRemark(remark);
		dl.setDeviceToken(deviceToken);
		dl.setUserId(userId);
		userDeviceLogsMapper.insertUserDeviceLogs(dl);
		if (userId > 0)
		{
			// 请求远程采集进行设备号与用户的更新处理
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("deviceToken", deviceToken);
			boolean b = chezuService.updateUserDevice(map);
			if (b == false)
			{
				LOG.info("远程调用出错" + userId + "--" + deviceToken);
				return null;
			}
			LOG.info("发送远程请求！！！更新设备与用户信息");
		}
		return alias;
	}

	public static final String PHONE_ERR = "PHONE_ERR";
	public static final String PWD_ERR = "PWD_ERR";
	public static final String VC_ERR = "VC_ERR";
	public static final String VC_EX = "VC_EX";
	public static final String OK = "OK";

	public String validVerifyCode(String phone, int codeType, String code)
	{
		VerifyCode verifyCode = new VerifyCode();
		verifyCode.setCodeType(codeType);
		Long time = (System.currentTimeMillis() - 5 * 60 * 1000);
		verifyCode.setPhone(phone);
		verifyCode.setVerifyCode(code);
		VerifyCode dbVerify = verifyCodeMapper.findVerifyCode(verifyCode);
		if (dbVerify == null)
		{
			return VC_ERR;
		}
		else
		{
			if (time > dbVerify.getCreateTimeLong())
			{
				return VC_EX;
			}
		}
		return OK;
	}

	public String validAccount(String phone, String pwd, UsersAccount pAccount)
	{
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		UsersAccount dbAccount = usersAccountMapper.findAccountByPhone(account);
		if (dbAccount == null)
		{
			return PHONE_ERR;
		}
		else
		{
			if (!dbAccount.getPwd().equalsIgnoreCase(pwd))
			{
				return PWD_ERR;
			}
		}
		pAccount.setId(dbAccount.getId());
		pAccount.setImgUrl(dbAccount.getImgUrl());
		pAccount.setNickName(dbAccount.getNickName());
		return OK;
	}

	public UsersAccount validToken(String token)
	{

		UserLoginSession session = userLoginSessionMapper.findToken(token);
		if (session == null)
		{
			return null;
		}
		UsersAccount account = usersAccountMapper.findAccountById(session.getUserId());
		return account;
	}

	public boolean isPhoneExist(String phone)
	{
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		UsersAccount dbAccount = usersAccountMapper.findAccountByPhone(account);
		if (dbAccount == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public UsersAccount getUserByPhone(String phone)
	{
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		UsersAccount dbAccount = usersAccountMapper.findAccountByPhone(account);
		return dbAccount;
	}

	public void insertToken(Long userId, String token)
	{
		UserLoginSession loginSession = new UserLoginSession();
		loginSession.setCreateTimeLong(System.currentTimeMillis());
		loginSession.setUserId(userId);
		loginSession.setToken(token);
		userLoginSessionMapper.insertToken(loginSession);
		// 更新登入时间
	}

	public String regUserTest(String phone, String pwd)
	{
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		usersAccountMapper.insertUser(account);
		LOG.info("id:" + account.getId());
		return null;
	}

	// 小菜补充
	// 用familyId查出该族长用户id,后调用成就公共接口统计方法
	public void achieveAddNewUser(long familyId)
	{
		FamilyParamBean bean = new FamilyParamBean();
		bean.setFamilyId(familyId);
		// 查询出用户Id
		Long userId = familyMapper.queryCreateUserId(bean);
		// 调用公共接口
		achieveCommService.addAchieve(userId, 0d, AchieveEnum.AddGayTimes);
	}
    @Transactional
	public String regUser(String phone, String pwd, String nickName,Map<String, Object> rtMap,UsersAccount account)
	{
//		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		account.setNickName(nickName);
		int hadInsert=usersAccountMapper.insertUser(account);
		while(hadInsert==0){
			nickName=nickName+RandUtils.generateRand(1, 100);
			account.setNickName(nickName);
			hadInsert=usersAccountMapper.insertUser(account);
		}
		if (account != null && account.getId() != null && account.getId() > 0)
		{
			// 增加token信息并登入
			String token = UUID.randomUUID().toString().replaceAll("-", "");
			UserLoginSession loginSession = new UserLoginSession();
			loginSession.setCreateTimeLong(System.currentTimeMillis());
			loginSession.setUserId(account.getId());
			loginSession.setToken(token);
			userLoginSessionMapper.insertToken(loginSession);
			rtMap.put("userId", account.getId());
			
			//插入角色
			userRoleLogService.insertUserRoleLog(account.getId(), RoleConstant.LAOSIJI_ROLE);
			try
			{
				//初始化账号
				chezuAssetService.initUserCreate(account.getId(), SignUtils.encryptHMAC(String.valueOf(account.getId())));
				
				// 进行家族绑定的检索，如果存在家族邀请，则发送申请消息
				List<FamilyInvite> list = familyInviteMapper.getFamilyInviteByPhone(phone);
				for (FamilyInvite invite : list)
				{
					Map<String, Object> m = usersAccountMapper.getFamilyByFamilyId(invite.getFamilyId());
					// 构建成员加入消息
					Message message = messageService.buildMessage(account.getId(), phone, "",
							Long.valueOf(m.get("createUserId").toString()), invite.getId(), MessageEnum.INVITE_FAMILY);
					// 插入消息
					messageService.insertMessage(message, MessageEnum.INVITE_FAMILY);
					// 推送消息
					messageService.pushMessageNotrans(message, MessageEnum.INVITE_FAMILY);
					// 更新invite
					invite.setMemberUserId(account.getId());
					familyInviteMapper.updateFamilyInviteWhenReg(invite);
				}
				// 发送成就初始化
				achieveCommService.initCreateUserAchieve(account.getId());

				// 发送拉新信息
				if (list != null && list.size() > 0)
				{
					FamilyInvite invite = list.get(0);
					achieveAddNewUser(invite.getFamilyId());
				}

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return token;
		}
		return null;
	}

	public String updateUserPwd(String phone, String pwd, Map<String, Object> rtMap)
	{
		UsersAccount account = new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		UsersAccount dbAccount = usersAccountMapper.findAccountByPhone(account);
		usersAccountMapper.updateUserPwd(account);
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		UserLoginSession loginSession = new UserLoginSession();
		loginSession.setCreateTimeLong(System.currentTimeMillis());
		loginSession.setUserId(dbAccount.getId());
		loginSession.setToken(token);
		userLoginSessionMapper.insertToken(loginSession);
		rtMap.put("userId", dbAccount.getId());
		return token;

	}

	public List<VerifyCode> getVerifyCodeTest()
	{
		return verifyCodeMapper.getVerifyCodeTest(null);
	}

	private String getVerifyRoundValue()
	{
		Random rd = new Random();
		String strValue = "";
		do
		{
			char szNum = (char) (Math.abs(rd.nextInt()) % 10 + 48);// 产生数字0-9的随机数
			strValue += Character.toString(szNum);
		}
		while (strValue.length() < 6);
		return strValue;
	}
	
	@Transactional
	public int updateUserNickName(UsersAccount account) {
		int i = usersAccountMapper.hadAccountByNick(account.getNickName());
		if (i > 0) {
			account.setNickName(account.getNickName()+RandUtils.generateRand(1, 100));
		}
		usersAccountMapper.updateNickName(account);
		return 0;
	}
}
