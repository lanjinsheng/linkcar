package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.SignatureDayLogBean;

public interface SignatureDayLogMapper
{
	List<String> query(SignatureDayLogBean bean);
	
	int countByUserId(SignatureDayLogBean bean);
	
	void save(SignatureDayLogBean bean);
}
