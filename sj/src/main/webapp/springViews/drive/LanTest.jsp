<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>数瑾駕駛日志管理</title>
</head>
<script type="text/javascript">
		$(function(){
		 
			$("#table").datagrid({
			 loader : function(param, success, error) {  
                   var opts = $(this).datagrid("options");  
		            if(!opts.url) {  
		                return false;  
		            }  
                $.ajax({  
                    type : opts.method,  
                    url : opts.url,  
                    dataType : 'json',  
                    contentType : 'application/json;charset=utf-8', // 设置请求头信息  
                    data : JSON.stringify(param),  
                    success : function(result) {         
                            success(result);                  
                    }  ,  
	                error: function() {  
	                    error.apply(this, arguments);  
	                }  
                });  
            },  
				title:"驾驶数据",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'http://product-col.idata365.com/v1/listPageDriveLog?time=' + new Date().getTime(),
				rownumbers:true,
				contentType : 'application/json;charset=utf-8', 
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'数据上传时间',field:'createTime',width:100,align:'center'},
					{title:'传感数据',field:'hadSensorData',width:100,align:'center'},
					{title:'用户Id',field:'userId',width:100,align:'center'},
					{title:'行程片段',field:'isEnd',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var isEnd=rowData.isEnd;
					   
					    if(isEnd==1){
					    	return "整行程";
					    } else{
					         return "片段行程";
					    }
					}},
					{title:'habitId',field:'habitId',width:50,align:'center'},
				    {title:'equipmentInfo',field:'equipmentInfo',width:150,align:'center'},
				    {title:'原始行程数据下载',field:'logPath1',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var uuid=rowData.id;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:downLoad1('"+uuid+"');\"> 下载该包</span>";
					}},
				    {title:'GPS',field:'logPath3',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var uuid=rowData.id;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:downLoad2('"+uuid+"');\"> 下载GPSexcel</span>";
					}},
					{title:'事件',field:'logPath4',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var uuid=rowData.id;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:downLoad4('"+uuid+"');\"> 下载Alarm-excel</span>";
					}},
					{title:'传感与屏幕亮度',field:'logPath5',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var uuid=rowData.id;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:downLoad3('"+uuid+"');\"> 下载传感及其他excel</span>";
					}},
					{title:'查看事件',field:'logPath6',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var userId=rowData.userId;
					    var habitId=rowData.habitId;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:viewEvent('"+userId+"','"+habitId+"');\"> 在线计算事件</span>";
					}},
					{title:'数据id',field:'数据id',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var id=rowData.id;
					    	return id;
					}} 
				]],
				toolbar:"#tb",
				pageSize:10,
				pageList : [2,10,50,100],  
				pagination:true
			});
		});

	   function downLoadSJ(uuid){
		 alert("开发中");
		}
		function doSearch(){
			var userId = $("#userId").val();
			userId = $.trim(userId);
			var phone = $("#phone").val();
			phone = $.trim(phone);
			var startTime = $("#startTime").datebox("getValue");
			startTime = $.trim(startTime);
			var endTime = $("#endTime").datebox("getValue");
			endTime = $.trim(endTime);
			$("#table").datagrid('load',{
				userId:userId,
				phone:phone,
				startTime:startTime,				
				endTime:endTime				
			});
		}
		function downLoad1(id){
			var pUrl="http://product-col.idata365.com/v1/downLoadDrive?id="+id;
			window.open(pUrl);
		}
		function downLoad2(id){
			var pUrl="http://product-col.idata365.com/v1/downLoadGpsExcel?id="+id;
			window.open(pUrl);
		}
		function downLoad3(id){
			var pUrl="http://product-col.idata365.com/v1/downLoadSensorExcel?id="+id;
			window.open(pUrl);
		}
	     function downLoad4(id){
			var pUrl="http://product-col.idata365.com/v1/downLoadAlarmExcel?id="+id;
			window.open(pUrl);
		}
		 function viewEvent(userId,habitId){
			var pUrl="http://product-col.idata365.com/v1/getDriveResultByUH?userId="+userId+"&habitId="+habitId;
			window.open(pUrl);
		}
		
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
		  
	<div id="tb" style="padding:3px;height:auto">
		<div align="right">
			用户ID：<input  id="userId" name="userId" >&nbsp;&nbsp; 
			用户电话：<input  id="phone" name="phone" >&nbsp;&nbsp;
			时间区间：<input id="startTime" class="easyui-datetimebox">--<input id="endTime" class="easyui-datetimebox">
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch();"></a>
		</div>
	</div>
</body>
</html>
