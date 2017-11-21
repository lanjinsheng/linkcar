package com.idata365.app.remote;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.idata365.app.entity.UserEntity;

@Component
public class DemoServiceHystric implements DemoService {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }

	@Override
	public String getUsers() {
		// TODO Auto-generated method stub
		return "sorry,sorry getUsers ";
	}

	@Override
	public void save(UserEntity user) {
		// TODO Auto-generated method stub
//		return "sorry,sorry save ";
	}
	@Override
	public String saveByMap(Map<Object, Object> user) {
		// TODO Auto-generated method stub
		return "sorry,sorry saveByMap ";
	}

}
