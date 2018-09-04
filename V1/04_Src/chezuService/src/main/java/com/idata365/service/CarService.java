package com.idata365.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.mapper.app.UserCarMapper;

/**
 * 
        * @ClassName: CarService
        * @Description: TODO(car)
        * @author LiXing
        * @date 2018年7月17日
        *
 */
@Service
public class CarService extends BaseService<CarService> {
	private final static Logger LOG = LoggerFactory.getLogger(CarService.class);
	@Autowired
	UserCarMapper userCarMapper;

	public CarService() {

	}
	

}
