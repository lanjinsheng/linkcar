package com.idata365.col.remote;


import org.springframework.cloud.netflix.feign.FeignClient;

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
//    @RequestMapping(value = "/yingyan/addPoint",method = RequestMethod.POST)
//    Map<String,Object> addPoint(@RequestBody  (required = false) Map<String,String> point) ;
//   @RequestMapping(value = "/yingyan/addPointList",method = RequestMethod.POST)
//    Map<String,Object> addPointList(@RequestBody  (required = false) List<Map<String,String>> pointList);
//   @RequestMapping(value = "/yingyan/analysis",method = RequestMethod.POST)
//    Map<String,Object> analysis(@RequestBody  (required = false) Map<String,String> param);
//    
//    
}
