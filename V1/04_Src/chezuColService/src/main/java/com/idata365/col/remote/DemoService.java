package com.idata365.col.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.col.entity.UserEntity;

/**
 * 
    * @ClassName: DemoService
    * @Description: TODO(这里用一句话描述这个类的作用)
    * @author LanYeYe
    * @date 2017年11月29日
    *
 */
@FeignClient(value = "service-chezu",fallback = DemoServiceHystric.class)
public interface DemoService {
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    String sayHiFromClientOne(@RequestParam(value = "hi") String name);
    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    String getUsers();
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    void save(@RequestBody UserEntity user);
    @RequestMapping(value = "/addMap",method = RequestMethod.POST)
    String saveByMap(@RequestBody Map<Object,Object> user);
    
    
}
