package com.idata365.app.aop;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.idata365.app.util.ResultUtils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@ControllerAdvice
public class ExceptionHandle {

  private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

  /**
   * 
   * @param e
   * @return
   */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
  public Map<String,Object> exceptionGet(Exception e){
//	  e.printStackTrace();
      LOGGER.error("【系统异常】{}",e);
      return ResultUtils.rtFail(null);
  }
}
