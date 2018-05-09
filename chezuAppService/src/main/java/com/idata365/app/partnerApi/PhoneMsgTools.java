package com.idata365.app.partnerApi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.idata365.app.remote.ChezuSmsService;
import com.idata365.app.service.SpringContextUtil;

@Component
public class PhoneMsgTools {
	public static int EventType1 = 1;// 注册
	public static int EventType2 = 2;// 登入
	public static int EventType3 = 3;// 找回密码
	@Autowired
	ChezuSmsService chezuSmsService;

	public static boolean sendCodeMsg(String mobile, int templateType, String validateCode) {
		// 发送短信验证码
		PhoneMsgTools phoneMsgTools = SpringContextUtil.getBean("phoneMsgTools", PhoneMsgTools.class);
		Map<String, String> map = new HashMap<String, String>();
		map.put("mobile", mobile);
		map.put("templateType", String.valueOf(templateType));
		map.put("validateCode", validateCode);
		boolean flag = phoneMsgTools.chezuSmsService.sendMessage(map);
//		boolean flag = phoneMsgTools.chezuSmsService.sendMessageTest();
		return flag;
	}
}
