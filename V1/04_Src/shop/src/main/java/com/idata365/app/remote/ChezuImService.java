package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.bean.AuctionBean;

/**
 * 
 * @ClassName: ChezuImService
 * @Description: TODO()
 * @author LanYeYe
 * @date 2018年06月13日
 *
 */
@FeignClient(value = "service-im-chezu-ljs", fallback = ChezuImHystric.class)
public interface ChezuImService {
	@RequestMapping(value = "/im/notifyAuction",method = RequestMethod.POST)
	public Map<String,Object>  notifyAuction(@RequestBody  AuctionBean auctionBean,@RequestParam(value="auctionPerson") String auctionPerson,@RequestParam(value="auctionTimes")  String auctionTimes);
	
}
