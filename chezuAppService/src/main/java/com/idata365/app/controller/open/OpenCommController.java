package com.idata365.app.controller.open;

import com.idata365.app.serviceV2.LivenessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.service.ScoreService;
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
    @Autowired
    ScoreService scoreService;
    @Autowired
    LivenessService livenessService;

    @RequestMapping(value = "/app/updateLoginBss", method = RequestMethod.POST)
    public boolean updateLoginBss(@RequestParam(value = "userId") long userId,
                                  @RequestParam(value = "sign") String sign){
        LOG.info("userId=" + userId + "===sign=" + sign);
        LOG.info("valid sign=" + SignUtils.encryptHMAC(String.valueOf(userId)));
        taskService.updateUserScoreDayByUserId(userId);
        taskService.updateLastLoginTimeByUserId(userId);
        return true;
    }
    //开车加入活跃值业务by Service
    @RequestMapping(value = "/app/insertUserLivenessLog", method = RequestMethod.POST)
    public boolean insertUserLivenessLog(@RequestParam(value = "userId") long userId,@RequestParam(value = "livenessId") int livenessId,
                                  @RequestParam(value = "sign") String sign){
        LOG.info("userId=" + userId + "===livenessId=" + livenessId);
        livenessService.insertUserLivenessLog(userId, livenessId);
        return true;
    }
}
