package com.idata365.app.remote;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
 * @ClassName: ChezuImService
 * @Description: TODO(获取用户证件认证情况)
 * @author lcc
 * @date 2018年6月14日
 *
 */
@FeignClient(value = "service-im-chezu", fallback = ChezuImHystric.class)
public interface ChezuImService {

 
	@RequestMapping(value = "/im/notifyFamilyChange",method = RequestMethod.POST)
	public boolean notifyFamilyChange(@RequestParam(value="userId") long userId,@RequestParam(value="sign") String sign);
}
