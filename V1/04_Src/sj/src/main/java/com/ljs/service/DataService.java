package com.ljs.service;
import io.netty.channel.socket.SocketChannel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ljs.dao.DataMapper;
import com.ljs.util.ServerUtil;

@Transactional(rollbackFor = Exception.class)
@Service("dataService")
public class DataService {
	private static Logger log = Logger.getLogger(DataService.class);
	@Autowired
	private DataMapper dataMapper;
	//获取车源数据
	public String listPageNetOpera(Map<String,Object> map){
		List<Map<String, Object>> list= this.dataMapper.listPageNetOpera(map);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(list));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}
	public String listPageUrl(Map<String,Object> map){
		List<Map<String, Object>> list= this.dataMapper.listPageUrl(map);
		StringBuffer sb = new StringBuffer("");
		sb.append(ServerUtil.toJson(list));
		ServerUtil.putSuccess(map);
		return sb.toString();
	}
	
	public int insertUrl(Map<String,Object> map){
		return dataMapper.insertUrl(map);
	}
	public int updateUrl(Map<String,Object> map){
		return dataMapper.updateUrl(map);
	}
}
