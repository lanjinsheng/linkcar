package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 
 */

@FeignClient(value = "service-sms", fallback = ChezuSmsHystric.class)
public interface ChezuSmsService {

	/**
	 * 
	 * @Title: sendMessage
	 * @Description: TODO(发送短信)
	 * @param map里放 mobile templateType validateCode
	 * @param @param
	 *            mobile
	 * @param @param
	 *            templateType			注册：1        登入：2        找回密码：3
	 * @param @param
	 *            validateCode
	 * @param @return
	 *            参数
	 * @return boolean
	 * 
	 * @throws @author
	 *             Lixing
	 */

	@RequestMapping("/sendMessage")
	public boolean sendMessage(@RequestParam Map<String, String> map);
	@RequestMapping("/sendMessage/test")
	public boolean sendMessageTest(); 
}
