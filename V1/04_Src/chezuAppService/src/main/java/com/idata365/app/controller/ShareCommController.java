package com.idata365.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.idata365.app.service.LoginRegService;
import com.idata365.app.util.SignUtils;


@Controller
public class ShareCommController{
	private final static Logger LOG = LoggerFactory.getLogger(ShareCommController.class);
	@Autowired
	public ShareCommController() {
	}

 
    @RequestMapping("/share/goInvite")
    public String goInvite(@RequestParam (required = false) Map<String, String> allRequestParams,Map<String, Object> model){
    	String content=allRequestParams.get("key");
//    	if(content==null) {
//    		return "error";
//    	}
    	try {
//    	String key=SignUtils.decryptDataAes(content);
//    	String []arrayString = key.split(":");
//    	Long familyId=Long.valueOf(arrayString[0]);
//    	Long createTimeLong=Long.valueOf(arrayString[1]);
//    	Long now=System.currentTimeMillis()-(3600*1000);//一天过期
//    	if(now>createTimeLong) {
//    		LOG.info("过期的数据 key："+key);
//    		return "error";
//    	}
//    	//跳转到加盟页面
//    	
//    	String datas=familyId+":"+System.currentTimeMillis();

//			String sign=SignUtils.encryptDataAes(datas);
    	String sign=SignUtils.encryptDataAes("23r23234234");
			model.put("sign", sign);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "error";
		}
    	return "invite1";
    }
     
}