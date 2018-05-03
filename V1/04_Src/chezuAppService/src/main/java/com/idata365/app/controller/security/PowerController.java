package com.idata365.app.controller.security;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.entity.TaskPowerLogs;
import com.idata365.app.enums.PowerEnum;
import com.idata365.app.service.PowerService;
import com.idata365.app.util.DateTools;
import com.idata365.app.util.ResultUtils;

@RestController
public class PowerController extends BaseController
{
	protected static final Logger LOG = LoggerFactory.getLogger(PowerController.class);
	
	@Autowired
	private PowerService powerService;
	
	/**
	 * 
	    * @Title: submitSignInPower
	    * @Description: TODO(签到接口)
	    * @param @param allRequestParams
	    * @param @param requestBodyParams
	    * @param @return    参数
	    * @return Map<String,Object>    返回类型
	    * @throws
	    * @author LanYeYe
	 */
	 
	@RequestMapping("/submitSignInPower")
	public Map<String, Object> submitSignInPower(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams)
	{
		TaskPowerLogs taskPowerLogs=new TaskPowerLogs();
		taskPowerLogs.setUserId(this.getUserId());
		taskPowerLogs.setTaskType(PowerEnum.SignIn);
		taskPowerLogs.setCreateTime(DateTools.getYYYY_MM_DD());
		String jsonValue="{\"userId\":\"%d\",\"power\":\"5\"}";
		taskPowerLogs.setJsonValue(String.format(jsonValue, taskPowerLogs.getUserId()));
		if(powerService.hadSignInToday(taskPowerLogs)) {
	    	return ResultUtils.rtFailParam(null, "当日已经签到过");
		}else {
			powerService.addPowerLogs(taskPowerLogs);
			return ResultUtils.rtSuccess(null);
		}
	
	}
	public	 static void main(String []args) {
		Long userId=32342l;
		String powerValue="{\"userId\":\"%d\",\"power\":\"5\"}";
		System.out.println(String.format(powerValue, userId));
	}
}
