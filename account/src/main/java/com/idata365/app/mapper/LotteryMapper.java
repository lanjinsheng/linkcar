package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.LotteryBean;
import com.idata365.app.entity.LotteryLogInfoParamBean;
import com.idata365.app.entity.ReadyLotteryBean;

public interface LotteryMapper
{
	public List<LotteryBean> query(LotteryBean bean);
	
	public void saveOrUpdate(LotteryBean bean);
	
	public Integer countLottery(LotteryBean bean);
	
	public void updateLotteryCount(LotteryBean bean);
	
	public void increLotteryCount(LotteryBean bean);
	
	public List<ReadyLotteryBean> queryReadyLotteryAwardId(ReadyLotteryBean bean);
	
	public List<LotteryBean> queryReadyLottery(ReadyLotteryBean bean);
	
	public int updateReadyLotteryStatus(LotteryBean bean);
	
	public void addLotteryCount(LotteryBean bean);
	
	public void delLotteryCount(LotteryBean bean);
	
	public void increReadyLotteryCount(LotteryBean bean);
	
	public void decreReadyLotteryCount(LotteryBean bean);
	
	public void saveOrUpdateReadyLottery(ReadyLotteryBean bean);
	
	public int countReadyLottery(ReadyLotteryBean bean);
	
	public void delReadyLottery(ReadyLotteryBean bean);
	
	public void saveLotteryLog(LotteryLogInfoParamBean bean);
	
	public int reduceLotteryCount(LotteryBean bean);
    //v1.1增加 行程道具的获取与接收
	 Map<String,Object> getChest(Map<String,Object> map);
	 public int recChest(@Param("id") Long id);
	 
}
