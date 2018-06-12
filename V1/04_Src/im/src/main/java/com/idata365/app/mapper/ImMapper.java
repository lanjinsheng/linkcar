package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

public interface ImMapper {

    List<Map<String,String>> getMsg();
    void insert(Map<String,Object> msg);
 

}
