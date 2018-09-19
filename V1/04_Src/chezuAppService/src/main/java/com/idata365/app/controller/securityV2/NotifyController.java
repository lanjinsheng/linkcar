package com.idata365.app.controller.securityV2;

import java.util.HashMap;
import java.util.Map;

import com.idata365.app.constant.DicLivenessConstant;
import com.idata365.app.serviceV2.LivenessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.DicService;
import com.idata365.app.serviceV2.UserMissionService;
import com.idata365.app.util.ResultUtils;

@RestController
public class NotifyController extends BaseController {
    private static long lastNotifyTime = 0;
    private static String notifyText = "";
    protected static final Logger LOG = LoggerFactory.getLogger(NotifyController.class);
    @Autowired
    DicService dicService;
    @Autowired
    UserMissionService userMissionService;
    @Autowired
    LivenessService livenessService;

    @RequestMapping("/indexNotice")
    public Map<String, Object> indexNotice(@RequestParam(required = false) Map<String, String> allRequestParams,
                                           @RequestBody(required = false) Map<Object, Object> requestBodyParams) {
        long now = System.currentTimeMillis();
        long userId = this.getUserId();
        LOG.info("userId==============" + userId);
        if ((now - lastNotifyTime) > (3600 * 1000)) {//一个小时重新去库获取
            notifyText = dicService.getNotify();
            lastNotifyTime = now;
        }
        Map<String, String> rtMap = new HashMap<String, String>();
        rtMap.put("notice", notifyText);

        //登录加入活跃值
        livenessService.insertUserLivenessLog(userId, DicLivenessConstant.livenessId1);

        return ResultUtils.rtSuccess(rtMap);
    }


}
