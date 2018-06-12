package com.idata365.app.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.idata365.app.entity.IDCard;
import com.idata365.app.entity.LicenseDriver;

public interface IDCardMapper {

	void insertImgIDCardFrontImg(Map<String, Object> IDCard);

	void insertImgIDCardBackImg(Map<String, Object> IDCard);

	int modifyIDCard(Map<String, Object> iDCard);

	IDCard findIDCardByUserId(@Param("userId") Long userId);

	List<IDCard> getUserIDCards();

}
