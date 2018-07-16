package com.idata365.app.partnerApi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idata365.app.remote.ChezuSmsService;
import com.idata365.app.service.SpringContextUtil;
import com.idata365.app.util.SignUtils;

@Component
public class PhoneMsgTools {
	public static int EventType1 = 1;// 注册
	public static int EventType2 = 2;// 登入
	public static int EventType3 = 3;// 找回密码
	public static int EventType4 = 4;// 邀请
	@Autowired
	ChezuSmsService chezuSmsService;

	public static boolean sendCodeMsg(String mobile, int templateType, String validateCode) {
		// 发送短信验证码
		PhoneMsgTools phoneMsgTools = SpringContextUtil.getBean("phoneMsgTools", PhoneMsgTools.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("templateType", String.valueOf(templateType));
		map.put("validateCode", validateCode);
		map.put("smsSignName", "链车");
		String sign=SignUtils.encryptHMAC(mobile+validateCode);
		map.put("sign", sign);
		boolean flag = phoneMsgTools.chezuSmsService.sendMessage(map);
//		boolean flag = phoneMsgTools.chezuSmsService.sendMessageTest();
		return flag;
	}
}
