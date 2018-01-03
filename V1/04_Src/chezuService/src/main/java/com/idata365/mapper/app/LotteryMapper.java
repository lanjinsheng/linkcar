package com.idata365.mapper.app;

import java.util.List;

import com.idata365.entity.LotteryBean;
import com.idata365.entity.ReadyLotteryBean;

public interface LotteryMapper
{

	
	public List<LotteryBean> queryReadyLottery(ReadyLotteryBean bean);
	
	public int updateReadyLotteryStatus(LotteryBean bean);
	
}
