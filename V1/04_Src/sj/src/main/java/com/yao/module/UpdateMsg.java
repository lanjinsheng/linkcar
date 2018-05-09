package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 心跳检测的消息类型
 */
public class UpdateMsg extends BaseMsg {
    public UpdateMsg() {
        super();
        setType(MsgType.UPDATE);
    }
    private String rtJson;
	public String getRtJson() {
		return rtJson;
	}
	public void setRtJson(String rtJson) {
		this.rtJson = rtJson;
	}
}
