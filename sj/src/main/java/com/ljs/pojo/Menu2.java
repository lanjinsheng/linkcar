package com.ljs.pojo;

import java.util.List;

/**
 * 二级菜单
 * @author Administrator
 *
 */
public class Menu2 {
	private String text;
	private boolean collapsed;
	private List<Menu3> items;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isCollapsed() {
		return collapsed;
	}
	public void setCollapsed(boolean collapsed) {
		this.collapsed = collapsed;
	}
	public List<Menu3> getItems() {
		return items;
	}
	public void setItems(List<Menu3> items) {
		this.items = items;
	}
}
