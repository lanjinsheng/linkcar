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

import com.idata365.app.entity.UserDeviceLogs;
import com.idata365.app.entity.UserLoginSession;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.VerifyCode;
import com.idata365.app.mapper.UserDeviceLogsMapper;
import com.idata365.app.mapper.UserLoginSessionMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.mapper.VerifyCodeMapper;
import com.idata365.app.remote.ChezuColService;
import com.idata365.app.util.DateTools;

 

@Service
public class LoginRegService extends BaseService<LoginRegService>{
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
	ChezuColService  chezuColService;
	
	public LoginRegService() {
		LOG.info("DataService DataService DataService");
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
	public Map<String,Object> getVerifyCode(String phone,int codeType){
		 VerifyCode verifyCode=new VerifyCode();
		 verifyCode.setCodeType(codeType);
		 String code=getVerifyRoundValue();
		 verifyCode.setVerifyCode(code);
		 verifyCode.setCreateTimeLong(System.currentTimeMillis());
		 verifyCode.setPhone(phone);
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
	public String addDeviceUserInfo(String deviceToken,long userId){
		 UserDeviceLogs dl=new UserDeviceLogs();
		  String date=DateTools.getCurDate();
		 String remark= "{%s 用户:%s 设备号:%s 别名:%s}";
		 String alias="";
		 if(userId>0) {
			 alias=userId+"_"+0;
		 }else {
			 alias=0+"_"+System.currentTimeMillis();
		 }
		 remark=String.format(remark, date,String.valueOf(userId),deviceToken,alias);
		 dl.setAlias(alias);
		 dl.setRemark(remark);
		 dl.setDeviceToken(deviceToken);
		 dl.setUserId(userId);
		 userDeviceLogsMapper.insertUserDeviceLogs(dl);
		 if(userId>0) {
			//请求远程采集进行设备号与用户的更新处理
			 Map<String,Object> map=new HashMap<String,Object>();
			 map.put("userId", userId);
			 map.put("deviceToken", deviceToken);
			boolean b= chezuColService.updateUserDevice(map);
			if(b==false) {
				LOG.info("远程调用出错"+userId+"--"+deviceToken);
				return null;
			}
			LOG.info("发送远程请求！！！更新设备与用户信息"); 
		 }
		 return alias;
	}
	public static final String PHONE_ERR="PHONE_ERR";
	public static final String PWD_ERR="PWD_ERR";
	public static final String VC_ERR="VC_ERR";
	public static final String VC_EX="VC_EX";
	public static final String OK="OK";
	public String validVerifyCode(String phone,int codeType,String code) {
		 VerifyCode verifyCode=new VerifyCode();
		 verifyCode.setCodeType(codeType);
		 Long time=(System.currentTimeMillis()-5*60*1000);
		 verifyCode.setPhone(phone);
		 verifyCode.setVerifyCode(code);
		  VerifyCode dbVerify=verifyCodeMapper.findVerifyCode(verifyCode);
		      if(dbVerify==null ) {
				return VC_ERR;
		      }else {
		    	  if(time>dbVerify.getCreateTimeLong()) {
		    	  return VC_EX;
		    	  }
		      }
		      return OK;
	}
	
	public String validAccount(String phone,String pwd,UsersAccount pAccount) {
		 UsersAccount account=new UsersAccount();
		 account.setPhone(phone);
		 account.setPwd(pwd);
		 UsersAccount dbAccount=usersAccountMapper.findAccountByPhone(account);
		      if(dbAccount==null ) {
				return PHONE_ERR;
		      }else {
		    	  if(!dbAccount.getPwd().equalsIgnoreCase(pwd)) {
		    	  return PWD_ERR;
		    	  }
		      }
		      pAccount.setId(dbAccount.getId());
		      return OK;
	}	
	
	
	public boolean isPhoneExist(String phone) {
		 UsersAccount account=new UsersAccount();
		 account.setPhone(phone);
		 UsersAccount dbAccount=usersAccountMapper.findAccountByPhone(account);
		      if(dbAccount==null ) {
				return false;
		      }else {
		    	   return true;
		      }
	}	
	
	public void insertToken(Long userId,String token) {
		UserLoginSession loginSession=new UserLoginSession();
		loginSession.setCreateTimeLong(System.currentTimeMillis());
		loginSession.setUserId(userId);
		loginSession.setToken(token);
		userLoginSessionMapper.insertToken(loginSession);
		//更新登入时间
	}
	public String regUserTest(String phone,String pwd) {
		UsersAccount account=new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		usersAccountMapper.insertUser(account);
		LOG.info("id:"+account.getId());
		return null;
	}
	public String regUser(String phone,String pwd,Map<String,Object> rtMap) {
		UsersAccount account=new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		usersAccountMapper.insertUser(account);
		if(account!=null && account.getId()!=null && account.getId()>0) {
			//增加token信息并登入
			String token=UUID.randomUUID().toString().replaceAll("-", "");
			UserLoginSession loginSession=new UserLoginSession();
			loginSession.setCreateTimeLong(System.currentTimeMillis());
			loginSession.setUserId(account.getId());
			loginSession.setToken(token);
			userLoginSessionMapper.insertToken(loginSession);
			rtMap.put("userId", account.getId());
			return token;
		}
		return null;
	}
	
	public String updateUserPwd(String phone,String pwd,Map<String,Object> rtMap) {
		UsersAccount account=new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		UsersAccount dbAccount=usersAccountMapper.findAccountByPhone(account);
		usersAccountMapper.updateUserPwd(account);
		String token=UUID.randomUUID().toString().replaceAll("-", "");
		UserLoginSession loginSession=new UserLoginSession();
		loginSession.setCreateTimeLong(System.currentTimeMillis());
		loginSession.setUserId(dbAccount.getId());
		loginSession.setToken(token);
		userLoginSessionMapper.insertToken(loginSession);
	    rtMap.put("userId", dbAccount.getId());
		return token;
 
	}
	public List<VerifyCode> getVerifyCodeTest(){
		return verifyCodeMapper.getVerifyCodeTest(null);
	}
	
	 private  String getVerifyRoundValue() {
	        Random rd = new Random();
	        String strValue = "";
	        do {
	            char szNum = (char) (Math.abs(rd.nextInt()) % 10 + 48);// 产生数字0-9的随机数
	            strValue += Character.toString(szNum);
	        } while (strValue.length() < 6);
	        return strValue;
	    }
}
