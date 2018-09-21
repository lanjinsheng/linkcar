package com.idata365.app.mapper;

import com.idata365.app.entity.v2.CompoundInfo;
import org.apache.ibatis.annotations.Param;

public interface CompoundMapper {

    Integer getOddByQualityAndTravelNum(@Param("quality")String quality,@Param("travelNum")Integer travelNum);

    int insertCompoundInfo(CompoundInfo compoundInfo);

    CompoundInfo getCompoundInfoByFamilyIdAndStoveId(@Param("familyId")Long familyId,@Param("stoveId")Integer stoveId);

    int updateCompoundInfo(CompoundInfo compoundInfo);

    int updateCompoundInfoStatus(@Param("newStatus")Integer newStatus,@Param("familyId")Long familyId,@Param("stoveId")Integer stoveId,@Param("oldStatus")Integer oldStatus);

    int updateFinalComponentId(@Param("id")Long id, @Param("finalComponentId")Integer finalComponentId);
}
