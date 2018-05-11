package com.idata365.remote;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.entity.bean.AssetFamiliesPowerLogs;
import com.idata365.entity.bean.AssetUsersPowerLogs;
import com.idata365.entity.bean.FamilyGameAsset;


@FeignClient(value = "service-asset-chezu",fallback = ChezuAssetHystric.class)
public interface ChezuAssetService {
    @RequestMapping(value = "/asset/addPowerUsersTask",method = RequestMethod.POST)
    boolean addPowerUsersTask(@RequestParam(value="jsonValue")  String jsonValue,@RequestParam(value="sign")   String sign, @RequestBody AssetUsersPowerLogs assetUsersPowerLogs);
    @RequestMapping(value = "/asset/addPowerFamilyTask",method = RequestMethod.POST)
    boolean addPowerFamilyTask(@RequestParam(value="jsonValue")  String jsonValue,@RequestParam(value="sign")   String sign, @RequestBody   AssetFamiliesPowerLogs assetFamiliesPowerLogs);
    /**
     * 
        * @Title: addFamilyGameOrder
        * @Description: TODO(对order进行sign)
        * @param @param sign
        * @param @param assetFamiliesPowerLogs
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/asset/addFamilyGameOrder",method = RequestMethod.POST)
    boolean addFamilyGameOrder(@RequestParam(value="sign")   String sign, @RequestBody   FamilyGameAsset familyGameAsset);
    /**
     * 
        * @Title: addFamilyGameOrderEnd
        * @Description: TODO(对season进行sign)
        * @param @param sign
        * @param @param season
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/asset/addFamilyGameOrderEnd",method = RequestMethod.POST)
    boolean addFamilyGameOrderEnd(@RequestParam(value="season")   String season,@RequestParam(value="sign")   String sign);

}
