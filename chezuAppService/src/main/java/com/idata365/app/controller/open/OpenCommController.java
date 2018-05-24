package com.idata365.app.controller.open;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.TaskService;
import com.idata365.app.serviceV2.GameServiceV2;
import com.idata365.app.util.SignUtils;

@RestController
public class OpenCommController {
	protected static final Logger LOG = LoggerFactory.getLogger(MessageOpenController.class);
	@Autowired
	TaskService taskService;
	@Autowired
	GameServiceV2 gameService;

	@RequestMapping(value = "/app/updateLoginBss", method = RequestMethod.POST)
	public boolean updateLoginBss(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "sign") String sign)

	{
		LOG.info("userId=" + userId + "===sign=" + sign);
		LOG.info("valid sign=" + SignUtils.encryptHMAC(String.valueOf(userId)));
		taskService.updateUserScoreDayByUserId(userId);
		taskService.updateLastLoginTimeByUserId(userId);
		return true;
	}

	@RequestMapping(value = "/app/updateHadNewPower", method = RequestMethod.POST)
	public void updateHadNewPower(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "familyId") long familyId, @RequestParam(value = "sign") String sign) {
		LOG.info("userId=" + userId + "===sign=" + sign);
		LOG.info("familyId=" + familyId + "===sign=" + sign);
		LOG.info("valid sign=" + SignUtils.encryptHMAC(String.valueOf(userId)));
		
		gameService.updateHavaNewPower(userId,familyId);
	}

}
