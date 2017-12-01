package com.idata365.col.remote;


import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
    * @ClassName: DemoService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月29日
    *
 */
@FeignClient(value = "service-chezu",fallback = ChezuServiceHystric.class)
public interface ChezuDriveService {
    @RequestMapping(value = "/recieveDrive",method = RequestMethod.POST)
    boolean recieveDrive(@RequestBody (required = false) List<Map<String,Object>> postList);
}
