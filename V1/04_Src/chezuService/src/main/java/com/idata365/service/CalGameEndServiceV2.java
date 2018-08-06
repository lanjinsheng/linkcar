package com.idata365.service;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.constant.DicFamilyTypeConstant;
import com.idata365.entity.DicFamilyType;
import com.idata365.entity.FamilyScore;
import com.idata365.entity.TaskGameEnd;
import com.idata365.entity.bean.FamilyGameAsset;
import com.idata365.entity.bean.FamilySeasonAsset;
import com.idata365.mapper.app.TaskGameEndMapper;
import com.idata365.remote.ChezuAssetService;
import com.idata365.util.SignUtils;
@Service
public class CalGameEndServiceV2 {

	@Autowired
   TaskGameEndMapper taskGameEndMapper;
	@Autowired
	ChezuAssetService chezuAssetService;
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public boolean calGameEnd(TaskGameEnd taskGameEnd) throws RemoteException {
		 //最后得分的统计增加
		//判断俱乐部的比赛时长
//		int dayTimes=taskGameEnd.getDayTimes();
		FamilySeasonAsset familySeasonAsset=new FamilySeasonAsset();
		FamilyScore fs=taskGameEndMapper.getFamilyScore(taskGameEnd);
		familySeasonAsset.setFamilyId(fs.getFamilyId());
		familySeasonAsset.setEndDay(fs.getEndDay());
		familySeasonAsset.setOrderNo(Long.valueOf(fs.getYesterdayOrderNo()));
		familySeasonAsset.setSeasonName(fs.getEndDay());
		familySeasonAsset.setFamilyType(fs.getFamilyType());
		familySeasonAsset.setTrophy(fs.getTrophy());
		familySeasonAsset.setStartDay(fs.getStartDay());
		familySeasonAsset.setMemberNum(taskGameEnd.getMemberNum());
		String sign=SignUtils.encryptHMAC(fs.getFamilyId()+""+fs.getTrophy());
		boolean r=chezuAssetService.addFamilySeason(sign, familySeasonAsset);
		if(!r) {
			throw new RemoteException("远程异常");
		}
		//回退familyInfo的familyType
		DicFamilyType dft=DicFamilyTypeConstant.getDicFamilyType(fs.getFamilyType());
		fs.setFamilyType(dft.getNextExtends());
		fs.setTrophy(DicFamilyTypeConstant.getDicFamilyType(dft.getNextExtends()).getTrophy());
		taskGameEndMapper.updateFamilyInfo(fs);
		
		
		
		return true;
	}
	
	@Transactional
	public List<TaskGameEnd> geGameEndTask(TaskGameEnd taskGameEnd){
		//先锁定任务
		taskGameEndMapper.lockGameEndTask(taskGameEnd);
		//返回任务列表
		return taskGameEndMapper.getGameEndTask(taskGameEnd);
	}
	@Transactional
	public	void updateSuccGameEndTask(TaskGameEnd taskGameEnd) {
		taskGameEndMapper.updateGameEndSuccTask(taskGameEnd);
	}
	@Transactional
	public void updateGameEndFailTask(TaskGameEnd taskGameEnd) {
		if(taskGameEnd.getFailTimes()>100) {
			//状态置为2，代表计算次数已经极限
			taskGameEnd.setTaskStatus(2);
		}else {
			taskGameEnd.setTaskStatus(0);
		}
		taskGameEndMapper.updateGameEndFailTask(taskGameEnd);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskGameEndMapper.clearLockTask(compareTimes);
	}
	
}
