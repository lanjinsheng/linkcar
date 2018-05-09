package com.ljs.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ljs.dao.CoreMapper;
import com.ljs.pojo.Menu1;
import com.ljs.pojo.Menu2;
import com.ljs.pojo.Menu3;
import com.ljs.util.Utility;





@Transactional(rollbackFor = Exception.class)
@Service("coreService")
public class CoreService {
	@Autowired
	private CoreMapper coreMapper;
	public String getShowMenu(HttpServletRequest request,Map<String,Object> map){
		Gson gson = new Gson();
		if(Utility.cache.get(String.valueOf(map.get("id"))) != null){
//			return gson.toJson(Utility.cache.get(String.valueOf(map.get("id")))); 
			Utility.cache.remove(String.valueOf(map.get("id")));
		}
		
		List<Menu1>	listMenu=reflashListMenu(request,map);
		return gson.toJson(listMenu);
	}
	public List<Menu1> reflashListMenu(HttpServletRequest request,Map<String,Object> map){
		List<Menu1> listMenu = new ArrayList<Menu1>();
		Map<String,Object> userPriv = coreMapper.getUserPriv(map);//当前用户的权限
		List<Map<String,Object>> list1 = getMenu1List(userPriv);
		if(list1 != null && list1.size()>0){
			for(int i=0;i<list1.size();i++){
				Map<String,Object> menu1Map = list1.get(i);
				Menu1 menu1 = new Menu1();
				menu1.setId(String.valueOf(menu1Map.get("id")));
				if(menu1Map.get("collapsed") == null){
					menu1.setCollapsed(false);
				}else{
					menu1.setCollapsed(Boolean.parseBoolean(String.valueOf(menu1Map.get("collapsed"))));
				}
				if(menu1Map.get("home_page") != null){
					menu1.setHomePage(String.valueOf(menu1Map.get("home_page")));
				}
				Map<String,Object> queryMap = new HashMap<String,Object>();
				queryMap.put("type", "2");
				queryMap.put("parent_id", menu1Map.get("seq_id"));
				List<Menu2> menu = new ArrayList<Menu2>();
				List<Map<String,Object>> list2 = coreMapper.getShowMenu(queryMap);
				if(list2 != null && list2.size()>0){
					for(int j=0;j<list2.size();j++){
						Map<String,Object> menu2Map = list2.get(j);
						//判断当前的二级菜单是不是符合用户的权限设置
						if(hasPriv(menu2Map.get("seq_id"), userPriv)){
							Menu2 menu2 = new Menu2();
							menu2.setText(String.valueOf(menu2Map.get("text")));
							if(menu2Map.get("collapsed") == null){
								menu2.setCollapsed(false);
							}else{
								menu2.setCollapsed(Boolean.parseBoolean(String.valueOf(menu2Map.get("collapsed"))));
							}
							queryMap.put("type", "3");
							queryMap.put("parent_id", menu2Map.get("seq_id"));
							List<Menu3> items = new ArrayList<Menu3>();
							List<Map<String,Object>> list3 = coreMapper.getShowMenu(queryMap);
							if(list3 !=null && list3.size() > 0){
								for(int k=0;k<list3.size();k++){
									Map<String,Object> menu3Map = list3.get(k);
									if(hasPriv(menu3Map.get("seq_id"), userPriv)){
										Menu3 menu3 = new Menu3();
										menu3.setId(String.valueOf(menu3Map.get("id")));
										menu3.setText(String.valueOf(menu3Map.get("text")));
										menu3.setHref(request.getContextPath()+String.valueOf(menu3Map.get("href")));
										if(menu3Map.get("closeable") == null){
											menu3.setCloseable(true);
										}else{
											menu3.setCloseable(Boolean.parseBoolean(String.valueOf(menu3Map.get("closeable"))));
										}
										items.add(menu3);
									}
								}
							}
							menu2.setItems(items);
							menu.add(menu2);
						}
					}
				}
				menu1.setMenu(menu);
				listMenu.add(menu1);
			}
		}
		Utility.cache.put(String.valueOf(map.get("id")),listMenu);
		return listMenu;
	}
	public String getMenu1(Map<String, Object> map){
		Map<String,Object> userPriv = coreMapper.getUserPriv(map);//当前用户的权限
		Gson gson = new Gson();
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", gson.toJson(getMenu1List(userPriv)));
	}
	
	/**
	 * 根据用户角色获取第一级菜单
	 * @param map
	 * @return
	 */
	private List<Map<String,Object>> getMenu1List(Map<String,Object> userPriv){
		List<Map<String,Object>> bakList = new ArrayList<Map<String,Object>>();
		Map<String,Object> queryMap = new HashMap<String,Object>();
		queryMap.put("type","1");
		List<Map<String,Object>> list = coreMapper.getMenuByType(queryMap);
		
		if(list != null && list.size() > 0 && userPriv != null){
			for(int i=0;i<list.size();i++){
				if(hasPriv(list.get(i).get("seq_id"), userPriv)){
					bakList.add(list.get(i));
				}
			}
		}
		return bakList;
	}
	private boolean hasPriv(Object menu_id,Map<String,Object> priv){
				String menu =  priv.get("menu")==null?"":String.valueOf(priv.get("menu"));
				List<String> ids = Arrays.asList(menu.split(","));
				if(ids!=null && ids.size()>0){
					for(int j=0;j<ids.size();j++){
						Map<String,Object> queryMap = new HashMap<String,Object>();
						queryMap.put("tree_code","'"+menu_id+"'");
						queryMap.put("seq_id",ids.get(j));
						if(coreMapper.hasPriv(queryMap) > 0){
							return true;
						}
					}
		}
		return false;
	}
	public String getMenus(){
		List<Map<String,Object>> list = coreMapper.getMenus();
		Gson gson = new Gson();
		StringBuffer sb = new StringBuffer("");
		sb.append("{\"total\":"+list.size()+",");
		sb.append("\"rows\":");
		sb.append(gson.toJson(list));
		sb.append("}");
		return sb.toString();
	}
	public String addMenu(Map<String,Object> map){
		int seq_id = coreMapper.getMenuIdLastIndex();
		seq_id=seq_id+1;
		if("1".equals(String.valueOf(map.get("type")))){
			map.put("tree_code", "'0','"+seq_id+"'");
		}else{
			Map<String,Object> parentMap = coreMapper.getMenuById(String.valueOf(map.get("parent_id")));
			if(parentMap != null){
				map.put("tree_code", parentMap.get("tree_code")+",'"+seq_id+"'");
			}
		}
		coreMapper.addMenu(map);
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String updateMenu(Map<String,Object> map){
		coreMapper.updateMenu(map);
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String deleteMenu(String id){
		List<String> ids = coreMapper.getChildren(id);
		coreMapper.deleteMenu(ids);
		return Utility.rtJson(Utility.RETURN_OK, "操作成功", null);
	}
	public String getMenu(String id){
		Map<String,Object> map =  coreMapper.getMenuById(id);
		if("false".equals(String.valueOf(map.get("collapsed")))){
			map.put("collapsed", "0");
		}else{
			map.put("collapsed", "1");
		}
		if("false".equals(String.valueOf(map.get("closeable")))){
			map.put("closeable", "0");
		}else{
			map.put("closeable", "1");
		}
		return Utility.toJson(map);
	}
}
