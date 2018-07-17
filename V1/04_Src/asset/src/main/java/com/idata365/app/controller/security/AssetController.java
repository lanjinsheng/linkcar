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
		LOG.info("userId=================" + userId);
		Map<String, String> data = assetService.getTotalNums(userId);
		return ResultUtils.rtSuccess(data);
	}

	/**
	 * 
	 * @Title: getIndexDiamonds
	 * @Description: TODO(个人钻石记录)
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
		LOG.info("userId=================" + userId);
		List<Map<String, String>> list = assetService.getIndexDiamonds(userId, requestBodyParams);
		Map<String, String> myorder = assetService.getCurOrderAndNum(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("orderInfo", myorder);
		// result.put("diamondsInfo", assetService.diamondsInfo(userId));

		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 
	 * @Title: getIndexPowers
	 * @Description: TODO(个人动力记录)
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
		LOG.info("userId=================" + userId);
		List<Map<String, String>> list = assetService.getIndexPowers(userId, requestBodyParams);
		Map<String, String> myorder = assetService.getCurOrderAndNum(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("list", list);
		result.put("orderInfo", myorder);
		// result.put("powersInfo", assetService.powersInfo(userId));
		return ResultUtils.rtSuccess(result);
	}

	/**
	 * 
	 * @Title: getFamilyFightPowers
	 * @Description: TODO(获取家族对战动力情况---停车场)
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
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("userId=================" + userId);
		LOG.info("familyId=================" + familyId);
		String sign = SignUtils.encryptHMAC(String.valueOf(familyId));
		Map<String, Object> familiesInfo = chezuAccountService.getFamiliesInfoByfamilyId(familyId, sign);
//		Map<String, Object> relationInfo = chezuAppService.getFightRelationAsset(familyId, sign);
		return ResultUtils
				.rtSuccess(assetService.getFamilyPowers(userId, familiesInfo, requestBodyParams));
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
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		long ballId = Long.valueOf(String.valueOf(requestBodyParams.get("ballId")));
		long powerNum = Long.valueOf(String.valueOf(requestBodyParams.get("power")));
		LOG.info("userId=================" + userId);
		LOG.info("familyId=================" + familyId);
		LOG.info("ballId=================" + ballId);
		LOG.info("powerNum=================" + powerNum);
		String sign = SignUtils.encryptHMAC(String.valueOf(familyId));
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
		long familyId = Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("userId=================" + userId);
		LOG.info("familyId=================" + familyId);
		String sign = SignUtils.encryptHMAC(String.valueOf(familyId));
		StringBuilder sb = new StringBuilder();
		long fightFamilyId = Long
				.valueOf(chezuAccountService.getFamiliesInfoByfamilyId(familyId, sign).get("fightFamilyId").toString());
		List<Map<String, String>> result = assetService.getStoleRecord(familyId, fightFamilyId);
		for (Map<String, String> map : result) {
			sb.append(map.get("userId") + ",");
		}
		String Ids = sb.toString().substring(0, sb.length());
		Map<String, Object> map = chezuAccountService.getUsersInfoByIds(Ids, familyId, sign);

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
		LOG.info("userId=================" + userId);
		//
		assetService.getDaySignInLog(userId);
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		chezuAppService.updateLoginBss(userId, sign);
		return ResultUtils.rtSuccess(null);
	}

	@RequestMapping("/getYestodayHarvest")
	public Map<String, Object> getYestodayHarvest(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		long userId = this.getUserId();
		Object familyId = requestBodyParams.get("familyId");

		if (familyId == null) {
			// 通过userId获取全family分配值
			return ResultUtils.rtFailParam(null);
		}
		// 通过familyId获取family分配值
		Map<String, Object> familyHarvest = assetService.getFamilyHarvestYestoday(userId,
				Long.valueOf(familyId.toString()));
		// 通过userId获取昨日动力值
		Map<String, Object> personHarvest = assetService.getPersonHarvestYestoday(userId);
		Map<String, Object> global = assetService.getGlobalYestoday();

		rtMap.put("globalHarvest", global);
		rtMap.put("familyHarvest", familyHarvest);
		rtMap.put("personHarvest", personHarvest);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	@RequestMapping("/v2/getYestodayHarvest")
	public Map<String, Object> getYestodayHarvestV2(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = new HashMap<String, Object>();
		long userId = this.getUserId();
		Object familyId = requestBodyParams.get("familyId");

		if (familyId == null) {
			// 通过userId获取全family分配值
			return ResultUtils.rtFailParam(null);
		}
		// 
		List<Map<String, Object>> list = assetService.getYestodayHarvestV2(userId,
				Long.valueOf(familyId.toString()));
		rtMap.put("assetList", list);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	/**
	 * 
	        * @Title: pltfDiamondsDetail
	        * @Description: TODO(平台运营账号钻石详情)
	        * @param @param allRequestParams
	        * @param @param requestBodyParams
	        * @param @return 参数
	        * @return Map<String,Object> 返回类型
	        * @throws
	        * @author LiXing
	 */
	@RequestMapping("/pltfDiamondsDetail")
	public Map<String, Object> pltfDiamondsDetail(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		Map<String, Object> rtMap = this.assetService.pltfDiamondsDetail(requestBodyParams);
		return ResultUtils.rtSuccess(rtMap);
	}
}
