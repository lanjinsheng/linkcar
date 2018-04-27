package com.idata365.app.remote;


import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.UsersAccount;

/**
 * 
    * @ClassName: ChezuColService
    * @Description: TODO(远程调用更新userId 与 deviceToken)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
@FeignClient(value = "service-chezu",fallback = ChezuHystric.class)
public interface ChezuService {
   /**
    * 远程进行用户账户验证，待远程接口写入
    * @param token
    * @return
    */
   @RequestMapping(value = "/Account/validToken",method = RequestMethod.POST)
   UsersAccount  validToken(@RequestParam  String  token);
   

   
}
