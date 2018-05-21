package com.idata365.app.controller.security;

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

import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.remote.ChezuAccountService;
import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.service.AssetService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;
import com.idata365.app.util.ValidTools;

@RestController("securityAssetController")
public class AssetController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	@Autowired
	AssetService assetService;
	@Autowired
	ChezuAccountService chezuAccountService;
     
	@Autowired
	ChezuAppService chezuAppService;
     
	
	/**
	 * 
	 * @Title: getIndexDiamonds
	 * @Description: TODO(获取首页钻石、动力总数量)
	 * @param @return
	 *            参数
	 * @return <Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getTotalNums")
	public Map<String, Object> getTotalNums(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		Map<String, String> data = assetService.getTotalNums(userId);
		return ResultUtils.rtSuccess(data);
	}

	/**
	 * 
	 * @Title: getIndexDiamonds
	 * @Description: TODO(获取首页钻石数量)
	 * @param @return
	 *            参数
	 * @return List<Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getIndexDiamonds")
	public Map<String, Object> getIndexDiamonds(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		List<Map<String, String>> data = assetService.getIndexDiamonds(userId, requestBodyParams);
		Map<String, String> myorder = assetService.getCurOrderAndNum(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("list", data);
		result.put("orderInfo", myorder);
		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 
	 * @Title: getIndexPowers
	 * @Description: TODO(获取首页动力数量)
	 * @param @return
	 *            参数
	 * @return List<Map<String,String>> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getIndexPowers")
	public Map<String, Object> getIndexPowers(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		List<Map<String, String>> data = assetService.getIndexPowers(userId, requestBodyParams);
		Map<String, String> myorder = assetService.getCurOrderAndNum(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("list", data);
		result.put("orderInfo", myorder);
		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 
	 * @Title: getFamilyFightPowers
	 * @Description: TODO(获取家族对战动力情况)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getFamilyFightPowers")
	public Map<String, Object> getFamilyFightPowers(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		Map<String, Object> familiesInfo = chezuAccountService.getFamiliesInfoByfamilyId(familyId, sign);
		return ResultUtils.rtSuccess(assetService.getFamilyPowers(userId, familiesInfo, requestBodyParams));
	}

	/**
	 * 
	 * @Title: stoleFamilyFightPowers
	 * @Description: TODO(家族对战能量偷取)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/stoleFamilyFightPowers")
	public Map<String, Object> stoleFamilyFightPowers(
			@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		long ballId = Long.valueOf(String.valueOf(requestBodyParams.get("ballId")));
		long powerNum = Long.valueOf(String.valueOf(requestBodyParams.get("power")));
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		Map<String, Object> familiesInfo = chezuAccountService.getFamiliesInfoByfamilyId(familyId, sign);
		Map<String, String> datas = new HashMap<>();
		try {
			assetService.stoleFamilyFightPowers(userId, familiesInfo, ballId, powerNum);
			datas.put("isReceive", "1");
			return ResultUtils.rtSuccess(datas);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			datas.put("isReceive", "0");
			return ResultUtils.rtSuccess(datas);
		}
	}

	/**
	 * 
	 * @Title: getStoleFamilyFightPowers
	 * @Description: TODO(家族对战能量偷取记录)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LiXing
	 */
	@RequestMapping("/getFamilyFightPowersRecord")
	public Map<String, Object> getStoleRecord(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		StringBuilder sb = new StringBuilder();
		List<Map<String, String>> result = assetService.getStoleRecord(familyId);
		for (Map<String, String> map : result) {
			sb.append(map.get("userId") + ",");
		}
		String Ids = sb.toString().substring(0, sb.length());
		Map<String, Object> map = chezuAccountService.getUsersInfoByIds(Ids,familyId, sign);

		if (ValidTools.isNotBlank(map) && ValidTools.isNotBlank(map.get("nickNames"))) {
			String nikeNames = map.get("nickNames").toString();
			String[] nikeName = nikeNames.split(",");
			for (int i = 0; i < result.size(); i++) {
				for (int j = 0; j < nikeName.length; j++) {
					if (j == i) {
						result.get(i).put("nickname", nikeName[i]);
						break;
					}
				}
			}
		}
		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 
	 * @Title: signInPower
	 * @Description: TODO(签到获取power)
	 * @param @param
	 *            allRequestParams
	 * @param @param
	 *            requestBodyParams
	 * @param @return
	 *            参数
	 * @return Map<String,Object> 返回类型
	 * @throws @author
	 *             LanYeYe
	 */

	@RequestMapping("/signInPower")
	public Map<String, Object> signInPower(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {

		long userId = this.getUserId();
		LOG.info("userId================="+userId);
		//
		assetService.getDaySignInLog(userId);
		String sign=SignUtils.encryptHMAC(String.valueOf(userId));
		chezuAppService.updateLoginBss(userId, sign);
		return ResultUtils.rtSuccess(null);
	}

}
