package com.idata365.app.controller.securityV2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.idata365.app.constant.DicComponentConstant;
import com.idata365.app.controller.security.BaseController;
import com.idata365.app.entity.bean.ReturnMessage;
import com.idata365.app.entity.v2.DicComponent;
import com.idata365.app.service.FamilyService;
import com.idata365.app.serviceV2.ComponentService;
import com.idata365.app.util.ResultUtils;

@RestController
public class ComponentController extends BaseController {
	protected static final Logger LOG = LoggerFactory.getLogger(ComponentController.class);
	@Autowired
	ComponentService componentService;
	
	@Autowired
	FamilyService familyService;
	
	@RequestMapping(value = "/getUserComponent")
	Map<String, Object> getUserComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Map<String,Object> rtMap=componentService.getUserComponent(this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}

	@RequestMapping(value = "/getUserComponentV2")
	Map<String, Object> getUserComponentV2(@RequestParam (required = false) Map<String, String> allRequestParams,
										 @RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		Map<String,Object> rtMap=componentService.getUserComponentV2(this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	@RequestMapping(value = "/getFamilyComponent")
	Map<String, Object> getFamilyComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		Map<String,Object> rtMap=componentService.getFamilyComponent(familyId);
		return ResultUtils.rtSuccess(rtMap);
	}

	@RequestMapping(value = "/getFamilyComponentV2")
	Map<String, Object> getFamilyComponentV2(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		Map<String,Object> rtMap=componentService.getFamilyComponentV2(familyId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	@RequestMapping(value = "/dropFamilyComponent")
	Map<String, Object> dropFamilyComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long familyComponentId=Long.valueOf(requestBodyParams.get("familyComponentId").toString());
		Map<String,Object> rtMap=componentService.dropFamilyComponent(familyComponentId);
		if(rtMap==null){
			return ResultUtils.rtFailParam(null, "零件无法丢弃");
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	//零件分配成员列表
	@RequestMapping(value = "/getComponentDistribute")
	Map<String, Object> getComponentDistribute(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		Map<String,Object> family=familyService.findFamilyIdByUserId(this.getUserId());
		long familyId=Long.valueOf(family.get("id").toString());
		Map<String,Object> rtMap=componentService.getComponentDistribute(this.getUserId(),familyId);
		return ResultUtils.rtSuccess(rtMap);
	}
	//分配零件
	@RequestMapping(value = "/submitComponentDistribute")
	Map<String, Object> submitDistribute(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long familyComponentId=Long.valueOf(requestBodyParams.get("familyComponentId").toString());
		long operationManId=this.getUserId();
		Map<String,Object> family=familyService.findFamilyIdByUserId(operationManId);
		long familyId=Long.valueOf(family.get("id").toString());
		long toUserId=Long.valueOf(requestBodyParams.get("toUserId").toString());
		int update=componentService.submitComponentDistribute(familyComponentId,familyId,toUserId,this.getUserId());
		if(update==0){
			return ResultUtils.rtFailParam(null, "赠送失败");
		}
		return ResultUtils.rtSuccess(null);
	}
	
	//获取申请日志
	@RequestMapping(value = "/getComponentGiveLog")
	Map<String, Object> getComponentGiveLog(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
		Map<String,Object> rtMap=componentService.getComponentGiveLog(componentGiveLogId,this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}
	
	
	//消息点击领取  ()
	@RequestMapping(value = "/recieveGiveLog")
	Map<String, Object> recieveGiveLog(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
		Map<String,Object> rtMap=componentService.recieveGiveLog(componentGiveLogId);
		return ResultUtils.rtSuccess(rtMap);
	}
	
	//上架装备
	@RequestMapping(value = "/deployComponent")
	Map<String, Object> deployComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long userComponentId=Long.valueOf(requestBodyParams.get("userComponentId").toString());
		long userCarId=Long.valueOf(requestBodyParams.get("userCarId").toString());
		long destroyComponentId=0;
		Map<String,Object> rtMap=componentService.deployComponent(userComponentId,userCarId,this.getUserId());
		return ResultUtils.rtSuccess(rtMap);
	}

	//零件合成几率查询
	@RequestMapping(value = "/getCompoundOdds")
	Map<String, Object> getCompoundOdds(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		String componentIds=String.valueOf(requestBodyParams.get("componentIds"));
		LOG.info("userId==="+this.getUserId()+"===componentIds=="+componentIds);
		Map<String,Object> rtMap=componentService.getCompoundOdds(componentIds);
		return ResultUtils.rtSuccess(rtMap);
	}

	//零件合成提交
	@RequestMapping(value = "/compoundComponent")
	Map<String, Object> compoundComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
								   @RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		String componentIds=String.valueOf(requestBodyParams.get("componentIds"));
		Integer stoveId=Integer.valueOf(requestBodyParams.get("stoveId").toString());
		LOG.info("userId==="+this.getUserId()+"===componentIds=="+componentIds+"===+stoveId=="+stoveId);
		Map<String,Object> rtMap=componentService.compoundComponent(this.getUserId(),componentIds,stoveId);
		return ResultUtils.rtSuccess(rtMap);
	}

	//零件合成情况查询
	@RequestMapping(value = "/queryCompoundStatus")
	Map<String, Object> queryCompoundStatus(@RequestParam (required = false) Map<String, String> allRequestParams,
										 @RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		Integer stoveId=Integer.valueOf(requestBodyParams.get("stoveId").toString());
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("userId==="+this.getUserId()+"===familyId=="+familyId+"===+stoveId=="+stoveId);
		Map<String,Object> rtMap=componentService.queryCompoundStatus(this.getUserId(),familyId,stoveId);
		return ResultUtils.rtSuccess(rtMap);
	}

	//合成--提交广告减少时间
	@RequestMapping(value = "/reduceCompoundTime")
	Map<String, Object> reduceCompoundTime(@RequestParam (required = false) Map<String, String> allRequestParams,
										 @RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		Integer stoveId=Integer.valueOf(requestBodyParams.get("stoveId").toString());
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("userId==="+this.getUserId()+"===familyId=="+familyId+"===+stoveId=="+stoveId);
		Map<String,Object> rtMap=componentService.reduceCompoundTime(this.getUserId(),familyId,stoveId);
		return ResultUtils.rtSuccess(rtMap);
	}

	//零件合成入库
	@RequestMapping(value = "/submitCompound")
	Map<String, Object> submitCompound(@RequestParam (required = false) Map<String, String> allRequestParams,
										 @RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		Integer stoveId=Integer.valueOf(requestBodyParams.get("stoveId").toString());
		LOG.info("userId==="+this.getUserId()+"===+stoveId=="+stoveId);
		Map<String,Object> rtMap=componentService.submitCompound(this.getUserId(),stoveId);
		return ResultUtils.rtSuccess(rtMap);
	}

	//熔炉情况查询
	@RequestMapping(value = "/queryStoveStatus")
	Map<String, Object> queryStoveStatus(@RequestParam (required = false) Map<String, String> allRequestParams,
											@RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		LOG.info("userId==="+this.getUserId()+"===familyId=="+familyId);
		Map<String,Object> rtMap=componentService.queryStoveStatus(this.getUserId(),familyId);
		return ResultUtils.rtSuccess(rtMap);
	}

	//出售装备
	@RequestMapping(value = "/sellComponent")
	Map<String, Object> sellComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
		long userComponentId=Long.valueOf(requestBodyParams.get("userComponentId").toString());
		Map<String,Object> rtMap=componentService.sellComponent(userComponentId,this.getUserId());
		if(rtMap==null){
			return ResultUtils.rtFailParam(null, "配件卖出失败");
		}
		return ResultUtils.rtSuccess(rtMap);
	}

	//配件贡献
	@RequestMapping(value = "/contributeComponent")
	Map<String, Object> contributeComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
									  @RequestBody  (required = false)  Map<String, Object> requestBodyParams){
		long userComponentId=Long.valueOf(requestBodyParams.get("userComponentId").toString());
		if (requestBodyParams.get("familyId")==null||requestBodyParams.get("familyId").equals("")) {
			return ResultUtils.rtFailParam(null, "familyId参数不能为空");
		}
		long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		Map<String,Object> rtMap=componentService.contributeComponent(userComponentId,this.getUserId(),familyId);
		if(rtMap==null){
			return ResultUtils.rtFailParam(null, "贡献失败！");
		}
		return ResultUtils.rtSuccess(rtMap);
	}
	
	//祈愿列表
	@RequestMapping(value = "/listPraying")
	Map<String, Object> listPraying(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
//		Long familyId=Long.valueOf(requestBodyParams.get("familyId").toString());
		Map<String,Object> rtMap=componentService.listPraying(this.getUserId(),this.getImgBasePath());
		if(rtMap==null){
			return ResultUtils.rtFailParam(null, "配件卖出失败");
		}
		return ResultUtils.rtSuccess(rtMap);
		}
	//配件字典表
	@RequestMapping(value = "/listDicComponent")
	Map<String, Object> listDicComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
	Map<String,Object> rtMap=new HashMap<>();
	List<Map<String,Object>> list=new ArrayList<>();
	DicComponentConstant.getDicComponent(1);
	Collection<DicComponent> c=DicComponentConstant.dicComponentMap.values();
	for(DicComponent comp:c){
		Integer id = comp.getComponentId();
		if(id == 10||id == 20||id == 30||id == 40||id == 50) {
			Map<String,Object> m=new HashMap<String,Object>();
			m.put("componentId", comp.getComponentId());
			m.put("imgUrl", comp.getComponentUrl());
			m.put("componentName", comp.getComponentValue());
			list.add(m);
		}
	}
	rtMap.put("components", list);
	 
	return ResultUtils.rtSuccess(rtMap);
	}
		
	
	//祈愿提交
	@RequestMapping(value = "/submitPraying")
	Map<String, Object> submitPraying(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
	Map<String,Object> rtMap=new HashMap<>();
	List<Map<String,Object>> list=new ArrayList<>();
	
	Integer componentId=Integer.valueOf(requestBodyParams.get("componentId").toString());
	rtMap.put("components", list);
	int insert= componentService.submitPraying(componentId, this.getUserId(),this.getUserInfo().getNickName());
	if(insert==0){
		return ResultUtils.rtFailParam(null,"今日已经祈愿过");
	}
	return ResultUtils.rtSuccess(null);
	}
	//零件库申请
	@RequestMapping(value = "/requestComponent")
	Map<String, Object> requestComponent(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
	Long familyComponentId=Long.valueOf(requestBodyParams.get("familyComponentId").toString());
	ReturnMessage msg= componentService.requestComponent(familyComponentId, this.getUserId(),this.getUserInfo().getNickName());
	return ResultUtils.rtJsonMap(msg);
	}
				
