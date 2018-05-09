<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>在线数据查看</title>
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
				title:"channelMap",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/channelMapList?time=' + new Date().getTime(),
				rownumbers:true,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				columns:[[
					{title:'设备值',field:'client_ip',width:100,align:'center'},
					{title:'通道引用',field:'socket',width:50,align:'center'},	
					{title:'最后接收心跳时间',field:'lastTime',width:50,align:'center'}
				]],
				
				pageSize:1000,
				pageList : [500,1000,2000],  
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
