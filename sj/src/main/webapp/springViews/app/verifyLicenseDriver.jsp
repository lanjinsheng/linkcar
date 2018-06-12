<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%@page import = "com.ljs.util.CommentUtil" %>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
String appUrl = CommentUtil.appUrl;
%>
<html>
<head>
<title>驾驶证审核</title>
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
				title:"驾驶证审核",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=appUrl%>/ment/getUserLicenseDrivers',
				rownumbers:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'用户名',field:'userName',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var userName=rowData.userName;
	                    return userName;
					}},
					{title:'性别',field:'gender',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var t='无';
					    if(value == 'M'){
					    	t="男";
					    }else if(value == 'F'){
					    	t="女";
					    }
	                    return t;
					}},
					{title:'出生日期',field:'birthday',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var birthday=rowData.birthday;
	                    return birthday;
					}},
					{title:'国籍',field:'nation',width:50,align:'center',formatter:function(value,rowData,rowIndex){
	                    var t='无';
					    if(value == 'C'){
					    	t="中国";
					    }else if(value == 'B'){
					    	t="其他";
					    }
	                    return t;
					}},
					{title:'驾驶证正面',field:'frontDrivingImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var frontDrivingImg=rowData.frontDrivingImg;
					    if(frontDrivingImg==""){
					    	return "";
					    }
				      	var img = '<img style="width:30px; height:30px" src="' + frontDrivingImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'驾驶证背面',field:'backDrivingImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var backDrivingImg=rowData.backDrivingImg;
					    if(backDrivingImg==""){
					    	return "";
					    }
	                    var img = '<img style="width:30px; height:30px" src="' + backDrivingImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'初次领证日期',field:'virginDay',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var virginDay=rowData.virginDay;
	                    return virginDay;
					}},
					{title:'有效起始日期',field:'validDay',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var validDay=rowData.validDay;
	                    return validDay;
					}},
					{title:'有效年限',field:'validYears',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var validYears=rowData.validYears;
	                    return validYears;
					}},
					{title:'车辆类型',field:'driveCardType',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var driveCardType=rowData.driveCardType;
	                    return driveCardType;
					}},
					{title:'可编辑',field:'isDrivingEdit',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var t='无';
					    if(value == '1'){
					    	t="是";
					    }else if(value == '0'){
					    	t="否";
					    }
	                    return t;
					}},
					{title:'审核操作',field:'userId',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var userId=rowData.userId;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:verifySuccess("+userId+");\"> 通过</span>";
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
		function verifySuccess(userId){
			var person = "<%=person %>";
        	var param="userId="+userId+"&operatingUser="+person;
        	$.ajax({
				type:'POST',
				url:"<%=appUrl%>/ment/verifyLicenseDriver",
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
