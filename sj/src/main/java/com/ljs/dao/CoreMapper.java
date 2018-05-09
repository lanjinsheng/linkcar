package com.ljs.dao;


import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


@Repository
public interface CoreMapper {
	public Map<String,Object> getUserByUserName(Map<String,Object> map);
	public List<Map<String,Object>> getMenus();
	public List<Map<String,Object>> getShowMenu(Map<String,Object> map);
	public List<Map<String,Object>> getMenuByType(Map<String,Object> map);
	public void addMenu(Map<String,Object> map);
	public void updateMenu(Map<String,Object> map);
	public Map<String,Object> getUserPriv(Map<String,Object> map);
	public List<Map<String,Object>> getShowGd(Map<String,Object> map);
	public List<Map<String,Object>> getShowXm(Map<String,Object> map);
	public int hasPriv(Map<String,Object> map);
	public Map<String,Object> getMenuById(String seq_id);
	public List<String> getChildren(String id);
	public void deleteMenu(List<String> list);
	public int getMenuIdLastIndex();
}
