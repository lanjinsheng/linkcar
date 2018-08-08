package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

public interface ImMapper {

    List<Map<String,String>> getMsg(Map<String,Object> map);

    List<Map<String,String>> getMsgGlobal(Map<String,Object> map);
    
    void insert(Map<String,Object> msg);
 
    int insertLog(Map<String,Object> map);
    int updateLog(Map<String,Object> map);
    
}
