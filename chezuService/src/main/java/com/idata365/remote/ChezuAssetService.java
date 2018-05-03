package com.idata365.remote;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.entity.bean.AssetFamiliesPowerLogs;
import com.idata365.entity.bean.AssetUsersPowerLogs;


@FeignClient(value = "service-asset-chezu",fallback = ChezuAssetHystric.class)
public interface ChezuAssetService {
    @RequestMapping(value = "/asset/addPowerUsersTask",method = RequestMethod.POST)
    boolean addPowerUsersTask(@RequestParam(value="jsonValue")  String jsonValue,@RequestParam(value="sign")   String sign, @RequestBody AssetUsersPowerLogs assetUsersPowerLogs);
    @RequestMapping(value = "/asset/addPowerFamilyTask",method = RequestMethod.POST)
    boolean addPowerFamilyTask(@RequestParam(value="jsonValue")  String jsonValue,@RequestParam(value="sign")   String sign, @RequestBody   AssetFamiliesPowerLogs assetFamiliesPowerLogs);


}
