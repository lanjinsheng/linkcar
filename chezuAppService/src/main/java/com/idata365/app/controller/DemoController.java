package com.idata365.app.controller;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.app.entity.UserEntity;
import com.idata365.app.entity.VerifyCode;
import com.idata365.app.enums.UserSexEnum;
import com.idata365.app.remote.DemoService;
import com.idata365.app.service.LoginRegService;


@RestController
public class DemoController {

    @Autowired
    DemoService demoService;
    @Autowired
    LoginRegService loginRegService;
    @RequestMapping(value = "/hi",method = RequestMethod.GET)
    public String sayHi(@RequestParam String name){
//    	return name;
        return demoService.sayHiFromClientOne(name);
    }
    @RequestMapping(value = "/getDemoUser")
    public String getUser(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
//    	return name;
    	System.out.println(allRequestParams==null?"null":allRequestParams.size());
    	System.out.println(requestBodyParams==null?"null":requestBodyParams.size());
        return demoService.getUsers();
    }
    @RequestMapping(value = "/insertUserRemote")
    public String insertUserRemote(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
//    	return name;
    	System.out.println(allRequestParams==null?"null":allRequestParams.size());
    	System.out.println(requestBodyParams==null?"null":requestBodyParams.size());
    	UserEntity user=new UserEntity();
    	user.setNickName("兰爷爷");
    	user.setPassWord("abcd");
    	user.setUserName("mogelylan");
    	user.setUserSex(UserSexEnum.WOMAN);
         demoService.save(user);
         return String.valueOf(user.getId());
    }
    @RequestMapping(value = "/account/getVerifyCodeTest")
    public List<VerifyCode> getVerifyCodeTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
//    	return name;
 
         return loginRegService.getVerifyCodeTest();
    }
    
    @RequestMapping(value = "/insertUserRemoteMap")
    public String insertUserRemoteMap(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams){
//    	return name;
    	System.out.println(allRequestParams==null?"null":allRequestParams.size());
    	System.out.println(requestBodyParams==null?"null":requestBodyParams.size());
    	Map<Object,Object> user=new HashMap<Object,Object>();
    	user.put("nickName","兰爷爷2");
    	user.put("passWord","abcd3");
    	user.put("userName","mogelylan");
    	user.put("userSex",UserSexEnum.WOMAN);
         demoService.saveByMap(user);
         return String.valueOf("ok");
    }
    

    @RequestMapping("fileUpload")
    public String  fileUpload(@RequestParam CommonsMultipartFile file) throws IOException {
         
         
        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());
         
        try {
            //获取输出流
            OutputStream os=new FileOutputStream("D:/"+System.currentTimeMillis()+file.getOriginalFilename());
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            int temp;
            //一个一个字节的读取并写入
            while((temp=is.read())!=(-1))
            {
                os.write(temp);
            }
           os.flush();
           os.close();
           is.close();
         
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "/success"; 
    }
}