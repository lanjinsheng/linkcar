package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 心跳检测的消息类型
 */
public class MacsFileMsg extends BaseMsg {
	 private String status;
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	 public String getSuccessMsg() {
	    	String succ="{\"status\":\"200\",\"errorMsg\":\""+this.getMsg()+"\"}";
			return succ;
		}
	    public String getFailMsg() {
	    	String fail="{\"status\":\"201\",\"errorMsg\":\""+this.getMsg()+"\"}";
			return fail;
		}
	public MacsFileMsg() {
        super();
        setType(MsgType.MACS);
    }
}
