package com.idata365.app.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.idata365.app.constant.AssetConstant;
import com.idata365.app.entity.UserEntity;
import com.idata365.app.enums.UserSexEnum;
import com.idata365.app.util.DateTools;


@RestController
public class RestTestController   {

  
    @RequestMapping(value = "/getAssetPerDay",method ={RequestMethod.POST,RequestMethod.GET})
    public int getAssetPerDay() {
     return AssetConstant.DAY_DAIMONDS_FOR_POWER_ALLNET;
    }
   
    
   
}