package com.idata365.app.controller.security;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.idata365.app.config.SystemProperties;
import com.idata365.app.entity.FamilyInfoScoreAllBean;
import com.idata365.app.entity.FamilyInfoScoreResultBean;
import com.idata365.app.entity.FamilyInviteParamBean;
import com.idata365.app.entity.FamilyInviteResultBean;
import com.idata365.app.entity.FamilyParamBean;
import com.idata365.app.entity.FamilyRandResultBean;
import com.idata365.app.entity.InviteInfoResultBean;
import com.idata365.app.entity.MainResultBean;
import com.idata365.app.entity.MyFamilyInfoResultBean;
import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.enums.UserImgsEnum;
import com.idata365.app.partnerApi.QQSSOTools;
import com.idata365.app.partnerApi.SSOTools;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.service.BusinessDatasService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class FamilyController extends BaseController
{
	@Autowired
	private FamilyService familyService;
	@Autowired
	BusinessDatasService businessDatasService;
	@Autowired
	SystemProperties systemProperties;
	@Autowired
	ChezuAssetService chezuAssetService;
	
	@RequestMapping("/family/removeMember")
	public Map<String, Object> removeMember(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		this.familyService.removeMember(reqBean,this.getUserInfo());
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 查看审核成员
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/getApplyInfo")
	public Map<String, Object> getApplyInfo(@RequestBody FamilyInviteParamBean bean)
	{
		LOG.info("param==={}", JSON.toJSONString(bean));
		List<FamilyInviteResultBean> resutList = this.familyService.getApplyInfo(bean);
		String imgBasePath = super.getImgBasePath();
		for (FamilyInviteResultBean tempBean : resutList)
		{
			String imgUrl = tempBean.getImgUrl();
			tempBean.setImgUrl(imgBasePath + imgUrl);
		}
		return ResultUtils.rtSuccess(resutList);
	}
	
	/**
	 * 审核成员通过
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/permitApply")
	public Map<String, Object> permitApply(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==permitApply====={}", JSON.toJSONString(reqBean));
		UserInfo userInfo = super.getUserInfo();
		int msgType = this.familyService.permitApply(reqBean, userInfo);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("msgType", String.valueOf(msgType));
		List<Map<String, String>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 拒绝用户加入
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/rejectApply")
	public Map<String, Object> rejectApply(@RequestBody FamilyParamBean reqBean)
	{
		UserInfo userInfo = super.getUserInfo();
		this.familyService.rejectApply(reqBean, userInfo);
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping("/family/quitFromFamily")
	public Map<String, Object> quitFromFamily(@RequestBody FamilyParamBean reqBean)
	{
		this.familyService.quitFromFamily(reqBean);
		return ResultUtils.rtSuccess(null);
	}
	
	@RequestMapping("/family/listRecruFamily")
	public Map<String, Object> listRecruFamily()
	{
		Long userId = super.getUserId();
		List<FamilyRandResultBean> resultList = this.familyService.listRecruFamily(userId);
		String imgBasePath = super.getImgBasePath();
		for (FamilyRandResultBean tempBean : resultList)
		{
			String imgUrl = tempBean.getImgUrl();
			tempBean.setImgUrl(imgBasePath + imgUrl);
		}
		return ResultUtils.rtSuccess(resultList);
	}
	
	@RequestMapping("/family/findFamilyByCode")
	public Map<String, Object> findFamilyByCode(@RequestBody FamilyParamBean reqBean)
	{
		Long userId = super.getUserId();
		FamilyRandResultBean resultBean = this.familyService.findFamilyByCode(reqBean, userId);
		if (null == resultBean)
		{
			return ResultUtils.rtSuccess(null);
		}
		else
		{
			String imgBasePath = super.getImgBasePath();
			String imgUrl = resultBean.getImgUrl();
			resultBean.setImgUrl(imgBasePath + imgUrl);
			return ResultUtils.rtSuccess(resultBean);
		}
	}
	
	@RequestMapping("/family/applyByFamily")
	public Map<String, Object> applyByFamily(@RequestBody FamilyInviteParamBean bean)
	{
		UserInfo userInfo = super.getUserInfo();
		
		this.familyService.applyByFamily(bean, userInfo);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 检查家族名称
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/checkFamilyName")
	public Map<String, Object> checkFamilyName(@RequestBody FamilyParamBean bean)
	{
		String duplicateFlag = this.familyService.checkFamilyName(bean);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("duplicateFlag", duplicateFlag);
		List<Map<String, String>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 创建家族
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/createFamily")
	public Map<String, Object> createFamily(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("createFamily===========param==={}", JSON.toJSONString(reqBean));
		long familyId = this.familyService.createFamily(reqBean);
		if (-1 == familyId)
		{
			return ResultUtils.rtFailRequest(null);
		}
		else if (-2 == familyId)
		{
			return ResultUtils.rtFailVerification(null);
		}
		//家族资产初始化
		chezuAssetService.initFamilyCreate(familyId, SignUtils.encryptHMAC(String.valueOf(familyId)));
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("familyId", familyId);
		List<Map<String, Long>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 生成邀请码
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/generateInviteInfo")
	public Map<String, Object> generateInviteInfo(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		InviteInfoResultBean resultBean = this.familyService.generateInviteInfo(reqBean);
		List<InviteInfoResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		return ResultUtils.rtSuccess(resultList);
	}
	/**
	 * 
	    * @Title: generateInviteInfo
	    * @Description: TODO(家族头像)
	    * @param @param reqBean
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	@RequestMapping("/family/updateFamilyImg")
    public  Map<String,Object>  updateFamilyImg(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) {
	   	 Long userId=this.getUserId();
	   	 if(file==null) {
	   		 return ResultUtils.rtFailParam(null,"附件为空");
	   	 }
     	Map<String,Object> rtMap=new HashMap<String,Object>();
	    try {
	    	 String key="";
	    	      if(systemProperties.getSsoQQ().equals("1")) {//走qq
	    	    	  key=QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.FAMILY_HEADER);
	    	    	  File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+key);
	        		  File fileParent = dealFile.getParentFile();  
	        			if(!fileParent.exists()){  
	        			    fileParent.mkdirs();  
	        			} 
	                  file.transferTo(dealFile);
	                  QQSSOTools.saveOSS(dealFile,key);
	    	      }else {//走阿里
	               //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
	    		   key=SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.FAMILY_HEADER);
	               InputStream is=file.getInputStream();
	               SSOTools.saveOSS(is,key);
	      
	               is.close();
	    	      }
	    	        rtMap.put("allImgUrl", getImgBasePath()+key);
		            rtMap.put("imgUrl", key);
	        }catch (Exception e) {
	               // TODO Auto-generated catch block
	            e.printStackTrace();
	            return ResultUtils.rtFail(null); 
	        }
	       return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 查询自己创建的家族
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/findMyFamily")
	public Map<String, Object> findMyFamily(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		
		MyFamilyInfoResultBean resultBean = this.familyService.findMyFamily(reqBean);
		List<MyFamilyInfoResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 查询用户创建的家族及加入的家族信息
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/queryFamilyRelationInfo")
	public Map<String, Object> queryFamilyRelationInfo(@RequestBody FamilyParamBean reqBean)
	{
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		//插入活跃日志
		businessDatasService.insertActiveLogs(this.getUserId());
		FamilyInfoScoreAllBean resultBean = this.familyService.queryFamilyRelationInfo(reqBean);
		String imgBasePath = super.getImgBasePath();
		
		FamilyInfoScoreResultBean joinFamily = resultBean.getJoinFamily();
		if (null != joinFamily)
		{
			joinFamily.setImgUrl(imgBasePath + joinFamily.getImgUrl());
		}
		
		FamilyInfoScoreResultBean origFamily = resultBean.getOrigFamily();
		if (null != origFamily)
		{
			origFamily.setImgUrl(imgBasePath + origFamily.getImgUrl());
		}
		
		List<FamilyInfoScoreAllBean> resultList = new ArrayList<>();
		resultList.add(resultBean);
		
		return ResultUtils.rtSuccess(resultList);
	}
	
	/**
	 * 主页统计字段接口
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/queryMainNum")
	public Map<String, Object> queryMainNum(@RequestBody FamilyParamBean reqBean)
	{
		MainResultBean resultBean = this.familyService.queryMainNum(reqBean);
		return ResultUtils.rtSuccess(resultBean);
	}
	
	/**
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/updateTaskFlag")
	public Map<String, Object> updateTaskFlag(@RequestBody FamilyParamBean reqBean)
	{
		this.familyService.updateTaskFlag(reqBean);
		return ResultUtils.rtSuccess(null);
	}
}
