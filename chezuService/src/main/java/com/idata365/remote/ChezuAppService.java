package com.idata365.remote;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(value = "service-app-chezu", fallback = ChezuAppHystric.class)
public interface ChezuAppService {
    @RequestMapping(value = "/v1/getUserLotter", method = RequestMethod.POST)
    boolean getUserLotter(@RequestParam Map<String, Object> map);

    @RequestMapping(value = "/app/insertUserLivenessLog", method = RequestMethod.POST)
    boolean insertUserLivenessLog(@RequestParam(value = "userId") long userId, @RequestParam(value = "livenessId") int livenessId,
                                  @RequestParam(value = "sign") String sign);

}
