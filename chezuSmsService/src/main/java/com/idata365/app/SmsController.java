package com.idata365.app;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

@RestController
public class SmsController {

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
	 *            Map mobile---string---用户手机 templateType---string 注册：1 登入：2 找回密码：3
	 *            ---消息模板类型 validateCode---string---验证码
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
			SendSmsResponse response = smsService.sendSms(map.get("mobile"), map.get("templateType"),
					map.get("validateCode"));
			System.out.println("code:" + response.getCode());
			System.out.println("message:" + response.getMessage());
			
			
			return true;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	@RequestMapping("/sendMessage/test")
	public boolean sendMessageTest() {
		try {
			SendSmsResponse response = smsService.sendSms("19802310491", "3", "1241413");
			System.out.println("code:" + response.getCode());
			System.out.println("message:" + response.getMessage());
			return true;
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
