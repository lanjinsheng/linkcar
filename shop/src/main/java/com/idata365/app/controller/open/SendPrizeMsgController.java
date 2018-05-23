package com.idata365.app.controller.open;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.remote.ChezuAppService;
import com.idata365.app.util.SignUtils;

/**
 * @ClassName: SendPrizeMsgController
 * @Description: TODO()
 * @author LiXing
 * @date 2018年5月9日
 *
 */

@RestController
public class SendPrizeMsgController extends BaseController {

	@Autowired
	private ChezuAppService chezuAppService;

	@RequestMapping(value = "/hello/sendPrizeMsg", method = RequestMethod.POST)
	boolean sendPrizeMsg(@RequestParam(value = "userId") long userId,
			@RequestParam(value = "prizeName") String prizeName, @RequestParam(value = "sign") String sign) {
		LOG.info("PARAM:" + userId + "===" + prizeName + "====" + sign);

		String relSign = SignUtils.encryptHMAC(userId + "" + prizeName);
		boolean flag = false;
		if (relSign.equals(sign)) {
			flag = chezuAppService.sendGoodsSendMsg(userId, prizeName, sign);
		}
		return flag;
	}

//	@RequestMapping("/hello/sendPrizeMsg")
//	public Map<String, Object> sendPrizeMsg(@RequestParam(required = false) Map<String, String> allRequestParams,
//			@RequestBody(required = false) Map<Object, Object> requestBodyParams) {
//		long userId = this.getUserId();
//		String prizeName;
//		if (ValidTools.isNotBlank(allRequestParams)) {
//			prizeName = allRequestParams.get("prizeName");
//		} else if (ValidTools.isNotBlank(requestBodyParams)) {
//			prizeName = requestBodyParams.get("prizeName");
//		} else {
//			prizeName = null;
//		}
//
//		String sign = SignUtils.encryptHMAC(userId + "" + prizeName);
//
//		boolean flag = chezuAppService.sendGoodsSendMsg(userId, prizeName, sign);
//		return ResultUtils.rtSuccess(flag);
//	}

}
