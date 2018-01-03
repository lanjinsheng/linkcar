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
@FeignClient(value = "service-chezu",fallback = ChezuHystric.class)
public interface ChezuService {
    @RequestMapping(value = "/v1/updateUserDevice",method = RequestMethod.POST)
    boolean updateUserDevice(@RequestParam  Map<String,Object>  map);
    
    /**
     * 
        * @Title: getGpsByUH
        * @Description: TODO(sign是通过SignUtils.encryptHMAC(userId+habitId)所得)
        * @param  map 里放 userId  habitId  sign
        * @param @param userId
        * @param @param habitId
        * @param @param sign
        * @param @return    参数
        * @return Map<String,Object>    返回类型
        * 	  Map<String,Object> datas=new HashMap<String,Object>();
	    *	  datas.put("gpsInfo", list);
	    *	  datas.put("alarmInfo", events);
        * @throws
        * @author LanYeYe
     */
   @RequestMapping(value = "/drive/getGpsByUH",method = RequestMethod.POST)
   Map<String,Object> getGpsByUH(@RequestParam  Map<String,Object>  map);
}
