package com.yao.module;

/**
 * Created by yaozb on 15-4-11.
 * 登录验证类型的消息
 */
public class ConfigMsg extends BaseMsg {
    private String version;
    private String reqTime;

	private String status;
    private String token;
    private String dataIp;
    private String serverTime;
    private String errorMsg;
    private String sign;
    
    public String getSuccessMsg() {
    	String succ="{\"status\":\"200\",\"token\":\""+this.token+"\",\"dataIp\":\""+this.dataIp+"\",\"serverTime\":\""+this.getServerTime()+"\"}";
		return succ;
	}
    public String getFailMsg() {
    	String fail="{\"status\":\"201\",\"errorMsg\":\""+this.getErrorMsg()+"\",\"serverTime\":\""+this.getServerTime()+"\"}";
		return fail;
	}
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getReqTime() {
		return reqTime;
	}

	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}
		public String getVersion() {
			return version;
		}
	
		public void setVersion(String version) {
			this.version = version;
		}
	    public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getDataIp() {
			return dataIp;
		}

		public void setDataIp(String dataIp) {
			this.dataIp = dataIp;
		}

		public String getServerTime() {
			return serverTime;
		}

		public void setServerTime(String serverTime) {
			this.serverTime = serverTime;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		public ConfigMsg() {
	        super();
	        setType(MsgType.CONFIG);
	    }

}
