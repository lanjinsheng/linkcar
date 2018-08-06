package com.idata365.app.controller.securityV2;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.serviceV2.FamilyServiceV2;
import com.idata365.app.util.ResultUtils;

@RestController
public class FamilyControllerV2 extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FamilyControllerV2.class);
	@Autowired
	private FamilyServiceV2 familyServiceV2;

	/**
	 * 修改俱乐部资料
	 * 
	 * @param reqBean
	 * @return
	 */
	@RequestMapping("/family/updateFamily")
	public Map<String, Object> createFamily(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams) {
		LOG.info("userId==========="+this.getUserId());
		int status = this.familyServiceV2.updateFamily(requestBodyParams, this.getUserId());
		if (-1 == status) {
			return ResultUtils.rtFailParam(null, "俱乐部昵称已存在");
		}
		return ResultUtils.rtSuccess(null);
	}

}
