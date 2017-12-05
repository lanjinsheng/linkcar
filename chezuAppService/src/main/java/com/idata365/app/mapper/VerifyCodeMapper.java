package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.VerifyCode;

public interface VerifyCodeMapper {

	void insertVerifyCode(VerifyCode verifyCode);
	 VerifyCode findVerifyCode(VerifyCode verifyCode);
	 List<VerifyCode> getVerifyCodeTest(VerifyCode verifyCode);
}
