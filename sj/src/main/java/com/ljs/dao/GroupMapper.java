package com.ljs.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface GroupMapper {
	public List<Map<String,String>> getDepartList();
	public List<Map<String,String>> getRootDepartList(Map<String,String> map);
	public List<String> getDeptChildren(Map<String,Object> map);
	public Map<String,Object> getDepartNodeById(String id);
	public int getIdLastIndex();
	public void addDepartNode(Map<String,String> map);
	public void updateDepartNode(Map<String,String> map);
	public void updateDepartTreeCode(Map<String,Object> map);
	public void deleteDepartNode(List<String> list);
	public List<Map<String,String>> listPagePriv(Map<String,Object> map);
	public void addPriv(Map<String,Object> map);
	public void deletePriv(List<String> list);
	public void updatePriv(Map<String,Object> map);
	public void addDataPriv(List<Map<String,Object>> list);
	public List<Map<String,Object>> getDataPriv(Map<String,Object> map);
	public void deleteDataPriv(Map<String,Object> map);
	public Map<String,Object> getPrivById(String id);
	public List<Map<String,Object>> getSysMenuList(Map<String,Object> map);
	public void updatePrivMenu(Map<String,Object> map);
	public List<Map<String,Object>> listPageUser(Map<String,Object> map);
	public List<Map<String,Object>> listPriv();
	public Map<String,Object> getUserByUserName(Map<String,Object> map);
	public void addUser(Map<String,Object> map);
	public void updateUser(Map<String,Object> map);
	public void addUserDept(Map<String,Object> map);
	public void deleteUserDept(Map<String,Object> map);
	public void addUserPriv(Map<String,Object> map);
	public void deleteUserPriv(Map<String,Object> map);
	public Map<String,Object> getUserById(Map<String,Object> map);
	public void deleteUser(List<String> list);
	public void deleteUser1(List<String> list);
	public List<Map<String,Object>> getSystemTypeTree(Map<String,Object> map);
	public List<Map<String,Object>> getSystemTypeBox(Map<String,Object> map);
	public List<Map<String,Object>> getSystemType();
	public Map<String,Object> getSystemTypeById(Map<String,Object> map);
	public Map<String,Object> getBusinessView(Map<String,String> map);
	public void addSystemType(Map<String,Object> map);
	public void updateSystemType(Map<String,Object> map);
	public void deleteSystemType(Map<String,Object> map);
	public List<String> getUsersByPrivName(@Param("priv_name")String priv_name);
}
