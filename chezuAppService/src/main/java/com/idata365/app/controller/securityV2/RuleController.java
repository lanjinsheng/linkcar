package com.idata365.app.controller.securityV2;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.v2.DicLiveness;
import com.idata365.app.service.DicService;
import com.idata365.app.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RuleController extends BaseController {
    List<Map<String, String>> result = new ArrayList<>();
    protected static final Logger LOG = LoggerFactory.getLogger(RuleController.class);
    @Autowired
    DicService dicService;

    @RequestMapping("/getRules")
    public Map<String, Object> getRules(@RequestParam(required = false) Map<String, String> allRequestParams,
                                        @RequestBody(required = false) Map<Object, Object> requestBodyParams) {
        long userId = this.getUserId();
        int ruleType = Integer.valueOf(requestBodyParams.get("ruleType").toString());
        LOG.info("userId==============" + userId);
        result = dicService.getRulesByType(ruleType);
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("rules", result);
        rtMap.put("ruleName", result.get(0).get("ruleName"));
        return ResultUtils.rtSuccess(rtMap);
    }

    @RequestMapping("/getLivenessRules")
    public Map<String, Object> getLivenessRules(@RequestParam(required = false) Map<String, String> allRequestParams,
                                                @RequestBody(required = false) Map<Object, Object> requestBodyParams) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        List<DicLiveness> liveness = dicService.getLiveness();
        List<Map<String, String>> rules = new ArrayList<>();
        for (DicLiveness dicLiveness : liveness) {
            Map<String, String> map = new HashMap<>();
            map.put("livenessName", dicLiveness.getLivenessName());
            map.put("livenessValue", String.valueOf(dicLiveness.getLivenessValue()));
            rules.add(map);
        }
        rtMap.put("rules", rules);
        return ResultUtils.rtSuccess(rtMap);
    }

}
