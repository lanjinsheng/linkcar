package com.idata365.app.service;

 

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.idata365.app.entity.FamilyRelation;
import com.idata365.app.mapper.FamilyRelationMapper;
import com.idata365.app.mapper.UsersAccountMapper;
import com.idata365.app.util.DateTools;

@Service
public class FightService extends BaseService<FightService> {
	private static final Logger LOGGER = LoggerFactory.getLogger(FightService.class);

	@Autowired
	private FamilyRelationMapper familyRelationMapper;
	 
	private UsersAccountMapper usersAccountMapper;
     /**
      * 获取对手家族id，无对战记录返回null
      * @param selfFamilyId
      * @return
      */
	public Long getOpponentIdBySelfId(Long selfFamilyId) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("familyId", selfFamilyId);
		map.put("daystamp", DateTools.getTomorrowDateStr());
		FamilyRelation relation=familyRelationMapper.getFightRelation(map);
		if(relation==null)
		return null;
		if(selfFamilyId==relation.getSelfFamilyId()){
			return relation.getCompetitorFamilyId();
		}else{
			return relation.getSelfFamilyId();
		}
	}
	
	public Map<String,Object> getOpponentInfo(Long familyId) {
		Map<String,Object> family=usersAccountMapper.getFamilyByFamilyId(familyId);
		return family;
	}
	
/**
 * 
 * @param selfFamilyId
 * @param competitorFamilyId
 * @return
 */
    @Transactional
	public boolean insertFightRelation(Long selfFamilyId,Long competitorFamilyId){
    	//删除老匹配
    	String tomorrow=DateTools.getTomorrowDateStr();
    	Map<String,Object> map=new HashMap<String,Object>();
		map.put("familyId", selfFamilyId);
		map.put("daystamp", tomorrow);
		FamilyRelation relationMatch=familyRelationMapper.getFightRelation(map);
		if(relationMatch!=null){
			if(relationMatch.getRelationType()==1){
//				单方匹配的，直接删除记录
				familyRelationMapper.deleteRelation(relationMatch.getId());
			}else{
				//双方匹配的，进行判断并进行降级为单方匹配
				if(relationMatch.getSelfFamilyId()==selfFamilyId){
					relationMatch.setSelfFamilyId(relationMatch.getCompetitorFamilyId().longValue());
					relationMatch.setCompetitorFamilyId(selfFamilyId.longValue());
				}
				familyRelationMapper.reduceRelationType(relationMatch);
			}
		}
    	
    	
		FamilyRelation relation=new FamilyRelation();
		relation.setSelfFamilyId(selfFamilyId);
		relation.setCompetitorFamilyId(competitorFamilyId);
		relation.setRelationType(1);
		long key1=Math.abs(selfFamilyId-competitorFamilyId);
		long key2=selfFamilyId+competitorFamilyId;
		relation.setDaystamp(tomorrow);
		relation.setUniKey1(key1+"-"+key2+"-"+tomorrow);
		
		FamilyRelation hadRelation=familyRelationMapper.hadMatch(relation);
		if(hadRelation!=null){//存在被挑战记录，更新记录类型
			familyRelationMapper.updateRelationType(hadRelation.getId()); 
		}else{//插入记录
			familyRelationMapper.insertFamilyRelation(relation); 
		}
		return true;
	}
    /**
     * 
     * @param selfFamilyid
     * @return
     */
    public Map<String,Object> getRandFightFamily(Long selfFamilyid,Long competitorFamilyId){
    	
    	Map<String,Object> family=usersAccountMapper.getFamilyByFamilyId(selfFamilyid);
    	Integer familyType=Integer.valueOf(family.get("familyType").toString())-10;
    	family.put("familyType", familyType);
    	Map<String,Object> pkFamily=familyRelationMapper.getMatchFamily(family);
    	if(competitorFamilyId.longValue()==0){
    		//首次匹配，不进行扣能量
    	}else{
    		if(competitorFamilyId.longValue()==Long.valueOf(pkFamily.get("id").toString())){
    			//匹配重叠了，不扣能量
    		}else{
    			//扣除动力
    			
    			
    		}
    	}
//    	while(pkFamily!=null && Long.valueOf(pkFamily.get("id").toString())==selfFamilyid.longValue()){
//    		pkFamily=familyRelationMapper.getMatchFamily(family);
//    	}
    	return pkFamily;
    }
    
    public double getAvgThreeDayScore(long familyId){
    	Double d=familyRelationMapper.getFamilyAvgScore(familyId);
    	if(d==null){
    		return 0;
    	}
    	else{
    		return d;
    	}
    }
}
