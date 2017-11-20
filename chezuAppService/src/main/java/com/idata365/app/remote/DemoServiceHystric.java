package com.idata365.app.remote;

import org.springframework.stereotype.Component;

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

}
