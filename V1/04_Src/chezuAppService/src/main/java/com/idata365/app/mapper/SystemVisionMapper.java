package com.idata365.app.mapper;

import com.idata365.app.entity.SystemVisionBean;

/**
 * 
 * @className:com.idata365.app.mapper.SystemVisionMapper
 * @description:TODO
 * @date:2018年2月2日 上午10:39:36
 * @author:CaiFengYao
 */
public interface SystemVisionMapper
{
	/**
	 * 查询版本信息
	 */
	SystemVisionBean querySystemVisionInfo(SystemVisionBean bean);
}
