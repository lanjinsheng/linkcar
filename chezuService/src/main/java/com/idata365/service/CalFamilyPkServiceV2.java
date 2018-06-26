package com.idata365.service;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.constant.DicFamilyTypeConstant;
import com.idata365.entity.DicFamilyType;
import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.bean.FamilyGameAsset;
import com.idata365.mapper.app.TaskFamilyDayScoreMapper;
import com.idata365.mapper.app.TaskFamilyPkMapper;
import com.idata365.remote.ChezuAssetService;
import com.idata365.schedule.CalFamilyDayPkTask;
import com.idata365.util.SignUtils;
@Service
public class CalFamilyPkServiceV2 {
	private static Logger log = Logger.getLogger(CalFamilyPkServiceV2.class);
	@Autowired
    TaskFamilyPkMapper taskFamilyPkMapper;
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
	public boolean giveNewFamilyTrophy(String daystamp,String startDay,String endDay) throws RemoteException {
		   Map<String,Object> paramMap=new HashMap<String,Object>();
		   paramMap.put("daystamp", daystamp);
		   paramMap.put("startDay", startDay);
		   paramMap.put("endDay", endDay);
		   taskFamilyPkMapper.giveNewFamilyTrophy(paramMap);
		   
		   List<FamilyDriveDayStat> list=taskFamilyPkMapper.getNewFamilyDayScore(paramMap);
		   for(FamilyDriveDayStat fd:list) {
			   //更新familyInfo
			   taskFamilyPkMapper.updateFamilyInfo(fd);
			   log.info("新家族发送pk接口:"+fd.getFamilyId());
			   boolean r=addFamilyGameOrder(startDay, endDay, fd.getFamilyId(), fd.getDaystamp(),fd.getMemberNum(),80);
			   log.info("新家族发送pk接口结束:"+fd.getFamilyId()+r);
			   if(!r) {
		        	throw new RemoteException();  
		        }
		   }
		   return true;
	}
	private boolean addFamilyGameOrder(String startDay,String endDay,Long winFamily,String daystamp,Integer memberNum,
			int winFamilyType) {
		if(memberNum<1) {
			return true;
		}
		//远程同步比赛结果名次
		FamilyGameAsset fg=new FamilyGameAsset();
		fg.setEndDay(endDay);
		fg.setFamilyId(winFamily);
		fg.setOrderNo(0L);
		fg.setMemberNum(memberNum);
		fg.setSeasonName(daystamp);
		fg.setStartDay(startDay);
		fg.setFamilyType(winFamilyType);
		String sign=SignUtils.encryptHMAC(String.valueOf(winFamily));
		boolean r=chezuAssetService.addFamilyGameOrder(sign, fg);
		return r;
	}
	@Transactional
	public boolean calFamilyPk(TaskFamilyPk taskFamilyPk,String startDay,String endDay) throws RemoteException {
		//获取pk关系
		Long selfFamilyId=taskFamilyPk.getSelfFamilyId();
		Long competitorFamilyId= taskFamilyPk.getCompetitorFamilyId();
		int level1=taskFamilyPk.getSelfFamilyLevel();
		int level2=taskFamilyPk.getCompetitorFamilyLevel();
		FamilyDriveDayStat fdds1=new FamilyDriveDayStat();
		fdds1.setFamilyId(selfFamilyId);
		fdds1.setDaystamp(taskFamilyPk.getDaystamp());
		fdds1=taskFamilyPkMapper.getFamilyDayScoreByFD(fdds1);
		int trophy1=0, trophy2=0;
		FamilyDriveDayStat fdds2=new FamilyDriveDayStat();
		fdds2.setFamilyId(competitorFamilyId);
		fdds2.setDaystamp(taskFamilyPk.getDaystamp());
		fdds2=taskFamilyPkMapper.getFamilyDayScoreByFD(fdds2);
		DicFamilyType d1=DicFamilyTypeConstant.getDicFamilyType(level1);
		DicFamilyType d2=DicFamilyTypeConstant.getDicFamilyType(level2);
		Long winFamily=selfFamilyId;
		int winFamilyType=level1;
		Integer winMemberNum=0;
		boolean hadSendAsset=false;
		if(fdds1.getScore()>fdds2.getScore()) {
			trophy1=d1.getWin();
			if(fdds2.getScore()==0) {
				trophy2=d2.getLoss2();
			}else {
				trophy2=d2.getLoss();
			}
			winMemberNum=fdds1.getMemberNum();
			hadSendAsset=true;
		}else if(fdds1.getScore()<fdds2.getScore()) {
			if(taskFamilyPk.getRelationType()==2) {
				hadSendAsset=true;
			}
			winFamily=competitorFamilyId;
			winMemberNum=fdds2.getMemberNum();
			winFamilyType=level2;
			trophy2=d2.getWin();
			if(fdds1.getScore()==0) {
				trophy1=d1.getLoss2();
			}else {
				trophy1=d1.getLoss();
			}
		 
		}else {
			trophy1=d1.getDogfall();
			trophy2=d2.getDogfall();
		}
		fdds1.setTrophy(trophy1);
		fdds2.setTrophy(trophy2);

        String month=taskFamilyPk.getDaystamp().replaceAll("-", "").substring(0,6);
        fdds1.setMonth(month);
        fdds1.setStartDay(startDay);
        fdds1.setEndDay(endDay);
        fdds2.setMonth(month);
        fdds2.setStartDay(startDay);
        fdds2.setEndDay(endDay);
        
        if(taskFamilyPk.getRelationType()==2) {//双边更新
		    //更新日pk结果,更新奖杯
	        taskFamilyPkMapper.updateFamilyDayScoreById(fdds1);
	        taskFamilyPkMapper.updateFamilyDayScoreById(fdds2);
	        
	        //更新赛季结果,更新奖杯
	        taskFamilyPkMapper.updateFamilyScore(fdds1);
	        taskFamilyPkMapper.updateFamilyScore(fdds2);
	        
	        //更新family结果,更新奖杯
	        taskFamilyPkMapper.updateFamilyInfo(fdds1);
	        taskFamilyPkMapper.updateFamilyInfo(fdds2);
        }else if(taskFamilyPk.getRelationType()==1) {//单边更新
        	 //更新日pk结果,更新奖杯
	        taskFamilyPkMapper.updateFamilyDayScoreById(fdds1);
	        //更新赛季结果,更新奖杯
	        taskFamilyPkMapper.updateFamilyScore(fdds1);
	        //更新family结果,更新奖杯
	        taskFamilyPkMapper.updateFamilyInfo(fdds1);
        }
        if(hadSendAsset) {
		    boolean r=addFamilyGameOrder(startDay, endDay, winFamily, taskFamilyPk.getDaystamp(),winMemberNum,
		    		winFamilyType);
	        
	        if(!r) {
	        	throw new RemoteException();  
	        }
	       
			return r;
        }
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
