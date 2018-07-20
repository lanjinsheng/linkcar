package com.idata365.app;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.idata365.utils.SignUtils;

@RestController
public class SmsController {
	private final static Logger LOG = LoggerFactory.getLogger(SmsController.class);
	@Autowired
	private SmsService smsService;

	public SmsController() {
		System.out.println("this is SmsController~~~~~~");
	}

	/**
	 * 
	 * @Title: sendMessage
	 * @Description: TODO(发送短信)
	 * @param @param
	 *            Map mobile---string---用户手机 
	 *            templateType---string   注册：1            登入：2            找回密码：3     邀请：4        ---消息模板类型 
	 *            validateCode---string---短信验证码
	 * @param @param
	 * 
	 * @param @return
	 *            参数
	 * @return boolean 返回类型
	 * @throws @author
	 *             Lixing
	 */

	@RequestMapping("/sendMessage")
	public boolean sendMessage(@RequestParam Map<String, String> map) {
		try {
			LOG.info("mobile:" + map.get("mobile"));
			LOG.info("type:" + map.get("templateType"));
			LOG.info("sign:" + map.get("sign"));
			String sign=map.get("sign");
			boolean validSign=SignUtils.security(map.get("mobile")+map.get("validateCode"), sign);
			if(!validSign) {
				LOG.error("短信验证失败"+map.get("mobile")+"==="+map.get("validateCode"));
				return false;
			}
			SendSmsResponse response = smsService.sendSms(map.get("mobile"),
					map.get("templateType"), map.get("smsSignName"),
					map.get("validateCode"));
			LOG.info("code:" + response.getCode());
			LOG.info("message:" + response.getMessage());
			return true;
		} catch (ClientException e) {
			LOG.error(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

//	@RequestMapping("/sendMessage/test")
//	public boolean sendMessageTest() {
//		try {
//			SendSmsResponse response = smsService.sendSms("19802310491", "3", "1241413");
//			System.out.println("code:" + response.getCode());
//			System.out.println("message:" + response.getMessage());
//			return true;
//		} catch (ClientException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return false;
//		}
//	}
}
