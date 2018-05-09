package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 心跳检测的消息类型
 */
public class RebootMsg extends BaseMsg {
    public RebootMsg() {
        super();
        setType(MsgType.REBOOT);
    }
    private String rtJson;
	public String getRtJson() {
		return rtJson;
	}
	public void setRtJson(String rtJson) {
		this.rtJson = rtJson;
	}
}
