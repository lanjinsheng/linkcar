package com.idata365.app.controller.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.UserAchieveService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.ValidTools;

/**
 * 个人成就模块
 * 
 * @className:com.idata365.app.controller.security.UserAchieveController
 * @description:TODO
 * @date:2018年1月19日 上午10:55:18
 * @author:CaiFengYao
 */
@RestController
public class UserAchieveController extends BaseController
{

	@Autowired
	private UserAchieveService userAchieveService;

	/**
	 * 查看用户成就列表
	 * 
	 * @author:CaiFengYao
	 * @date:2018年1月19日 下午2:37:16
	 */
	@RequestMapping("/achieve/getUserAchieveList")
	public Map<String, Object> getUserAchieveList()
	{
		Map<String, Object> rtMap = new HashMap<String, Object>();
		// 业务处理
		List<Map<String, Object>> list = userAchieveService.getUserAchieveList(this.getUserId());
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String imgBasePath = super.getImgBasePath();
		for (Map<String, Object> m : list)
		{
			String imgUrl = m.get("imgUrl").toString();
			m.put("imgUrl", imgBasePath + imgUrl);
			resultList.add(m);
		}
		this.dealListObect2String(resultList);
		rtMap.put("getUserAchieveList", resultList);
		return ResultUtils.rtSuccess(rtMap);
	}

	/**
	 * 查看用户某项成就具体内容
	 * 
	 * @Description:
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 * @author:CaiFengYao
	 * @date:2018年1月19日 下午5:27:48
	 */
	@RequestMapping("/achieve/getAchieveListById")
	public Map<String, Object> getAchieveListById(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<String, Object> requestBodyParams)
	{
		if (requestBodyParams == null || ValidTools.isBlank(requestBodyParams.get("achieveId")))
			return ResultUtils.rtFailParam(null);
		Map<String, Object> rtMap = new HashMap<String, Object>();
		int achieveId = Integer.valueOf(requestBodyParams.get("achieveId").toString());
		// 业务处理
		List<Map<String, Object>> list = userAchieveService.getAchieveListById(this.getUserId(), achieveId);
		this.dealListObect2String(list);
		rtMap.put("getAchieveListById", list);
		return ResultUtils.rtSuccess(rtMap);
	}
}
