package com.idata365.schedule;

import java.util.List;

import org.apache.log4j.Logger;

import com.idata365.entity.TaskKeyLog;
import com.idata365.entity.TaskTest;
import com.idata365.service.SpringContextUtil;
import com.idata365.service.TaskKeyLogService;
import com.idata365.service.TaskTestService;

public class TaskThreadTest  implements Runnable
{
	private static Logger log = Logger.getLogger(TaskThreadTest.class);
//	private Long data=0L;
//	public TaskTest(Long pData) {
//		this.data=
//	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		TaskTestService taskTestService=SpringContextUtil.getBean("taskTestService", TaskTestService.class);
		TaskKeyLogService taskKeyLogService=SpringContextUtil.getBean("taskKeyLogService", TaskKeyLogService.class);
		String taskFlag=String.valueOf(System.currentTimeMillis());
	
		
		try {
			TaskKeyLog key=new TaskKeyLog();
			key.setTaskFlag(String.valueOf(taskFlag));
			key.setTaskName("TASKTEST");
		    int hadKey=	taskKeyLogService.insertColKey(key);
			if(hadKey==0) return;
			TaskTest task=new TaskTest();
			task.setTaskFlag(String.valueOf(taskFlag));
			List<TaskTest> list=taskTestService.getTaskTestTask(task);
//			taskTestService.releaseKey(key);
		log.info("TaskTest1 do--list.size="+list.size());
			for(TaskTest taskTest:list) {
				try {
					boolean result=taskTestService.insertTask(taskTest);
				if(result) {
					taskTestService.updateSuccTaskTestTask(taskTest);
					 
				}else {
					if(taskTest.getFailTimes()>100) {
						//状态置为2，代表计算次数已经极限
						taskTest.setTaskStatus(2);
					}
					taskTestService.updateTaskTestFailTask(taskTest);
				}
				}catch(Exception e) {
					e.printStackTrace();
					log.error(e);
					if(taskTest.getFailTimes()>100) {
						//状态置为2，代表计算次数已经极限
						taskTest.setTaskStatus(2);
					}
					taskTestService.updateTaskTestFailTask(taskTest);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
//			taskTestService.releaseKey(key);
		}finally {
			
		}
	}

}
