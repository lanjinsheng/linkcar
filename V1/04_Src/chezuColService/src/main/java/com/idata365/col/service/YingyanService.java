package com.idata365.col.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.idata365.col.remote.ChezuDriveService;

@Service
public class YingyanService {
@Autowired
ChezuDriveService chezuDriveService;

public Map<String,Object> addPoint(Map<String,String> point){
	return chezuDriveService.addPoint(point);
}

public Map<String,Object> addPointList(List<Map<String,String>> pointList){
	return chezuDriveService.addPointList(pointList);
}

public Map<String,Object> analysis(Map<String,String> param){
	return chezuDriveService.analysis(param);
}



}
