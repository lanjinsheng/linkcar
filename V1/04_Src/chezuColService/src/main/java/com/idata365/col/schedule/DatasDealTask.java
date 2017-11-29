package com.idata365.col.schedule;


import org.apache.log4j.Logger;

public class DatasDealTask implements Runnable
{
	private static Logger log = Logger.getLogger(DatasDealTask.class);
	private long userId;
	private long habitId;
	private String  taskId;
    private int hadSensorData;
	public DatasDealTask(long puserId,long phabitId,String ptaskId,int phadSensorData){
		 this.userId=puserId;
		 this.habitId=phabitId;
		 this.taskId=ptaskId;
		 this.hadSensorData=phadSensorData;
	}
	@Override
	public void run()
	{
		 log.info("start=="+this.userId+"=="+this.habitId+"=="+this.taskId+"=="+this.hadSensorData);
		 try{
				 
			}catch(Exception e){
				e.printStackTrace();
			}
		 log.info("end=="+this.userId+"=="+this.habitId+"=="+this.taskId+"=="+this.hadSensorData);
	}

}
