package com.idata365.app.remote;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
    * @ClassName: DemoService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月29日
    *
 */
@FeignClient(value = "service-col-chezu",fallback = ChezuColHystric.class)
public interface ChezuColService {
    @RequestMapping(value = "/v1/updateUserDevice",method = RequestMethod.POST)
    boolean updateUserDevice(@RequestParam  Map<String,Object>  map);
}
