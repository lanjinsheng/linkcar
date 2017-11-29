package com.idata365.col.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.idata365.col.api.SSOTools;
import com.idata365.col.entity.DriveDataLog;
import com.idata365.col.entity.SensorDataLog;
import com.idata365.col.service.DataService;
import com.idata365.col.util.DateTools;
import com.idata365.col.util.GsonUtils;
import com.idata365.col.util.ResultUtils;
import com.idata365.col.util.SignUtils;
import com.idata365.col.util.StaticDatas;
import com.idata365.col.util.ValidTools;


@RestController
public class BssCollectDataController extends BaseController<BssCollectDataController> {
	private final static Logger LOG = LoggerFactory.getLogger(BssCollectDataController.class);
    @Autowired
    DataService dataService;
    /**
     * 
        * @Title: uploadDriveData
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param file
        * @param @param map
        * @param @return
        * @param @throws IOException    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/v1/uploadDriveData",method = RequestMethod.POST)
    public Map<String,Object>  uploadDriveData(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) throws IOException {
    	 long  startTime=System.currentTimeMillis();
  	   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
  	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String identificationJson=request.getHeader("identification");
        String sign=request.getHeader("sign");
        LOG.info("identification="+identificationJson);
        LOG.info("sign="+sign);
        if(ValidTools.isBlank(identificationJson) || ValidTools.isBlank(sign)) {
      	  return ResultUtils.rtFailParam(null);
        }
        if(!SignUtils.security(identificationJson,sign)) {
      	  return ResultUtils.rtFailVerification(null);
        }
        Map<String,Object> identificationM=GsonUtils.fromJson(identificationJson);
        long userId=Long.valueOf(identificationM.get("userId").toString());
        long habitId=Long.valueOf(identificationM.get("habitId").toString());
        int isEnd=Integer.valueOf(identificationM.get("isEnd").toString());
        int seq=Integer.valueOf(identificationM.get("seq").toString());
        int hadSensorData=Integer.valueOf(identificationM.get("hadSensorData").toString());
        String equipmentInfo=String.valueOf(identificationM.get("equipmentInfo"));
        String YYYYMMDD=DateTools.getYYYYMMDD();
        String filePath=userId+"/"+YYYYMMDD+"/A"+System.currentTimeMillis();
        LOG.info("fileOrgName:"+file.getOriginalFilename()+"==now name:"+filePath);
        try {
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            SSOTools.saveOSS(is,filePath);
            is.close();
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtils.rtFail(null); 
        }
        DriveDataLog log=new DriveDataLog();
        log.setCreateTime(new Date());
        log.setFilePath(filePath);
        log.setUserId(userId);
        log.setHabitId(habitId);
        log.setIsEnd(isEnd);
        log.setSeq(seq);
        log.setEquipmentInfo(equipmentInfo);
        log.setHadSensorData(hadSensorData);
        dataService.insertDriveLog(log);
        long  endTime=System.currentTimeMillis();
        LOG.info("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return ResultUtils.rtSuccess(null); 
    } 
    /**
     * 
        * @Title: uploadDriveData
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param file
        * @param @param map
        * @param @return
        * @param @throws IOException    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/v1/uploadSensorData",method = RequestMethod.POST)
    public Map<String,Object>  uploadSensorData(@RequestParam CommonsMultipartFile file,@RequestParam Map<String,Object> map) throws IOException {
    	 long  startTime=System.currentTimeMillis();
  	   RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
  	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        String identificationJson=request.getHeader("identification");
        String sign=request.getHeader("sign");
        LOG.info("identification="+identificationJson);
        LOG.info("sign="+sign);
        if(ValidTools.isBlank(identificationJson) || ValidTools.isBlank(sign)) {
      	  return ResultUtils.rtFailParam(null);
        }
        if(!SignUtils.security(identificationJson,sign)) {
      	  return ResultUtils.rtFailVerification(null);
        }
        Map<String,Object> identificationM=GsonUtils.fromJson(identificationJson);
        long userId=Long.valueOf(identificationM.get("userId").toString());
        long habitId=Long.valueOf(identificationM.get("habitId").toString());
        int isEnd=Integer.valueOf(identificationM.get("isEnd").toString());
        int seq=Integer.valueOf(identificationM.get("seq").toString());
        String equipmentInfo=String.valueOf(identificationM.get("equipmentInfo"));
        String YYYYMMDD=DateTools.getYYYYMMDD();
        String filePath=userId+"/"+YYYYMMDD+"/B"+System.currentTimeMillis();
        LOG.info("fileOrgName:"+file.getOriginalFilename()+"==now name:"+filePath);
        try {
            //获取输入流 CommonsMultipartFile 中可以直接得到文件的流
            InputStream is=file.getInputStream();
            SSOTools.saveOSS(is,filePath);
            is.close();
        }catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return ResultUtils.rtFail(null); 
        }
        SensorDataLog log=new SensorDataLog();
        log.setCreateTime(new Date());
        log.setFilePath(filePath);
        log.setUserId(userId);
        log.setHabitId(habitId);
        log.setIsEnd(isEnd);
        log.setSeq(seq);
        log.setEquipmentInfo(equipmentInfo);
        dataService.insertSensorLog(log);
        long  endTime=System.currentTimeMillis();
        LOG.info("方法一的运行时间："+String.valueOf(endTime-startTime)+"ms");
        return ResultUtils.rtSuccess(null); 
    } 
    /**
     * 
        * @Title: uploadDriveData
        * @Description: TODO(这里用一句话描述这个方法的作用)
        * @param @param file
        * @param @param map
        * @param @return
        * @param @throws IOException    参数
        * @return Map<String,Object>    返回类型
        * @throws
        * @author LanYeYe
     */
    @RequestMapping(value = "/v1/config",method = { RequestMethod.POST,RequestMethod.GET})
    public Map<String,Object>  config(@RequestParam (required = false) Map<String, String> allRequestParams,@RequestBody  (required = false)  Map<Object, Object> requestBodyParams) {
          //获取RequestAttributes
    	  RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    	  HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
          String identificationJson=request.getHeader("identification");
          String sign=request.getHeader("sign");
          LOG.info("identification="+identificationJson);
          LOG.info("sign="+sign);
          if(ValidTools.isBlank(identificationJson) || ValidTools.isBlank(sign)) {
        	  return ResultUtils.rtFailParam(null);
          }
          if(!SignUtils.security(identificationJson,sign)) {
        	  return ResultUtils.rtFailVerification(null);
          }
          return ResultUtils.rtSuccess(StaticDatas.UserConfigDefault);
    } 
    
}