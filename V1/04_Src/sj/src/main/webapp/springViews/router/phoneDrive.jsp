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
				title:"日志接受管理",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/netOperaList?time=' + new Date().getTime(),
				rownumbers:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'处理时间',field:'ctime',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var startTime=rowData.ctime;
	                    return startTime;
					}},
					{title:'T',field:'type',width:30,align:'center',formatter:function(value,rowData,rowIndex){
	                    var t='无';
	                    if(value=='01'){
	                    	t="登入";
	                    }else if(value=='02'){
	                    	t="ping";
	                    }else if(value=='03'){
	                    	t="路由认证";
	                    }else if(value=='04'){
	                    	t="更新";
	                    }else if(value=='05'){
	                    	t="ping";
	                    }else if(value=='06'){
	                    	t="重启";
	                    }else if(value=='08'){
	                    	t="踢用户";
	                    }else if(value=='0C'){
	                    	t="免认证";
	                    }else if(value=='0D'){
	                    	t="黑名单";
	                    }else if(value=='0E'){
	                    	t="白名单";
	                    }else if(value=='0F'){
	                    	t="资源配置";
	                    }else if(value=='10'){
	                    	t="MACS上传";
	                    }else if(value=='11'){
	                    	t="健康数据";
	                    }
	                    else if(value=='1001'){
	                    	t="理想认证";
	                    }else if(value=='1002'){
	                    	t="理想踢用户";
	                    }else if(value=='1003'){
	                    	t="理想黑名单";
	                    }else if(value=='1004'){
	                    	t="理想白名单";
	                    }else if(value=='1005'){
	                    	t="理想免认证";
	                    }else{
	                    	t=value;
	                    }
	                    return  t ;
					}},
					{title:'类型',field:'recieve_or_send',width:20,align:'center',formatter:function(value,rowData,rowIndex){
	                    var type='无';
	                    if(rowData.recieve_or_send==1){
	                    	type="接收";
	                    }else if(rowData.recieve_or_send==2){
	                    	type="发送";
	                    }
	                    return  type ;
					}},
					{title:'报文1',field:'log_remark',width:400,align:'center',editor:{
						type:'textarea'
					},formatter:function(value,rowData,rowIndex){
						 return  '<div style="width:100%;display:block;word-break: break-all;word-wrap: break-word">'+value+'</div>';
	                   
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
