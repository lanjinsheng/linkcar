<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>健康日志管理</title>
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
				title:"健康日志查看",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/listPageRouterHealth?time=' + new Date().getTime(),
				rownumbers:true,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'设备',field:'client_id',width:100,align:'center'},
					{title:'cpu',field:'cpu',width:50,align:'center'},
					 
					{title:'memory',field:'memory',width:50,align:'center'},
					{title:'load Average1',field:'loadAverage1',width:50,align:'center'},
					{title:'load Average5',field:'loadAverage2',width:50,align:'center'},
					{title:'load Average15',field:'loadAverage3',width:100,align:'center'},

					{title:'ctime',field:'ctime',width:50,align:'center'} 
					
				]],
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});
 
		function rtCallBack(){
			$('#table').datagrid('reload');
		}
	 
		
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
	<div id="tb" style="padding:3px;height:auto">
	</div>
</body>
</html>
