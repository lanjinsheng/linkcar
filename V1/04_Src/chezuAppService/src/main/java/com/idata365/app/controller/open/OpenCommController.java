package com.idata365.app.controller.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.TaskService;
import com.idata365.app.util.SignUtils;

@RestController
public class OpenCommController {
	protected static final Logger LOG = LoggerFactory.getLogger(MessageOpenController.class);
	@Autowired
	TaskService taskService;
	
	@RequestMapping(value = "/app/updateLoginBss",method = RequestMethod.POST)
	public boolean  updateLoginBss(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign)

	{
		LOG.info("userId="+userId+"===sign="+sign);
		LOG.info("valid sign="+SignUtils.encryptHMAC(String.valueOf(userId)));
		taskService.updateUserScoreDayByUserId(userId);
		return true;
	}
}
