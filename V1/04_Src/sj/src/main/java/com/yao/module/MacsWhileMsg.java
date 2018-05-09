package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 心跳检测的消息类型
 */
public class MacsWhileMsg extends BaseMsg {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public MacsWhileMsg() {
        super();
        setType(MsgType.MACWHILELIST);
    }
    private String last4;
	public String getLast4() {
		return last4;
	}
	public void setLast4(String last4) {
		this.last4 = last4;
	}
}
