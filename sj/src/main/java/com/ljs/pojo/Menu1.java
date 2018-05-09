package com.ljs.pojo;

import java.util.List;


/**
 * 一级菜单
 * @author Administrator
 *
 */
public class Menu1 {
	private String id;
	private String homePage;
	private boolean collapsed;
	private List<Menu2> menu;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHomePage() {
		return homePage;
	}
	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}
	public boolean isCollapsed() {
		return collapsed;
	}
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	public List<Menu2> getMenu() {
		return menu;
	}
	public void setMenu(List<Menu2> menu) {
		this.menu = menu;
	}
}
