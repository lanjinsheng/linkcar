package com.ljs.controlManager;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljs.control.BaseControl;
import com.ljs.service.CoreService;


/**
 * 菜单管理
 * @author 徐凡
 *
 */
@Controller
@RequestMapping("/manage/core")
public class CoreController extends BaseControl{
	@Autowired 
	private CoreService coreService; 
    @RequestMapping(value = "/getShowMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getShowMenu(HttpServletRequest request) {
    	return coreService.getShowMenu(request,getSessionUser(request));
    }
    
    /**
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenu1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getMenu1(HttpServletRequest request) {
    	return coreService.getMenu1(getSessionUser(request));
    }
    
    /**
     * 
     * 系统管理处 获取所有菜单treegrid
     * @param request
     * @return
     */
    @RequestMapping(value = "/getMenus", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getMenus(HttpServletRequest request) {
    	return coreService.getMenus();
    }
    @RequestMapping(value = "/addMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String addMenu(HttpServletRequest request) {
    	return coreService.addMenu(requestParameterToMap(request));
    }
    @RequestMapping(value = "/deleteMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String deleteMenu(HttpServletRequest request) {
    	return coreService.deleteMenu(request.getParameter("id"));
    }
    @RequestMapping(value = "/getMenu", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getMenu(HttpServletRequest request) {
    	return coreService.getMenu(request.getParameter("id"));
    }
    @RequestMapping(value = "/updateMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updateMenu(HttpServletRequest request) {
    	return coreService.updateMenu(requestParameterToMap(request));
    }
}
