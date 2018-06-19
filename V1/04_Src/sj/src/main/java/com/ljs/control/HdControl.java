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
import org.springframework.web.servlet.ModelAndView;

import com.ljs.service.DataService;
 


/**
 * ClassName: HdControl
 * @Description: TODO
 * @author 兰锦生
 * @date 2016-3-17
 */
@Controller
@RequestMapping("/hd")
public class HdControl extends BaseControl{
	private static Logger log = Logger.getLogger(HdControl.class);
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
	@RequestMapping(value = "/",  method = {RequestMethod.POST, RequestMethod.GET})
	public  ModelAndView index(HttpServletRequest request){
		return new ModelAndView("redirect:http://www.baidu.com");
	}
	
}
