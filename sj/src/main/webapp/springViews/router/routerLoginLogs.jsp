<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>登入日志管理</title>
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
				title:"登入日志管理",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/routerLoginList?time=' + new Date().getTime(),
				rownumbers:true,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'设备',field:'client_ip',width:100,align:'center'},
					{title:'时间',field:'etime',width:100,align:'center'},
					{title:'事件',field:'status',width:50,align:'center',formatter:function(value,row,index){
						if(value==1){
							return "登入";
						}else if(value==0){
							return "离线";
						}else if(value==0){
							return "重登覆盖处理中";
						}
						else
							return "未知";
					}},
					{title:'描述',field:'remark',width:100,align:'center'},
					{title:'版本信息',field:'version',width:50,align:'center',formatter:function(value,row,index){
						if(row.status==1){
							return value;
						}
						else
							return "无";
					}}
					
				]],
				
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});
        
		
		
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
	<div id="tb" style="padding:3px;height:auto">
	</div>
</body>
</html>
