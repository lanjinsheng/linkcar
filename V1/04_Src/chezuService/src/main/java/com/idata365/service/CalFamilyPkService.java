package com.idata365.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.mapper.app.TaskFamilyDayScoreMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
@Service
public class CalFamilyPkService {

	@Autowired
   TaskFamilyPkMapper taskFamilyPkMapper;
	
	public String getDateStr(int diff)
	{
		Date curDate = Calendar.getInstance().getTime();
		Date diffDay = DateUtils.addDays(curDate, diff);
		
		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
		return dayStr;
	}
	@Transactional
	public boolean calFamilyPk(TaskFamilyPk taskFamilyPk,String startDay,String endDay) {
		//获取pk关系
		Long selfFamilyId=taskFamilyPk.getSelfFamilyId();
		Long competitorFamilyId= taskFamilyPk.getCompetitorFamilyId();
		int level1=taskFamilyPk.getSelfFamilyLevel();
		int level2=taskFamilyPk.getCompetitorFamilyLevel();
		FamilyDriveDayStat fdds1=new FamilyDriveDayStat();
		fdds1.setFamilyId(selfFamilyId);
		fdds1.setDaystamp(taskFamilyPk.getDaystamp());
		fdds1=taskFamilyPkMapper.getFamilyDayScoreByFD(fdds1);
		
		FamilyDriveDayStat fdds2=new FamilyDriveDayStat();
		fdds2.setFamilyId(competitorFamilyId);
		fdds2.setDaystamp(taskFamilyPk.getDaystamp());
		fdds2=taskFamilyPkMapper.getFamilyDayScoreByFD(fdds2);
		
		int level1Sub2=level1-level2;
		
        if(fdds1.getScoreComm()>fdds2.getScoreComm()) {
        	//PK等级得分
        	if(level1Sub2==0) {
        		fdds1.setFamilyLevelFactor(6d);
        		fdds2.setFamilyLevelFactor(-6d);
        	}else if(level1Sub2==10) {
        		fdds1.setFamilyLevelFactor(4d);
        		fdds2.setFamilyLevelFactor(-6d);
        	}else if(level1Sub2==20) {
        		fdds1.setFamilyLevelFactor(2d);
        		fdds2.setFamilyLevelFactor(-10d);
        	}else if(level1Sub2==-10) {
        		fdds1.setFamilyLevelFactor(8d);
        		fdds2.setFamilyLevelFactor(-4d);
        	}else if(level1Sub2==-20) {
        		fdds1.setFamilyLevelFactor(10d);
        		fdds2.setFamilyLevelFactor(-2d);
        	}
        }else if(fdds2.getScoreComm()>fdds1.getScoreComm()) {
        	//PK等级得分
        	if(level1Sub2==0) {
        		fdds2.setFamilyLevelFactor(6d);
        		fdds1.setFamilyLevelFactor(-6d);
        	}else if(level1Sub2==10) {
        		fdds2.setFamilyLevelFactor(4d);
        		fdds1.setFamilyLevelFactor(-6d);
        	}else if(level1Sub2==20) {
        		fdds2.setFamilyLevelFactor(2d);
        		fdds1.setFamilyLevelFactor(-10d);
        	}else if(level1Sub2==-10) {
        		fdds2.setFamilyLevelFactor(8d);
        		fdds1.setFamilyLevelFactor(-4d);
        	}else if(level1Sub2==-20) {
        		fdds2.setFamilyLevelFactor(10d);
        		fdds1.setFamilyLevelFactor(-2d);
        	}
        }else {//不得分
        	fdds2.setFamilyLevelFactor(0d);
    		fdds1.setFamilyLevelFactor(0d);
        }
        taskFamilyPkMapper.updateFamilyDayScoreById(fdds1);
        taskFamilyPkMapper.updateFamilyDayScoreById(fdds2);
        String month=taskFamilyPk.getDaystamp().replaceAll("-", "").substring(0,6);
        fdds1.setMonth(month);
        fdds1.setStartDay(startDay);
        fdds1.setEndDay(endDay);
        fdds2.setMonth(month);
        fdds2.setStartDay(startDay);
        fdds2.setEndDay(endDay);
        taskFamilyPkMapper.updateFamilyScore(fdds1);
        taskFamilyPkMapper.updateFamilyScore(fdds2);
		return true;
	}
	
	
	@Transactional
	public List<TaskFamilyPk> getFamilyPkTask(TaskFamilyPk taskFamilyPk){
		//先锁定任务
		taskFamilyPkMapper.lockFamilyPkTask(taskFamilyPk);
		//返回任务列表
		return taskFamilyPkMapper.getFamilyPkTask(taskFamilyPk);
	}
	@Transactional
	public	void updateSuccFamilyPkTask(TaskFamilyPk taskFamilyPk) {
		taskFamilyPkMapper.updateFamilyPkSuccTask(taskFamilyPk);
	}
//	
	@Transactional
	public void updateFailFamilyPkTask(TaskFamilyPk taskFamilyPk) {
		taskFamilyPkMapper.updateFamilyPkFailTask(taskFamilyPk);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyPkMapper.clearLockTask(compareTimes);
	}
	
}
