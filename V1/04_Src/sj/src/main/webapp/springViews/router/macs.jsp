<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>数据监控管理</title>
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
				title:"Macs日志管理",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/listPageMacs?time=' + new Date().getTime(),
				rownumbers:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'设备',field:'client_id',width:150,align:'center'},
					{title:'上传时间',field:'create_time',width:100,align:'center'},
					{title:'文件命名',field:'file_name',width:200,align:'center'},
					{title:'操作',field:'op',width:50,align:'center',formatter:function(value,row,index){
						var  op="<a href='javascript:viewMacs("+"\""+row.file_path+"/"+row.file_name+"\")'><font color='#dd0000'>查看</font></a> &nbsp;&nbsp;";
					    return op;
					}}
				
				]],
				onClickCell:function(rowIndex,field,value){
					
				},
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});
		function viewMacs(value){
			openDialogResize("<%=contextPath%>/r/manage/view/viewMacJson?fileName="+value);
		}
		function doSearch(){
			
		}
</script>
<body style="height: 97%">
	<div id="table"  data-options="fit:true"></div>
	 
	<div id="tb" style="padding:3px;height:auto">
	<!-- 	<div align="right">
			用户ID：<input  id="userId" name="userId" >&nbsp;&nbsp; 
			点火区间：<input id="startTime" class="easyui-datetimebox">--<input id="endTime" class="easyui-datetimebox">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch();"></a>
		</div>-->
	</div>
</body>
</html>
