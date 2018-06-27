package com.idata365.app.remote;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "service-app-chezu", fallback = ChezuAppHystric.class)
public interface ChezuAppService {
	@RequestMapping(value = "/app/getFamiliesByUserId",method = RequestMethod.POST)
	public String  getFamiliesByUserId(@RequestParam(value="userId") Long userId,@RequestParam(value="sign") String sign);
	@RequestMapping("/app/getFamilyUsers")
	public Map<String, Object> familyUsers(@RequestParam(value="userId") Long userId,@RequestParam(value="sign") String sign);
    @RequestMapping("/app/msg/sendImMsg")
    public boolean sendImMsg(
    		@RequestParam (value = "familyId") Long familyId,
    		@RequestParam (value = "familyType") Integer familyType,
    		@RequestParam (value = "fromUserId") Long fromUserId,
    		@RequestParam (value = "toUserId") Long toUserId,
    		@RequestParam (value = "msg") String msg,
    		@RequestParam (value = "sign") String sign);
}
