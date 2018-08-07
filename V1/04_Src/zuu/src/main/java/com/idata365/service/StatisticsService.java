package com.idata365.service;
 
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.idata365.entity.Statistics;
import com.idata365.mapper.app.UsersAccountMapper;
import com.idata365.mapper.statistics.StatisticsMapper;

@Service
public class StatisticsService
{
	protected static final Logger LOGGER = LoggerFactory.getLogger(StatisticsService.class);
 
	@Autowired
	private UsersAccountMapper usersAccountMapper;
	@Autowired
	private StatisticsMapper  statisticsMapper;
	@Transactional
	public String getUserId(String  token) {
		 Long userId=usersAccountMapper.getUserIdByToken(token);
		 if(userId==null) {
			 return "-1000";
		 }
		 return String.valueOf(userId);
	}
 
	@Transactional(value="statisticsTransactionManager")
	public void copyRecord(String day){
		//判断并建表
		Map<String,Object> map=new HashMap<>();
		map.put("tbName", "statistics"+day.replace("-", ""));
		Map<String,Object> hadMap=statisticsMapper.jugeTable(map);
		if(hadMap==null) {
			statisticsMapper.createTableSql(map);
		}
		//获取最高纪录
		Long maxId=statisticsMapper.getMaxId(map);
		if(maxId==null) {
			maxId=Long.valueOf(0);
		}
		map.put("id", maxId);
		map.put("eventTime", day+" 23:59:59.999");
		int count=statisticsMapper.moveRecord(map);
		if(count==0) {
			statisticsMapper.deleteRecord(map);
		}
		
	}

	@Transactional(value="statisticsTransactionManager")
	public void insertRecord(String fileName) throws IOException{
		File file = new File(fileName);   
		BufferedInputStream fis = new BufferedInputStream(new FileInputStream(file));    
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis,"utf-8"),5*1024*1024);// 用5M的缓冲读取文本文件  
		String line = "";
		List<Statistics> list=new ArrayList<>();
		while((line = reader.readLine()) != null){
		   //TODO: write your business
			String eventTime=line.substring(0, 23);
			String otherLog=line.substring(64, line.length());
			String []logs=otherLog.split("=");
			Long userId=Long.valueOf(logs[0].trim());
			String action=logs[1].trim();
			Statistics statistics=new Statistics();
			statistics.setAction(action);
			statistics.setEventTime(eventTime);
			statistics.setUserId(userId);
			list.add(statistics);
			if(list.size()>999) {
				statisticsMapper.insertRecord(list);
				list.clear();
			}
		}
		if(list.size()>0) {
			statisticsMapper.insertRecord(list);
		}
		
	}
}
