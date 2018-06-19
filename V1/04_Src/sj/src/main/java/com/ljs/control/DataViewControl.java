/**  
 * @Title: PhoneDataViewControl.java
 * @Package com.chebao.phonewebapi.controller
 * @Description: TODO
 * @author 兰锦生
 * @date 2016-3-17
 */
package com.ljs.control;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljs.service.DataService;
import com.ljs.util.ServerUtil;
 


/**
 * ClassName: PhoneDataViewControl
 * @Description: TODO
 * @author 兰锦生
 * @date 2016-3-17
 */
@Controller
@RequestMapping("/manage/view")
public class DataViewControl extends BaseControl{
	private static Logger log = Logger.getLogger(DataViewControl.class);
	@Autowired
	DataService dataService;

	
	/**
	 * @Description: 网络开关list
	 * @param @param request   
	 * @return String  
	 * @throws
	 * @author 兰锦生
	 * @date 2016-5-9
	 */
	@RequestMapping(value = "/netOperaList",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String listPageNetOpera(HttpServletRequest request){
		Map<String, Object> map=this.getPagerMap(request);
    	map.putAll(requestParameterToMap(request));
    	return dataService.listPageNetOpera(map);
	}
	@RequestMapping(value = "/listPageUrl",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String listPageUrl(HttpServletRequest request){
		Map<String, Object> map=this.getPagerMap(request);
    	map.putAll(requestParameterToMap(request));
    	return dataService.listPageUrl(map);
	}
	@RequestMapping(value = "/insertOrUpdateUrl",  method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json;charset=UTF-8")
	public @ResponseBody String insertOrUpdateUrl(HttpServletRequest request){
		Map<String, Object> map=this.requestParameterToMap(request);
		if(map.get("id")==null || map.get("id").equals("null") || map.get("id").equals("")){
			dataService.insertUrl(map);
		}else{
	        dataService.updateUrl(map);	
		}

    	return ServerUtil.rtJsonSuccess(null);
	}
}
