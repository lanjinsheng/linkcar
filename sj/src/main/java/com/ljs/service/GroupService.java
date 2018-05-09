package com.ljs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ljs.dao.GroupMapper;
import com.ljs.util.Utility;


@Transactional(rollbackFor = Exception.class)
@Service("groupService")
public class GroupService {
	
	@Autowired
	private GroupMapper groupMapper;
	
	public String listDepartment(){
		StringBuffer sb = new StringBuffer("[");
		List<Map<String,String>> rootList = groupMapper.getDepartList();
		if(rootList != null && rootList.size() > 0){
			for(int i=0;i<rootList.size();i++){
				Map<String,String> m = rootList.get(i);
				if(i != 0 && i!= rootList.size()){
					sb.append(",");
				}
				sb.append("{\"id\":\"").append(String.valueOf(m.get("id")))
				.append("\",\"text\":\"").append(m.get("name"))
				.append("\",\"attributes\":"+Utility.toJson(m))
				.append(",\"children\":[");
				m.put("parent_id", String.valueOf(m.get("id")));
				sb.append(getChildrenDepartSys(m)).append("]}");
			}
		}
		sb.append("]");
		return sb.toString();
	}
	private String getChildrenDepartSys(Map<String,String> map){
		StringBuffer sb = new StringBuffer();
		List<Map<String,String>> nodeList = groupMapper.getRootDepartList(map);
		if(nodeList != null && nodeList.size() >0){
			for(int i=0;i<nodeList.size();i++){
				if(i != 0 && i!= nodeList.size()){
					sb.append(",");
				}
				Map<String,String> m = nodeList.get(i);
				sb.append("{\"id\":\"").append(String.valueOf(m.get("id")))
				.append("\",\"text\":\"").append(m.get("name"))
				.append("\",\"attributes\":"+Utility.toJson(m))
				.append(",\"children\":[");
				map.put("parent_id", String.valueOf(m.get("id")));
				sb.append(getChildrenDepartSys(map)).append("]}");
			}
			return sb.toString();
		}
		else{
			return "";
		}
	}
	