	//消息点击分配审核
	@RequestMapping(value = "/applyGiveLog")
	Map<String, Object> applyGiveLog(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
	Long componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
	int clickEvent=Integer.valueOf(requestBodyParams.get("clickEvent").toString());
	ReturnMessage msg= componentService.applyGiveLog(componentGiveLogId, clickEvent,this.getUserId());
	return ResultUtils.rtJsonMap(msg);
	}
	//选择列表
	@RequestMapping(value = "/prayingSelect")
	Map<String, Object> prayingSelect(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
	Long  componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
	 Map<String,Object> rtMap= componentService.prayingSelect(this.getUserId(),componentGiveLogId);
	return ResultUtils.rtSuccess(rtMap);
	}
	
	//同意赠予
	@RequestMapping(value = "/applyPraying")
	Map<String, Object> applyPraying(@RequestParam (required = false) Map<String, String> allRequestParams,
			@RequestBody  (required = false)  Map<String, Object> requestBodyParams){ 
	Long componentGiveLogId=Long.valueOf(requestBodyParams.get("componentGiveLogId").toString());
	Long userComponentId=Long.valueOf(requestBodyParams.get("userComponentId").toString());
	ReturnMessage msg= componentService.applyPraying(componentGiveLogId,userComponentId,this.getUserId(),this.getUserInfo().getNickName());
	return ResultUtils.rtJsonMap(msg);
	}
	
}
