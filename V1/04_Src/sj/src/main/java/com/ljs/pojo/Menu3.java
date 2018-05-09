package com.ljs.pojo;

/**
 * 3级菜单
 * @author Administrator
 *
 */
public class Menu3 {
	private String id;
	private String text;
	private String href;
	private boolean closeable;
	
	public boolean isCloseable() {
		return closeable;
	}
	public void setCloseable(boolean closeable) {
		this.closeable = closeable;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
}
