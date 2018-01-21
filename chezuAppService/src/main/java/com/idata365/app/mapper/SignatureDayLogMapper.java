package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.SigResultBean;
import com.idata365.app.entity.SignatureDayLogBean;
import com.idata365.app.entity.SignatureStatBean;

public interface SignatureDayLogMapper
{
	List<String> query(SignatureDayLogBean bean);
	
	int countByUserId(SignatureDayLogBean bean);
	
	SigResultBean queryLastSigDay(SignatureDayLogBean bean);
	
	SigResultBean countSigStatNum(SignatureDayLogBean bean);
	
	public void saveSigStat(SignatureDayLogBean bean);
	
	public void updateSigNumStat(SignatureDayLogBean bean);
	
	void save(SignatureDayLogBean bean);
	
	String querySigStatus(SignatureDayLogBean bean);
	
	SignatureStatBean querySigStatInfo(SignatureDayLogBean bean);
	
	void updateSigStatus(SignatureDayLogBean bean);
}
