package com.idata365.app.mapper;

import java.util.List;

import com.idata365.app.entity.LotteryMigrateInfoMsgBean;
import com.idata365.app.entity.LotteryMigrateInfoMsgParamBean;

public interface LotteryMigrateInfoMsgMapper
{
	public List<LotteryMigrateInfoMsgBean> query(LotteryMigrateInfoMsgParamBean bean);
	
	public void save(LotteryMigrateInfoMsgBean bean);
	
	public void updateStatus(LotteryMigrateInfoMsgParamBean bean);
	
	public LotteryMigrateInfoMsgBean queryById(LotteryMigrateInfoMsgParamBean bean);
}
