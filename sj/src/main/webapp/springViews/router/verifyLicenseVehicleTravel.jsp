<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
%>
<html>
<head>
<title>行驶证审核</title>
</head>
<script type="text/javascript">
		$(function(){
			$("#box_id").keypress(function(e) { 
	    	// 回车键事件 
	     	if(e.which == 13) { 
	     		 doSearch();
	       	} 
	   	}); 
			$("#table").datagrid({
				title:"行驶证审核",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'http://127.0.0.1:8082/ment/getPageUserLicenseVehicleTravels',
				rownumbers:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'发动机号',field:'engineNo',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var engineNo=rowData.engineNo;
	                    return engineNo;
					}},
					{title:'车牌号',field:'plateNo',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var plateNo=rowData.plateNo;
	                    return plateNo;
					}},
					{title:'营运类型',field:'userTypeDesc',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var userTypeDesc=rowData.userTypeDesc;
	                    return userTypeDesc;
					}},
					{title:'车辆类型',field:'modelTypeDesc',width:50,align:'center',formatter:function(value,rowData,rowIndex){
	                    var modelTypeDesc=rowData.modelTypeDesc;
	                    return modelTypeDesc;
					}},
					{title:'汽车类型',field:'cardTypeDesc',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var cardTypeDesc=rowData.cardTypeDesc;
	                    return cardTypeDesc;
					}},
					{title:'vin码',field:'vin',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var vin=rowData.vin;
	                    return vin;
					}},
					{title:'行驶证正面',field:'frontTravelImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var frontTravelImg=rowData.frontTravelImg;
	                    var img = '<img style="width:30px; height:30px" src="' + frontTravelImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'行驶证背面',field:'backTravelImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var backTravelImg=rowData.backTravelImg;
	                    var img = '<img style="width:30px; height:30px" src="' + backTravelImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'可编辑',field:'isTravelEdit',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var t='无';
					    if(value == '1'){
					    	t="是";
					    }else if(value == '0'){
					    	t="否";
					    }
	                    return t;
					}},
					{title:'审核操作',field:'opearting',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var plateNo=rowData.plateNo;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:verifySuccess('"+plateNo+"');\"> 通过</span>";
					}}
				
				]],
				onClickCell:function(rowIndex,field,value){
					if (lastIndex != rowIndex){
						jQuery('#table').datagrid('endEdit', lastIndex);
						jQuery('#table').datagrid('beginEdit', rowIndex);
						var ed = jQuery('#table').datagrid('getEditor', {index:rowIndex,field:field});
						if(ed == null){
							ed = jQuery('#table').datagrid('getEditor', {index:rowIndex,field:'id'});
						}
						jQuery(ed.target).select();
					}
					lastIndex = rowIndex;
				},
				toolbar:"#tb",
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});

		function doSearch(){
			var userId = $("#userId").val();
			userId = $.trim(userId);
			var startTime = $("#startTime").datebox("getValue");
			startTime = $.trim(startTime);
			var endTime = $("#endTime").datebox("getValue");
			endTime = $.trim(endTime);
			$("#table").datagrid('load',{
				userId:userId,				
				startTime:startTime,				
				endTime:endTime				
			});
		}
		function verifySuccess(plateNo){
			var person = "<%=person %>";
        	var param="plateNo="+plateNo+"&operatingUser="+person;
        	$.ajax({
				type:'POST',
				url:"http://127.0.0.1:8082/ment/verifyLicenseVehicleTravel",
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.rtState == '1'){
						window.location.reload();
					}
					else{
						$.messager.alert("提示",rtJson.errorMsg);
						//window.close();
					}
				}
			});
        }
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
	<!--  
	<div id="tb" style="padding:3px;height:auto">
		<div align="right">
			用户ID：<input  id="userId" name="userId" >&nbsp;&nbsp; 
			点火区间：<input id="startTime" class="easyui-datetimebox">--<input id="endTime" class="easyui-datetimebox">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch();"></a>
		</div>
	</div>-->
</body>
</html>
