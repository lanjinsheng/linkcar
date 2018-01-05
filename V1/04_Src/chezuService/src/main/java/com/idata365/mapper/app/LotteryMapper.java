package com.idata365.mapper.app;

import java.util.List;

import com.idata365.entity.ReadyLotteryBean;

public interface LotteryMapper
{

	
	public List<ReadyLotteryBean> queryReadyLottery(ReadyLotteryBean bean);
	
	public int updateReadyLotteryStatus(ReadyLotteryBean bean);
	
}
