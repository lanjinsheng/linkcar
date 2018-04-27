package com.idata365.app.remote;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.UsersAccount;
import com.idata365.app.entity.bean.UserInfo;

/**
 * 
    * @ClassName: ChezuService
    * @Description: TODO(远程调用更新userId 与 deviceToken)
    * @author LanYeYe
    * @date 2017年12月28日
    *
 */
@FeignClient(value = "service-account-chezu",fallback = ChezuHystric.class)
public interface ChezuService {
   /**
    * 远程进行用户账户验证，待远程接口写入
    * @param token
    * @return
    */
   @RequestMapping(value = "/account/validToken",method = RequestMethod.POST)
   UsersAccount  validToken(@RequestParam(value="token")  String  token);
   
   /**
    * 远程进行用户账户验证，待远程接口写入
    * @param token
    * @return
    */
   @RequestMapping(value = "/account/getUserInfoByToken",method = RequestMethod.POST)
   UserInfo  getUserInfoByToken(@RequestParam(value="token")  String  token);
   
}
