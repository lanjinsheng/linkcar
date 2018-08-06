package com.idata365.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.entity.FamilyDriveDayStat;
import com.idata365.entity.FamilyRelation;
import com.idata365.entity.TaskFamilyPk;
import com.idata365.entity.TaskFamilyPkRelation;
import com.idata365.mapper.app.TaskFamilyPkMapper;
import com.idata365.mapper.app.TaskFamilyPkRelationMapper;
/**
 * 
    * @ClassName: CalFamilyPkRelationService
    * @Description: TODO(自动匹配俱乐部pk赛)
    * @author LanYeYe
    * @date 2018年4月19日
    *
 */
@Service
public class CalFamilyPkRelationService {
    private final static long robotFamilyId=999999;
	@Autowired
   TaskFamilyPkRelationMapper taskFamilyPkRelationMapper;
//	public String getDateStr(int diff)
//	{
//		Date curDate = Calendar.getInstance().getTime();
//		Date diffDay = DateUtils.addDays(curDate, diff);
//		
//		String dayStr = DateFormatUtils.format(diffDay, "yyyy-MM-dd");
//		return dayStr;
//	}
	@Transactional
	public boolean calFamilyPkRelation(List<TaskFamilyPkRelation> taskFamilyPkRelations,String daystamp) {
	    int i=taskFamilyPkRelations.size();
	    List<FamilyRelation> frs=new ArrayList<FamilyRelation>();
	     if(i%2==1) {
	    	 //落單了，進行99999匹配
	    	 //數組最後一條記錄與999999匹配
	    	 FamilyRelation fr=new FamilyRelation();
		     fr.setCompetitorFamilyId(taskFamilyPkRelations.get(i-1).getFamilyId());
		     fr.setSelfFamilyId(robotFamilyId);
		     fr.setDaystamp(daystamp);
		     frs.add(fr);
		     i=i-1;
	     }else {
	    	 
	     }
	    for(int j=0;j<i;j++) {
	    	FamilyRelation fr=new FamilyRelation();
	    	fr.setCompetitorFamilyId(taskFamilyPkRelations.get(j).getFamilyId());
	    	fr.setDaystamp(daystamp);
	    	j++;
	    	fr.setSelfFamilyId(taskFamilyPkRelations.get(j).getFamilyId());
	    	frs.add(fr);
	    }
	    if(frs.size()>0) {
	    	taskFamilyPkRelationMapper.batchInsertRelation(frs);
	    }
		return true;
	}
	
	
	@Transactional
	public List<TaskFamilyPkRelation> getFamilyPkRelationTask(TaskFamilyPkRelation taskFamilyPkRelation){
		//先锁定任务
		taskFamilyPkRelationMapper.lockFamilyPkRelationTask(taskFamilyPkRelation);
		//返回任务列表
		return taskFamilyPkRelationMapper.getFamilyPkRelationTask(taskFamilyPkRelation);
	}
	@Transactional
	public	void updateSuccFamilyPkRelationTask(TaskFamilyPkRelation taskFamilyPkRelation) {
		taskFamilyPkRelationMapper.updateFamilyPkRelationSuccTask(taskFamilyPkRelation);
	}
//	
	@Transactional
	public void updateFailFamilyPkRelationTask(TaskFamilyPkRelation taskFamilyPkRelation) {
		taskFamilyPkRelationMapper.updateFamilyPkRelationFailTask(taskFamilyPkRelation);
	}
//	
	@Transactional
	public	void clearLockTask() {
		long compareTimes=System.currentTimeMillis()-(5*60*1000);
		taskFamilyPkRelationMapper.clearLockTask(compareTimes);
	}
	
}