	public String addDepartNode(Map<String, String> map) {
		int lastId = groupMapper.getIdLastIndex();
		
		Map<String,Object> parentMap = groupMapper.getDepartNodeById(map.get("parent_id"));
		map.put("tree_code", String.valueOf(parentMap.get("tree_code"))+","+lastId);
		groupMapper.addDepartNode(map);
		return Utility.rtJson(Utility.RETURN_OK, "添加成功", null);
	}
	public String updateDepartNode(Map<String, String> map) {
		//同时更新本节点以及下级子节点的treecode
    	Map<String,Object> parentMap = groupMapper.getDepartNodeById(map.get("parent_id"));
		map.put("tree_code", String.valueOf(parentMap.get("tree_code"))+","+String.valueOf(map.get("id")));
		groupMapper.updateDepartNode(map);
		updateDepartTreeCode(map.get("id"),map.get("tree_code"));
		return Utility.rtJson(Utility.RETURN_OK, "修改成功", null);
	}
	private void updateDepartTreeCode(String parent_id,String parent_tree_code){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("parent_id", parent_id);
		List<String> childList = groupMapper.getDeptChildren(map);
		if(childList != null && childList.size() > 0){
			for(int i=0;i<childList.size();i++){
				Map<String,Object> m = new HashMap<String,Object>();
				String id = childList.get(i);
				m.put("id", id);
				m.put("tree_code", parent_tree_code+","+id);
				groupMapper.updateDepartTreeCode(m);
				updateDepartTreeCode(id,String.valueOf(m.get("tree_code")));
			}
		}
	}
	public String deleteDepartNode(List<String> list){
		groupMapper.deleteDepartNode(list);
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String listPagePriv(Map<String,Object> map){
		List<Map<String,String>> privList= groupMapper.listPagePriv(map);
		StringBuffer sb = new StringBuffer("");
		sb.append(Utility.toJson(privList));
		Utility.putSuccess(map);
		return sb.toString();
	}
	public String addPriv(Map<String,Object> map){	
		groupMapper.addPriv(map);
		return Utility.rtJson(Utility.RETURN_OK, "添加成功", null);
	}
	public String deletePriv(Map<String,String> map){
		List<String> list = Arrays.asList(map.get("ids").split(","));
		groupMapper.deletePriv(list); 
		return Utility.rtJson(Utility.RETURN_OK, "删除成功", null);
	}
	public String updatePriv(Map<String,Object> map){
		groupMapper.updatePriv(map);
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String getPrivMenuTree(String priv){
		StringBuffer sb = new StringBuffer("[");
		Map<String,Object> privMap = groupMapper.getPrivById(priv);
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("parent_id", "0");
		sb.append(getMySysMenuTotalTree(queryMap,privMap));
		sb.append("]");
		return sb.toString();
	}
	public String initDataPriv(Map<String,Object> map){
		List<Map<String,Object>> list = groupMapper.getDataPriv(map);
		if(list != null && list.size() > 0){
		}else{
			List<Map<String,Object>> l = new ArrayList<Map<String,Object>>();
			map.put("master_id", map.get("master_id"));
			map.put("slave_id", map.get("master_id"));
			l.add(map);
			groupMapper.addDataPriv(l);
		}
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String getDataPriv(Map<String,Object> map){
		return Utility.lstMaptoJson(groupMapper.getDataPriv(map));
	}
	public String updateDataPriv(Map<String,Object> map){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String[] slaves = String.valueOf(map.get("slaves")).split(";");
		for(String slave:slaves){
			Map<String,Object> m = new HashMap<String,Object>();
			m.put("master_id", map.get("master"));
			m.put("slave_id", slave);
			list.add(m);
		}
		groupMapper.deleteDataPriv(map);
		groupMapper.addDataPriv(list);
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String getMySysMenuTotalTree(Map<String,Object> map,Map<String,Object> privMap){
		StringBuffer sb = new StringBuffer();
		List<Map<String,Object>> list = groupMapper.getSysMenuList(map);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map<String,Object> m = list.get(i);
				if(i > 0){
					sb.append(",");
				}
				sb.append("{\"id\":\"").append(m.get("seq_id"));
				sb.append("\",\"text\":\"").append(m.get("text"));
				
				map.put("parent_id", m.get("seq_id"));
				if("0".equals(m.get("parent_id"))){
					List<Map<String,Object>> l = groupMapper.getSysMenuList(map);
					if(l != null && l.size() > 0){
						sb.append("\",\"state\":\"closed");
					}
				}
				if(hasPriv(String.valueOf(m.get("seq_id")), privMap)){
					sb.append("\",\"checked\":\"true");
				}
				sb.append("\",\"attributes\":"+Utility.toJson(m));
				
				sb.append(",\"children\":[")
				.append(getMySysMenuTotalTree(map,privMap)).append("]}");
			}
			return sb.toString();
		}else{
			return "";
		}
	}
	private boolean hasPriv(String id,Map<String,Object> privMap){
		String menu =  privMap.get("menu")==null?"":String.valueOf(privMap.get("menu"));
		List<String> ids = Arrays.asList(menu.split(","));
		if(ids!=null && ids.size()>0){
			for(int j=0;j<ids.size();j++){
				if(id.equals(ids.get(j))){
					return true;
				}
			}
		}
		return false;
	}
	public String updatePrivMenu(Map<String,Object> map){
		groupMapper.updatePrivMenu(map);
		return Utility.rtJson(Utility.RETURN_OK, "修改成功", null);
	}
	public String listPageUser(Map<String,Object> map){
		List<Map<String,Object>> users= groupMapper.listPageUser(map);
		StringBuffer sb = new StringBuffer("");
		sb.append(Utility.lstMaptoJson(users));
		Utility.putSuccess(map);
		return sb.toString();
	}
	public String listPriv(){
		List<Map<String,Object>> list = groupMapper.listPriv();
		return Utility.lstMaptoJson(list);
	}
	public String getUserById(Map<String,Object> map){
		return Utility.toJson(groupMapper.getUserById(map));
	}
	
	
	public String addUser(Map<String,Object> map){
		groupMapper.addUser(map);
		map.put("user_id", String.valueOf(map.get("id")));
		groupMapper.addUserDept(map);
	 	groupMapper.addUserPriv(map);
	    //添加自己的数据权限
	    map.put("master_id", map.get("user_id"));
	    initDataPriv(map);
		return Utility.rtJson(Utility.RETURN_OK, "添加成功", null);
	}
	public String updateUser(Map<String,Object> map){
		Map<String,Object> m = groupMapper.getUserByUserName(map);
		//相同用户名不同id，表示本次修改的用户名已经存在
		if(m != null){
			if(!String.valueOf(m.get("id")).equals(String.valueOf(map.get("id")))){
				return Utility.rtJson(Utility.RETURN_ERROR, "该用户名称已存在", null);
			}
		}
		groupMapper.updateUser(map);
		groupMapper.deleteUserDept(map);
		groupMapper.deleteUserPriv(map);
		map.put("user_id", map.get("id"));
		groupMapper.addUserDept(map);
		groupMapper.addUserPriv(map);
		return Utility.rtJson(Utility.RETURN_OK, "更新成功", null);
	}
	public String deleteUser(Map<String,String> map){
		List<String> list = Arrays.asList(map.get("ids").split(","));
		groupMapper.deleteUser(list); 
		return Utility.rtJson(Utility.RETURN_OK, "删除成功", null);
	}
	public String deleteUser1(Map<String,String> map){
		List<String> list = Arrays.asList(map.get("ids").split(","));
		groupMapper.deleteUser1(list);
		return Utility.rtJson(Utility.RETURN_OK, "删除成功", null);
	}
}
