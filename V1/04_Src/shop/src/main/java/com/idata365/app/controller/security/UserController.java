package com.idata365.app.controller.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.idata365.app.entity.bean.UserInfo;
import com.idata365.app.remote.ChezuAssetService;
import com.idata365.app.util.ResultUtils;
import com.idata365.app.util.SignUtils;

@RestController
public class UserController extends BaseController {

	@Autowired
	ChezuAssetService chezuAssetService;

	@RequestMapping("/getUserInfo")
	public Map<String, Object> getUserInfo(@RequestParam(required = false) Map<String, String> allRequestParams,
			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
		long userId = this.getUserId();
		UserInfo userInfo = this.getUserInfo();
		String nickName = userInfo.getNickName();
		String sign = SignUtils.encryptHMAC(String.valueOf(userId));
		Map<String, Object> map = chezuAssetService.getUserAsset(userId, sign);
		Double diamondsNum = (Double) map.get("diamondsNum");
		map.clear();
		map.put("nickName", nickName);
		map.put("diamondsNum", diamondsNum);
		return ResultUtils.rtSuccess(map);
	}
}
