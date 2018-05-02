package com.idata365.remote;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "service-app-chezu",fallback = ChezuAppHystric.class)
public interface ChezuAppService {
    @RequestMapping(value = "/v1/getUserLotter",method = RequestMethod.POST)
    boolean getUserLotter(@RequestParam  Map<String,Object>  map);
}
