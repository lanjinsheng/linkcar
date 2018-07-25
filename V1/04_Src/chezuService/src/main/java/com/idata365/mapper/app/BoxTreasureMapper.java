package com.idata365.mapper.app;

import java.util.List;


import com.idata365.entity.BoxTreasureFamily;
import com.idata365.entity.BoxTreasureUser;

public interface BoxTreasureMapper {
 
//	CarpoolApprove getCarpoolApproveById(@Param("id") Long id);

	int insertBoxTreasureUser(BoxTreasureUser box);
	int insertBoxTreasureFamily(List<BoxTreasureFamily> list);
	
}
