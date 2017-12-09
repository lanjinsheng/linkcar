package com.idata365.app.mapper;

import com.idata365.app.entity.SignatureLogBean;

public interface SignatureLogMapper
{
	public void saveOrUpdate(SignatureLogBean bean);
	
	public SignatureLogBean query(SignatureLogBean bean);
}
