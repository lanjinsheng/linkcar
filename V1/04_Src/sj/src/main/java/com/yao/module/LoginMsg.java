package com.yao.module;

/**
 * Created by ljs on 15-4-11.
 * 登录验证类型的消息
 */
public class LoginMsg extends BaseMsg {
    
    private String token;
    private String reqTime;
    private String status;
    private String sign;
	private String version;
    private String ip;
    private String mac;
    public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}



    

	public LoginMsg() {
        super();
        setType(MsgType.LOGIN);
    }
	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getReqTime() {
		return reqTime;
	}


	public void setReqTime(String reqTime) {
		this.reqTime = reqTime;
	}


	public String  getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}
  
}
