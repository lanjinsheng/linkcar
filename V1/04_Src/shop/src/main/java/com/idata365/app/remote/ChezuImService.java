package com.idata365.app.remote;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.entity.AuctionGoods;
import com.idata365.app.entity.AuctionLogs;
import com.idata365.app.entity.bean.AuctionBean;

/**
 * 
 * @ClassName: ChezuImService
 * @Description: TODO()
 * @author LanYeYe
 * @date 2017年12月28日
 *
 */
@FeignClient(value = "service-im-chezu", fallback = ChezuImHystric.class)
public interface ChezuImService {
	@RequestMapping(value = "/im/notifyAuction",method = RequestMethod.POST)
	public Map<String,Object>  notifyAuction(@RequestBody  AuctionBean auctionBean);
	
}
