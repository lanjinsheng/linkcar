package com.idata365.app.service;

import java.util.HashMap;
import java.util.List;
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
import com.idata365.app.util.DateTools;
import com.idata365.app.util.PhoneUtils;
 

@Service
public class AccountService extends BaseService<AccountService>
{
	private final static Logger LOG = LoggerFactory.getLogger(AccountService.class);
	@Autowired
	UsersAccountMapper usersAccountMapper;
	@Autowired
	UserLoginSessionMapper userLoginSessionMapper;

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
	public Map<String,Object> getUsersInfoByIds(String userIds,String getImgBasePath)
	{
		Map<String,Object> rtMap=new HashMap<String,Object>();
		StringBuffer userNickNames=new StringBuffer();
		StringBuffer userHeadUrls=new StringBuffer();

		String []users=userIds.split(",");
		for(String id:users) {
			UsersAccount account=usersAccountMapper.findAccountById(Long.valueOf(id));
			if(account!=null) {
				userNickNames.append(account.getNickName()==null?PhoneUtils.hidePhone(account.getPhone()):account.getNickName());
				userNickNames.append(",");
				userHeadUrls.append(account.getImgUrl()==null?"":getImgBasePath+account.getImgUrl());
				userHeadUrls.append(",");
			}
		}
		rtMap.put("nickNames", userNickNames.substring(0, userNickNames.length()-1));
		rtMap.put("userHeadUrls", userHeadUrls.substring(0, userHeadUrls.length()-1));
		return rtMap;
	}
	
	/**
	 * 
	    * @Title: getFamiliesInfoByUserId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public Map<String,Object> getFamiliesInfoByUserId(long userId)
	{
		Map<String,Object> rtMap=new HashMap<String,Object>();
		 Map<String,Object> my=usersAccountMapper.getFamilyByUserId(userId);
		 if(my==null) return null;
//	 
//		    * Map.put("fightFamilyId":"id")
//	        * Map.put("fightFamilyUserCount":"number")
		   rtMap.put("familyId", my.get("id"));
	       rtMap.put("familyUserCount", my.get("memberNum"));
	       rtMap.put("daystamp", DateTools.getCurDateYYYY_MM_dd());
	       Map<String,Object> opp=usersAccountMapper.getOpponentFamilyId(rtMap);
	       
	        if(opp!=null && opp.size()>0) {
	        	Long familyId2=0l;
	        	if(String.valueOf(opp.get("selfFamilyId")).equals(String.valueOf(my.get("id")))){
	        		familyId2=Long.valueOf(opp.get("competitorFamilyId").toString());
	        	}else {
	        		familyId2=Long.valueOf(opp.get("selfFamilyId").toString());
	        	}
	        	 Map<String,Object> family2=usersAccountMapper.getFamilyByFamilyId(familyId2);
	        	 rtMap.put("fightFamilyId",family2.get("id"));
	  	         rtMap.put("fightFamilyUserCount", family2.get("memberNum"));
	        }else {
	        	 rtMap.put("fightFamilyId",0);
	  	         rtMap.put("fightFamilyUserCount", 0);
	        }
	        
		return rtMap;
	}
	/**
	 * 
	    * @Title: getFamiliesInfoByUserId
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param userId
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@Transactional
	public Map<String,Object> getFamiliesInfoByFamilyId(long familyId)
	{
		Map<String,Object> rtMap=new HashMap<String,Object>();
		 Map<String,Object> family1=usersAccountMapper.getFamilyByFamilyId(familyId);
		 if(family1==null) return null;
//	 
//		    * Map.put("fightFamilyId":"id")
//	        * Map.put("fightFamilyUserCount":"number")
		   rtMap.put("familyId", familyId);
	       rtMap.put("familyUserCount", family1.get("memberNum"));
	       rtMap.put("daystamp", DateTools.getCurDateYYYY_MM_dd());
	       Map<String,Object> opp=usersAccountMapper.getOpponentFamilyId(rtMap);
	       
	        if(opp!=null && opp.size()>0) {
	        	Long familyId2=0l;
	        	if(String.valueOf(opp.get("selfFamilyId")).equals(String.valueOf(familyId))){
	        		familyId2=Long.valueOf(opp.get("competitorFamilyId").toString());
	        	}else {
	        		familyId2=Long.valueOf(opp.get("selfFamilyId").toString());
	        	}
	        	 Map<String,Object> family2=usersAccountMapper.getFamilyByFamilyId(familyId2);
	        	 rtMap.put("fightFamilyId",family2.get("id"));
	  	         rtMap.put("fightFamilyUserCount", family2.get("memberNum"));
	        }else {
	        	 rtMap.put("fightFamilyId",0);
	  	         rtMap.put("fightFamilyUserCount", 0);
	        }
	        
		return rtMap;
	}
	@Transactional
	public List<Map<String,Object>> getUsersByFamilyId(long familyId,String daystamp){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("familyId", familyId);
		param.put("daystamp", daystamp);
		return usersAccountMapper.getUsersByFamilyId(param);
	}
	
	@Transactional
	public List<Map<String,Object>> getCurrentUsersByFamilyId(long familyId,String daystamp){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("familyId", familyId);
		param.put("daystamp", daystamp);
		return usersAccountMapper.getCurrentUsersByFamilyId(param);
	}
	@Transactional
	public boolean updateUserLoginTime(long userId){
		UsersAccount u=new UsersAccount();
		u.setId(userId);
		usersAccountMapper.updateUserLoginTime(u);
		return true;
	}
	
	
}
