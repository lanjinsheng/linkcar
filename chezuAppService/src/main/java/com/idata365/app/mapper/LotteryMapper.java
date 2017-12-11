package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.LotteryBean;

public interface LotteryMapper
{
	public List<LotteryBean> query(LotteryBean bean);
	
	public void saveOrUpdate(LotteryBean bean);
}
