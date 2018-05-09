<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>内存校验数据查看</title>
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
				url:'<%=contextPath%>/r/manage/view/listPageValidMap?time=' + new Date().getTime(),
				rownumbers:true,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				columns:[[
					{title:'key',field:'key',width:100,align:'center'},
					{title:'value',field:'value',width:50,align:'center'}
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
