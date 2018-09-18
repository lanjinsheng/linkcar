package com.idata365.app.schedule;

import com.idata365.app.serviceV2.RemindService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.TimerTask;


public class RemindBossTask extends TimerTask {
    private static Logger log = Logger.getLogger(RemindBossTask.class);
    private static Object lock = new Object();
    public static boolean pd = true;

    //注入ThreadPoolTaskExecutor 到主线程中
    private ThreadPoolTaskExecutor threadPool;

    @Autowired
    RemindService remindService;

    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
//		System.out.println(new Date().getTime());
        this.threadPool = threadPool;
    }

    //在主线程中执行任务线程.....
    @Override
    public void run() {
        log.info("RemindBossTask start--");

        if (!pd) {
            return;
        }
        synchronized (lock) {
            if (pd) {
                pd = false;

                try {
				    boolean flag = remindService.remindBossByTask();
				    log.info("RemindBossTask status--"+flag);
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error(e);

                }
                pd = true;
            }

        }
        log.info("RemindBossTask end--");
    }
}