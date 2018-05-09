<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>更新日志任务管理</title>
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
				title:"任务管理",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/updateTaskList?time=' + new Date().getTime(),
				rownumbers:true,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'任务号',field:'uuid',width:100,align:'center'},
					{title:'设备',field:'client_id',width:100,align:'center'},
					{title:'版本路径',field:'ftp_url',width:50,align:'center'},
					{title:'任务创建时间',field:'create_time',width:100,align:'center'},
					{title:'终端反馈时间',field:'response_time',width:100,align:'center'},
					{title:'任务状态',field:'status',width:50,align:'center',formatter:function(value,row,index){
						if(value==0){
							return "任务下发中";
						}else if(value==-1){
							return "任务下发失败";
						}else if(value==1){
							return "任务成功接收";
						}else if(value==2){
							return "路由接收指令失败";
						}else if(value==3){
							return "路由升级成功";
						}
						else
							return "未知";
					}}
					
				]],
				toolbar:[
							{
								id:'add',
								text:'增加升级任务',
								disabled:false,
								iconCls:'icon-add',
								handler:function(){
									
									openW();
								}
							}
					   ],
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});
        function openW(id,vin){
        	window.open("<%=contextPath%>/r/manage/view/toDeal?callback=reload");
        }
       
		function reload(){
			$('#table').datagrid('reload');
		}
		
		
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
	<div id="tb" style="padding:3px;height:auto">
	</div>
</body>
</html>
