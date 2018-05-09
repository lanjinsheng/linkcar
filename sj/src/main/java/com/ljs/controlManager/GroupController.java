package com.ljs.controlManager;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ljs.control.BaseControl;
import com.ljs.service.GroupService;





/**
 * 负责系统管理
 * @author 徐凡
 *
 */
@Controller
@RequestMapping("/manage/group")
public class GroupController extends BaseControl{
	@Autowired 
	private GroupService groupService; 
	
    
    @RequestMapping(value = "/listDepartment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String listDepartment(HttpServletRequest request) {
    	return groupService.listDepartment();
    }

    @RequestMapping(value = "/addDepartNode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String addDepartNode(HttpServletRequest request) {
    	String name = request.getParameter("name");
    	String sort = request.getParameter("sort");
    	String parent_id = request.getParameter("parent_id");
    	String parent_name = request.getParameter("parent_name");
    	Map<String,String> childNodeMap = new HashMap<String,String>();
    	
    	childNodeMap.put("name", name); 
    	childNodeMap.put("sort", sort); 
    	childNodeMap.put("parent_id", parent_id);
    	childNodeMap.put("parent_name",parent_name);
    	return groupService.addDepartNode(childNodeMap);
    }
    
    @RequestMapping(value = "/updateDepartNode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updateDepartNode(HttpServletRequest request) {
    	
    	String id = request.getParameter("id");
    	String name = request.getParameter("name");
    	String sort = request.getParameter("sort");
    	String parent_id = request.getParameter("parent_id");
    	String parent_name = request.getParameter("parent_name");
    	Map<String,String> childNodeMap = new HashMap<String,String>();
    	
    	childNodeMap.put("id", id); 
    	childNodeMap.put("name", name); 
    	childNodeMap.put("sort", sort); 
    	childNodeMap.put("parent_id", parent_id);
    	childNodeMap.put("parent_name",parent_name);
    	return groupService.updateDepartNode(childNodeMap);
    }
    
    @RequestMapping(value = "/deleteDepartNode", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String deleteDepartNode(HttpServletRequest request) {
    	String ids = request.getParameter("ids");
    	List<String> list = Arrays.asList(ids.split(","));
    	return groupService.deleteDepartNode(list);
    }
    @RequestMapping(value = "/listPagePriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String listPagePriv(HttpServletRequest request) {
    	Map<String,Object> map = getPagerMap(request);
    	return groupService.listPagePriv(map);
    }
    @RequestMapping(value = "/addPriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String addPriv(HttpServletRequest request) {
    	Map<String, Object> map  = requestParameterToMap(request);
    	return groupService.addPriv(map);
    }
    @RequestMapping(value = "/deletePriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String deletePriv(HttpServletRequest request) {
    	Map<String,String> m = new HashMap<String,String>();
    	m.put("ids", request.getParameter("ids"));
    	return groupService.deletePriv(m);
    }
    @RequestMapping(value = "/updatePriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updatePriv(HttpServletRequest request) {
    	Map<String, Object> map  = requestParameterToMap(request);
    	return groupService.updatePriv(map);
    }
    @RequestMapping(value = "/getPrivMenuTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getPrivMenuTree(HttpServletRequest request) {
    	String priv_id = request.getParameter("priv_id");
    	return groupService.getPrivMenuTree(priv_id);
    }
    @RequestMapping(value = "/initDataPriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String initDataPriv(HttpServletRequest request) {
    	return groupService.initDataPriv(requestParameterToMap(request));
    }
    @RequestMapping(value = "/getDataPriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getDataPriv(HttpServletRequest request) {
    	return groupService.getDataPriv(requestParameterToMap(request));
    }
    @RequestMapping(value = "/updateDataPriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updateDataPriv(HttpServletRequest request) {
    	return groupService.updateDataPriv(requestParameterToMap(request));
    }
    @RequestMapping(value = "/updatePrivMenu", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updatePrivSysMenu(HttpServletRequest request) {
    	String priv_id = request.getParameter("priv_id");
    	String ids = request.getParameter("treeId");
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("id", priv_id);
    	map.put("menu", ids);
    	return groupService.updatePrivMenu(map);
    }
    @RequestMapping(value = "/listPageUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String listPageUser(HttpServletRequest request) {
    	Map<String,Object> map =  getPagerMap(request);
    	map.putAll(requestParameterToMap(request));
    	return groupService.listPageUser(map);
    }
    @RequestMapping(value = "/listPriv", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String listPriv(HttpServletRequest request) {
    	return groupService.listPriv();
    }
    @RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String addUser(HttpServletRequest request) {
    	Map<String,Object> map = requestParameterToMap(request);
    	return groupService.addUser(map);
    }
    @RequestMapping(value = "/updateUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String updateUser(HttpServletRequest request) {
    	Map<String,Object> map = requestParameterToMap(request);
    	return groupService.updateUser(map);
    }
    @RequestMapping(value = "/getUserById", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public @ResponseBody String getUserById(HttpServletRequest request) {
    	Map<String,Object> map = requestParameterToMap(request);
    	return groupService.getUserById(map);
    }
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String deleteUser(HttpServletRequest request) {
    	Map<String,String> m = new HashMap<String,String>();
    	m.put("ids", request.getParameter("ids"));
    	return groupService.deleteUser(m);
    }
    /**
     * 伪删除
     * @param request
     * @return
     */
    @RequestMapping(value = "/deleteUser1", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public @ResponseBody String deleteUser1(HttpServletRequest request) {
    	Map<String,String> m = new HashMap<String,String>();
    	m.put("ids", request.getParameter("ids"));
    	return groupService.deleteUser1(m);
    }
}
