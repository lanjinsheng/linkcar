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
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.app.entity.UserLoginSession;
import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.VerifyCode;
import com.idata365.app.mapper.UserLoginSessionMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.mapper.VerifyCodeMapper;

 

@Service
public class LoginRegService extends BaseService<LoginRegService>{
	private final static Logger LOG = LoggerFactory.getLogger(LoginRegService.class);
	@Autowired
	 UsersAccountMapper usersAccountMapper;
	@Autowired
	 UserLoginSessionMapper userLoginSessionMapper;
	@Autowired
	 VerifyCodeMapper verifyCodeMapper;
	 
	
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
		      pAccount=dbAccount;
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
	
	public String regUser(String phone,String pwd) {
		UsersAccount account=new UsersAccount();
		account.setPhone(phone);
		account.setPwd(pwd);
		usersAccountMapper.insertUser(account);
		if(account.getId()>0) {
			//增加token信息并登入
			String token=UUID.randomUUID().toString().replaceAll("-", "");
			UserLoginSession loginSession=new UserLoginSession();
			loginSession.setCreateTimeLong(System.currentTimeMillis());
			loginSession.setUserId(account.getId());
			loginSession.setToken(token);
			userLoginSessionMapper.insertToken(loginSession);
			return token;
		}
		return null;
	}
	
	public String updateUserPwd(String phone,String pwd) {
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
