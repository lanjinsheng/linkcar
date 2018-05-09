<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>基础设备管理</title>
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
				title:"基础设备管理",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/routerBaseList?time=' + new Date().getTime(),
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
					{title:'有效时间',field:'validTime',width:100,align:'center'},
					{title:'初登时间',field:'ftime',width:50,align:'center'},
					{title:'最近登入(离线)时间',field:'ltime',width:50,align:'center',formatter:function(value,row,index){
						var status =row.status;
						if(status==0){
							return row.otime;
						}else if(status==1){
							return row.ltime;
						}else if(status==2){
							return row.ltime;
						}
						else
							return "--";
					}},
					{title:'WAN口ip',field:'ip',width:50,align:'center'},
					{title:'mac',field:'mac',width:80,align:'center'},
					{title:'公网ip',field:'pub_ip',width:50,align:'center'},
					{title:'位置',field:'location',width:100,align:'center'},

					{title:'状态',field:'status',width:50,align:'center',formatter:function(value,row,index){
						if(value==0){
							return "离线";
						}else if(value==1){
							return "在线";
						}else if(value==2){
							return "重登处理中";
						}
						else
							return "未知";
					}},
					
 
					
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
