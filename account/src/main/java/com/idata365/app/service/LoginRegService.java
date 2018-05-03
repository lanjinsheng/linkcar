package com.idata365.app.service;

import java.util.HashMap;
import java.util.Map;

/**
 * 
    * @ClassName: CollectService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月23日
    *
 */



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.UserLoginSession;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.mapper.UserLoginSessionMapper;
import com.idata365.app.mapper.UsersAccountMapper;
 

@Service
public class LoginRegService extends BaseService<LoginRegService>
{
	private final static Logger LOG = LoggerFactory.getLogger(LoginRegService.class);
	@Autowired
	UsersAccountMapper usersAccountMapper;
	@Autowired
	UserLoginSessionMapper userLoginSessionMapper;

	public LoginRegService()
	{
		LOG.info("LoginRegService LoginRegService LoginRegService");
	}

	@Transactional
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
	/**
	 * 
	    * @Title: getFamiliesInfoByUserId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userIds
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public Map<String,Object> getFamiliesInfoByUserId(String userIds)
	{
		Map<String,Object> rtMap=new HashMap<String,Object>();
		StringBuffer userNickNames=new StringBuffer();
		StringBuffer userHeadUrls=new StringBuffer();

		String []users=userIds.split(",");
		for(String id:users) {
			UsersAccount account=usersAccountMapper.findAccountById(Long.valueOf(id));
			if(account!=null) {
				userNickNames.append(account.getNickName()==null?account.getPhone():account.getNickName());
				userNickNames.append(",");
				userHeadUrls.append(account.getImgUrl()==null?"":account.getImgUrl());
				userHeadUrls.append(",");
			}
		}
		rtMap.put("nickNames", userNickNames.substring(0, userNickNames.length()-1));
		rtMap.put("userHeadUrls", userHeadUrls.substring(0, userHeadUrls.length()-1));
		return rtMap;
	}
}
