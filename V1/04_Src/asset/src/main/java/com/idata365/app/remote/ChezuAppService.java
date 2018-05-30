package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.UsersAccount;

/**
 * 
 * @ClassName: ChezuService
 * @Description: TODO(远程调用更新userId 与 deviceToken)
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-app-chezu", fallback = ChezuAppHystric.class)
public interface ChezuAppService {
	
    /**
     * 
        * @Title: sendFamilyDiamondsMsg
        * @Description: TODO(钻石分配通知)
        * @param @param season
        * @param @param familyId
        * @param @param orderNum
        * @param @param toUserId
        * @param @param diamondNum
        * @param @param sign
        * @param @return    参数
        * @return boolean    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping("/app/msg/sendFamilyDiamondsMsg")
    public boolean sendFamilyDiamondsMsg(@RequestParam (value = "season") String season,
    		@RequestParam (value = "familyId") String familyId,
    		@RequestParam (value = "orderNum") String orderNum,
    		@RequestParam (value = "toUserId") Long toUserId,
    		@RequestParam (value = "diamondNum") String diamondNum,
    		@RequestParam (value = "personDiamondNum") String personDiamondNum,
    		@RequestParam (value = "sign") String sign);
    
    @RequestMapping("/app/msg/sendFamilyDiamondsSeasonMsg")
    public boolean sendFamilyDiamondsSeasonMsg(@RequestParam (value = "season") String season,
    		@RequestParam (value = "familyId") String familyId,
    		@RequestParam (value = "familyType") Integer familyType,
    		@RequestParam (value = "toUserId") Long toUserId,
    		@RequestParam (value = "diamondNum") String diamondNum,
    		@RequestParam (value = "personDiamondNum") String personDiamondNum,
    		@RequestParam (value = "sign") String sign);
	
	@RequestMapping(value = "/app/updateLoginBss",method = RequestMethod.POST)
	public boolean  updateLoginBss(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign);

}
