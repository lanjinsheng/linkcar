package com.idata365.col.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.directory.api.util.Unicode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.col.api.QQSSOTools;
import com.idata365.col.config.SystemProperties;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.UserEntity;
import com.idata365.col.enums.UserSexEnum;
import com.idata365.col.remote.ChezuDriveService;
import com.idata365.col.remote.DemoService;
import com.idata365.col.service.DataService;
import com.idata365.col.util.ResultUtils;
import com.idata365.col.util.UnicodeUtil;


@RestController
public class DemoController {
	private final static Logger LOG = LoggerFactory.getLogger(BssGetDataController.class);
    @Autowired
    DemoService demoService;
    @Autowired
    DataService dataService;
    @Autowired
    ChezuDriveService chezuDriveService;
    @Autowired
	SystemProperties systemProperties;
    @RequestMapping(value = "/testDbEception",method = RequestMethod.GET)
    public String testDbEception(){
    	DriveDataLog log=new DriveDataLog();
    	log.setEquipmentInfo("");
    	log.setFilePath("17/20171211/A1_1512998450660");
    	log.setHabitId(1513689569L);
    	log.setUserId(17L);
    	log.setSeq(1);
    	log.setIsEnd(1);
    	dataService.insertDriveLog(log, "ewrwrwere");
    	return "aaa";
    }
      
    
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
    
    @RequestMapping(value = "/headerTest",method = RequestMethod.POST)
    public Map<String,Object>  headerTest(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestHeader(value="equipmentInfo") String equipmentInfo){
//    	return name;
   	   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
   	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
         LOG.info("equipmentInfo="+UnicodeUtil.unicodeDecode(equipmentInfo));
         return ResultUtils.rtFailParam(null);
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
    

    @RequestMapping("/fileUpload")
    public String  fileUpload(@RequestParam CommonsMultipartFile file) throws IOException {
         
         
        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());
      
        try {
            //获取输出流
            OutputStream os=new FileOutputStream("/usr/local/"+System.currentTimeMillis()+file.getOriginalFilename());
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

    @RequestMapping("/fileUploadSSO")
    public String  fileUploadSSO(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) throws IOException {
         
         
        //用来检测程序运行时间
        long  startTime=System.currentTimeMillis();
        System.out.println("fileName："+file.getOriginalFilename());
      
        try {
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            File   dealFile = new File(systemProperties.getFileTmpDir()+"/"+System.currentTimeMillis());
            file.transferTo(dealFile);
            QQSSOTools.saveOSS(dealFile,"1000/20171123/"+System.currentTimeMillis());
            is.close();
         
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long  endTime=System.currentTimeMillis();
        System.out.println("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return "/success"; 
    } 
   public static void main(String []args) {
	File file=new File("d:\\aa\\bb\\cc\\dd\\ddfd22.jgp");  
	File fileParent = file.getParentFile();  
	if(!fileParent.exists()){  
	    fileParent.mkdirs();  
	} 
	try {
		file.createNewFile();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
    
}