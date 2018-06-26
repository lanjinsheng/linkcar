package com.idata365.app.controller.open;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.controller.security.BaseController;
import com.idata365.app.service.FightService;
import com.idata365.app.util.DateTools;
@RestController
public class FightOpenController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(FightOpenController.class);
	@Autowired
    FightService fightService;
	
	
	/**
	 * 根据ID获取Realtion的Map
	 * @param allRequestParams
	 * @param requestBodyParams
	 * @return
	 */
	@RequestMapping("/app/getFightRelationAsset")
	public Map<String, Object> getFightRelationAsset(@RequestParam (value = "familyId") Long familyId,
    		@RequestParam (value = "sign") String sign) {
		LOG.info("familyId=================" + familyId);
		return fightService.getFightRelationAsset(familyId, DateTools.getYYYY_MM_DD());
	}
}
