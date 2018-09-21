package com.idata365.app.controller.securityV2;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.TripServiceV2;
import com.idata365.app.util.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TripControllerV2 extends BaseController {
    protected static final Logger LOG = LoggerFactory.getLogger(TripControllerV2.class);

    @Autowired
    private TripServiceV2 tripServiceV2;

    /**
     * @param @param  allRequestParams
     * @param @param  requestBodyParams
     * @param @return 参数
     * @return Map<String                                                                                                                                                                                                                                                               ,                                                                                                                                                                                                                                                               Object> 返回类型
     * @throws @author lcc
     * @Title: getTodayAllTravel
     * @Description: TODO(这里用一句话描述这个方法的作用)
     */
    @RequestMapping("/v2/getTodayAllTravel")
    public Map<String, Object> getTodayAllTravel(@RequestParam(required = false) Map<String, String> allRequestParams,
                                                 @RequestBody(required = false) Map<Object, Object> requestBodyParams) {
        LOG.info("userId=============" + this.getUserId());
        Map<String, Object> rtMap = tripServiceV2.getTodayAllTravel(this.getUserId());
        return ResultUtils.rtSuccess(rtMap);
    }

}
