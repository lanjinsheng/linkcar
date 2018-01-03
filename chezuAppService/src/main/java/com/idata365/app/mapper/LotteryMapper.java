package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.ReadyLotteryBean;

public interface LotteryMapper
{
	public List<LotteryBean> query(LotteryBean bean);
	
	public void saveOrUpdate(LotteryBean bean);
	
	public int countLottery(LotteryBean bean);
	
	public void updateLotteryCount(LotteryBean bean);
	
	public List<LotteryBean> queryReadyLottery(ReadyLotteryBean bean);
	
	public int updateReadyLotteryStatus(LotteryBean bean);
	
	public void saveOrUpdateReadyLottery(ReadyLotteryBean bean);
	
}
