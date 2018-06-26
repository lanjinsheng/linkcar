package com.idata365.constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.idata365.entity.FamilyRelation;
import com.idata365.service.DicService;
import com.idata365.service.SpringContextUtil;
import com.idata365.util.DateTools;

public class FamilyRelationConstant {
	public static Map<Long,List<FamilyRelation>> familyRelationMap=new HashMap<Long,List<FamilyRelation>>();
    public static String daystamp="";
	public static List<FamilyRelation> getFamilyRelation(Long familyId){
		String day=DateTools.getYYYY_MM_DD();
		if(familyRelationMap.size()==0 || !day.equals(daystamp)){
			familyRelationMap.clear();
			DicService dicService=SpringContextUtil.getBean("dicService", DicService.class);
			List<FamilyRelation> list=dicService.getFamilyRelation();
			for(FamilyRelation relation:list){
				List<FamilyRelation> valueList1=familyRelationMap.get(relation.getSelfFamilyId());
				List<FamilyRelation> valueList2=familyRelationMap.get(relation.getCompetitorFamilyId());
				if(valueList1==null) {
					valueList1=new ArrayList<FamilyRelation>();
					familyRelationMap.put(relation.getSelfFamilyId(), valueList1);
				}
				
				if(valueList2==null) {
					valueList2=new ArrayList<FamilyRelation>();
					familyRelationMap.put(relation.getCompetitorFamilyId(), valueList2);
				}
				
				valueList1.add(relation);
				valueList2.add(relation);
			}
			daystamp=day;
		}
		return familyRelationMap.get(familyId);
			
	}
	
	 
	 
}
