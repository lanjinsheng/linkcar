package com.idata365.app.remote;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @ClassName: ChezuImService
 * @Description: TODO()
 * @author lcc
 * @date 2018年6月14日
 *
 */
@FeignClient(value = "service-im-chezu", fallback = ChezuImHystric.class)
public interface ChezuImService {

 
	@RequestMapping(value = "/im/notifyFamilyChange",method = RequestMethod.POST)
	public boolean notifyFamilyChange(@RequestBody List<Map<String,Object>> list,@RequestParam(value="sign") String sign);

	@RequestMapping(value = "/im/prayingRealize",method = RequestMethod.POST)
	public boolean prayingRealize(@RequestParam(value="fromUserName") String fromUserName,
			@RequestParam(value="toUserName") String toUserName,
			@RequestParam(value="toUserId") String toUserId,
			@RequestParam(value="propName") String propName,
			@RequestParam(value="sign") String sign);

	@RequestMapping(value = "/im/prayingSubmit",method = RequestMethod.POST)
	public boolean prayingSubmit(@RequestParam(value="fromUserName") String fromUserName,
			@RequestParam(value="toUserName") String toUserName,
			@RequestParam(value="toUserId") String toUserId,
			@RequestParam(value="propName") String propName,
			@RequestParam(value="sign") String sign);
	
	@RequestMapping(value = "/im/lookedAllAd",method = RequestMethod.POST)
	public boolean lookedAllAd(@RequestParam(value="userName") String userName,
			@RequestParam(value="userId") String userId,
			@RequestParam(value="sign") String sign);
	
	@RequestMapping(value = "/im/doingAllActMission",method = RequestMethod.POST)
	public boolean doingAllActMission(@RequestParam(value="userName") String userName,
			@RequestParam(value="userId") String userId,
			@RequestParam(value="sign") String sign);
}
