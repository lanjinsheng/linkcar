package com.idata365.app.remote;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
    * @ClassName: ChezuColService
    * @Description: TODO(远程调用更新userId 与 deviceToken)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
@FeignClient(value = "service-chezu",fallback = ChezuColHystric.class)
public interface ChezuColService {
    @RequestMapping(value = "/v1/updateUserDevice",method = RequestMethod.POST)
    boolean updateUserDevice(@RequestParam  Map<String,Object>  map);
}
