package com.idata365.app.mapper;

import com.idata365.app.entity.SystemVisionBean;

/**
 * 
 * @className:com.idata365.app.mapper.SystemVisonMapper
 * @description:TODO
 * @date:2018年2月1日 下午12:39:36
 * @author:CaiFengYao
 */
public interface SystemVisonMapper
{
	/**
	 * 查询版本信息
	 */
	SystemVisionBean querySystemVisionInfo(SystemVisionBean bean);
}
