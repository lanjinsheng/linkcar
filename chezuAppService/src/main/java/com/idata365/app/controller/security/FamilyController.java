package com.idata365.app.controller.security;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.idata365.app.remote.ChezuImService;
import com.idata365.app.service.BusinessDatasService;
import com.idata365.app.service.FamilyService;
import com.idata365.app.service.UserInfoService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class FamilyController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FamilyController.class);
	@Autowired
	private FamilyService familyService;
	@Autowired
	BusinessDatasService businessDatasService;
	@Autowired
	SystemProperties systemProperties;
	@Autowired
	ChezuAssetService chezuAssetService;
	@Autowired
	ChezuImService chezuImService;
	@Autowired
	UserInfoService userInfoService;
	
	@RequestMapping("/family/removeMember")
	public Map<String, Object> removeMember(@RequestBody FamilyParamBean reqBean) {
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		
		List<Map<String,Object>> list1=userInfoService.getFamilyUsers(reqBean.getFamilyId(),this.getImgBasePath());
		this.familyService.removeMember(reqBean, this.getUserInfo());
		String sign=SignUtils.encryptHMAC(String.valueOf(reqBean.getUserId()));
		chezuImService.notifyFamilyChange(list1, sign);
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 查看审核成员
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/getApplyInfo")
	public Map<String, Object> getApplyInfo(@RequestBody FamilyInviteParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		FamilyInviteResultBean result = this.familyService.getApplyInfo(bean);
		String imgBasePath = super.getImgBasePath();
		result.setImgUrl(imgBasePath + result.getImgUrl());
		return ResultUtils.rtSuccess(result);
	}
	
	/**
	 * 查看家族邀请(招募)
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/getInviteInfo")
	public Map<String, Object> getInviteInfo(@RequestBody FamilyInviteParamBean bean) {
		LOG.info("param==={}", JSON.toJSONString(bean));
		FamilyInviteResultBean result = this.familyService.getInviteInfo(bean);
		String imgBasePath = super.getImgBasePath();
		result.setImgUrl(imgBasePath + result.getImgUrl());
		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 审核成员通过OR通过家族邀请
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/permitApply")
	public Map<String, Object> permitApply(@RequestBody FamilyParamBean reqBean) {
		LOG.info("param==permitApply====={}", JSON.toJSONString(reqBean));
		UserInfo userInfo = super.getUserInfo();
		String path = this.getImgBasePath();
		int msgType = this.familyService.permitApply(reqBean, userInfo, path);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("msgType", String.valueOf(msgType));
		List<Map<String, String>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		String sign=SignUtils.encryptHMAC(String.valueOf(reqBean.getUserId()));
		List<Map<String,Object>> list1=userInfoService.getFamilyUsers(reqBean.getFamilyId(),path);
		chezuImService.notifyFamilyChange(list1, sign);
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 拒绝用户加入OR拒绝家族邀请
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/rejectApply")
	public Map<String, Object> rejectApply(@RequestBody FamilyParamBean reqBean) {
		UserInfo userInfo = super.getUserInfo();
		this.familyService.rejectApply(reqBean, userInfo);
		return ResultUtils.rtSuccess(null);
	}
	
	//退出家族
	@RequestMapping("/family/quitFromFamily")
	public Map<String, Object> quitFromFamily(@RequestBody FamilyParamBean reqBean) {
		List<Map<String,Object>> list1=userInfoService.getFamilyUsers(reqBean.getFamilyId(),this.getImgBasePath());
		this.familyService.quitFromFamily(reqBean);
		chezuImService.notifyFamilyChange(list1, "");
		return ResultUtils.rtSuccess(null);
	}

	//可加入家族列表
	@RequestMapping("/family/listRecruFamily")
	public Map<String, Object> listRecruFamily() {
		Long userId = super.getUserId();
		List<FamilyRandResultBean> resultList = this.familyService.listRecruFamily(userId);
		String imgBasePath = super.getImgBasePath();
		for (FamilyRandResultBean tempBean : resultList) {
			String imgUrl = tempBean.getImgUrl();
			tempBean.setImgUrl(imgBasePath + imgUrl);
		}
		return ResultUtils.rtSuccess(resultList);
	}

	//通过邀请码查询家族
	@RequestMapping("/family/findFamilyByCode")
	public Map<String, Object> findFamilyByCode(@RequestBody FamilyParamBean reqBean) {
		Long userId = super.getUserId();
		FamilyRandResultBean resultBean = this.familyService.findFamilyByCode(reqBean, userId);
		if (null == resultBean) {
			return ResultUtils.rtSuccess(null);
		} else {
			String imgBasePath = super.getImgBasePath();
			String imgUrl = resultBean.getImgUrl();
			resultBean.setImgUrl(imgBasePath + imgUrl);
			return ResultUtils.rtSuccess(resultBean);
		}
	}

	//通过名字查询家族
	@RequestMapping("/family/queryFamilyByName")
	public Map<String, Object> queryFamilyByName(@RequestBody FamilyParamBean reqBean) {
		List<FamilyRandResultBean> result = new ArrayList<>();
		Long userId = super.getUserId();
		List<FamilyRandResultBean> resultBeans = this.familyService.queryFamilyByName(reqBean, userId);
		Map<String, Object> resultMap = new HashMap<>();
		if (null != resultBeans && resultBeans.size() != 0) {
			for (FamilyRandResultBean resultBean : resultBeans) {
				String imgBasePath = super.getImgBasePath();
				String imgUrl = resultBean.getImgUrl();
				resultBean.setImgUrl(imgBasePath + imgUrl);
				result.add(resultBean);
			}
		} else {
			return ResultUtils.rtSuccess(null);
		}
		resultMap.put("families", result);
		return ResultUtils.rtSuccess(resultMap);
	}

	/**
	 * 
	 * @Title: applyByFamily
	 * @Description: TODO(申请加入指定家族OR家族邀请指定用户)
	 * @param @return
	 *            参数
	 * @return <Map<String,Object>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/family/applyByFamily")
	public Map<String, Object> applyByFamily(@RequestBody FamilyInviteParamBean bean) {
		UserInfo userInfo = super.getUserInfo();

		int i = this.familyService.applyByFamily(bean, userInfo);
		if(i==0) {
			return ResultUtils.rtFail(null, "该用户已在该家族中", "100");
		}
		return ResultUtils.rtSuccess(null);
	}

	/**
	 * 检查家族名称
	 * 
	 * @param bean
	 * @return
	 */
	@RequestMapping("/family/checkFamilyName")
	public Map<String, Object> checkFamilyName(@RequestBody FamilyParamBean bean) {
		String duplicateFlag = this.familyService.checkFamilyName(bean);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("duplicateFlag", duplicateFlag);
		List<Map<String, String>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 创建家族
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/createFamily")
	public Map<String, Object> createFamily(@RequestBody FamilyParamBean reqBean) {
		LOG.info("createFamily===========param==={}", JSON.toJSONString(reqBean));
		long familyId = this.familyService.createFamily(reqBean, this.getUserId());
		if (-1 == familyId) {
			return ResultUtils.rtFailRequest(null);
		} else if (-2 == familyId) {
			return ResultUtils.rtFailVerification(null);
		}
		// 初始化家族分数

		// 家族资产初始化
		chezuAssetService.initFamilyCreate(familyId, SignUtils.encryptHMAC(String.valueOf(familyId)));
		Map<String, Long> resultMap = new HashMap<>();
		resultMap.put("familyId", familyId);
		List<Map<String, Long>> resultList = new ArrayList<>();
		resultList.add(resultMap);
		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 生成邀请码
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/generateInviteInfo")
	public Map<String, Object> generateInviteInfo(@RequestBody FamilyParamBean reqBean) {
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
	 * @param @param
	 *            reqBean
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */
	@RequestMapping("/family/updateFamilyImg")
	public Map<String, Object> updateFamilyImg(@RequestParam CommonsMultipartFile file,
			@RequestParam Map<String, Object> map) {
		Long userId = this.getUserId();
		if (file == null) {
			return ResultUtils.rtFailParam(null, "附件为空");
		}
		Map<String, Object> rtMap = new HashMap<String, Object>();
		try {
			String key = "";
			if (systemProperties.getSsoQQ().equals("1")) {// 走qq
				key = QQSSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.FAMILY_HEADER);
				File dealFile = new File(systemProperties.getFileTmpDir() + "/" + key);
				File fileParent = dealFile.getParentFile();
				if (!fileParent.exists()) {
					fileParent.mkdirs();
				}
				file.transferTo(dealFile);
				QQSSOTools.saveOSS(dealFile, key);
			} else {// 走阿里
				// 获取输入流 CommonsMultipartFile 中可以直接得到文件的流
				key = SSOTools.createSSOUsersImgInfoKey(userId, UserImgsEnum.FAMILY_HEADER);
				InputStream is = file.getInputStream();
				SSOTools.saveOSS(is, key);

				is.close();
			}
			rtMap.put("allImgUrl", getImgBasePath() + key);
			rtMap.put("imgUrl", key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultUtils.rtFail(null);
		}
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 查询自己创建的家族
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/findMyFamily")
	public Map<String, Object> findMyFamily(@RequestBody FamilyParamBean reqBean) {
		LOG.info("param==={}", JSON.toJSONString(reqBean));

		MyFamilyInfoResultBean resultBean = this.familyService.findMyFamily(reqBean);
		List<MyFamilyInfoResultBean> resultList = new ArrayList<>();
		resultList.add(resultBean);

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 查询用户创建的家族及加入的家族信息
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/queryFamilyRelationInfo")
	public Map<String, Object> queryFamilyRelationInfo(@RequestBody FamilyParamBean reqBean) {
		LOG.info("param==={}", JSON.toJSONString(reqBean));
		// 插入活跃日志
		businessDatasService.insertActiveLogs(this.getUserId());
		FamilyInfoScoreAllBean resultBean = this.familyService.queryFamilyRelationInfo(reqBean);
		String imgBasePath = super.getImgBasePath();

		FamilyInfoScoreResultBean joinFamily = resultBean.getJoinFamily();
		if (null != joinFamily) {
			joinFamily.setImgUrl(imgBasePath + joinFamily.getImgUrl());
		}

		FamilyInfoScoreResultBean origFamily = resultBean.getOrigFamily();
		if (null != origFamily) {
			origFamily.setImgUrl(imgBasePath + origFamily.getImgUrl());
		}

		List<FamilyInfoScoreAllBean> resultList = new ArrayList<>();
		resultList.add(resultBean);

		return ResultUtils.rtSuccess(resultList);
	}

	/**
	 * 主页统计字段接口
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/queryMainNum")
	public Map<String, Object> queryMainNum(@RequestBody FamilyParamBean reqBean) {
		reqBean.setUserId(this.getUserId());
		MainResultBean resultBean = this.familyService.queryMainNum(reqBean);
		return ResultUtils.rtSuccess(resultBean);
	}

	/**
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/updateTaskFlag")
	public Map<String, Object> updateTaskFlag(@RequestBody FamilyParamBean reqBean) {
		this.familyService.updateTaskFlag(reqBean);
		return ResultUtils.rtSuccess(null);
	}
	
	/**
	 * 可招募成员列表
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/canRecruitList")
	public Map<String, Object> canRecruitList(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		String nickName =String.valueOf(requestBodyParams.get("nickname"));
		LOG.info("userId=================" + userId);
		LOG.info("familyId=================" + familyId);
		LOG.info("nickName=================" + nickName);
		List<Map<String, String>> rtList = this.familyService.canRecruitList(userId,familyId,nickName);

		return ResultUtils.rtSuccess(rtList);
	}
}
