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
					{title:'版本',field:'last_ver',width:50,align:'center'},
					{title:'位置',field:'location',width:100,align:'center',formatter:function(value,row,index){
					    if(row.location){
					    	return "<a href='javascript:goEditLocation("+"\""+row.client_ip+"\",\""+row.location+"\")'><font color='#dd0000'>"+row.location+"</font></a> &nbsp;&nbsp;";
						}
						else
							return "<a href='javascript:goEditLocation("+"\""+row.client_ip+"\",\"\")'><font color='#dd0000'>添加</font></a> &nbsp;&nbsp;";
					}},

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
					
					{title:'编辑',field:'edit',width:30,align:'center',formatter:function(value,row,index){
						return "<a href='javascript:goEditRouter("+"\""+row.client_ip+"\",\""+row.ip+"\",\""+row.pub_ip+"\",\""+row.location+"\",\""+row.command_source+"\",\""+row.command_source+"\")'><font color='#dd0000'>编辑</font></a> &nbsp;&nbsp;";
						
					}},
					{title:'指令操作',field:'op',width:30,align:'center',formatter:function(value,row,index){
					    if(row.status==1){
					    	return "<a href='javascript:reboot("+"\""+row.client_ip+"\""+")'><font color='#dd0000'>重启</font></a> &nbsp;&nbsp;";
						}
						else
							return "无";
					}}
					
				]],
				toolbar:[
							{
								id:'add',
								text:'添加设备',
								disabled:false,
								iconCls:'icon-add',
								handler:function(){	
									addRouter();
								}
							}
				],
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});
		function addRouter(){
		    var commandSource="router.ruhaoyi.com";
			openDialogResize("<%=contextPath%>/springViews/router/addEditeRouter.jsp?command_source="+commandSource,600, 600);
		}
		function goEditRouter(client_ip,ip,pub_ip,location,command_source){
			if(command_source=="" || command_source=='undefined'){
				command_source="router.ruhaoyi.com";
			}
			openDialogResize("<%=contextPath%>/springViews/router/addEditeRouter.jsp?client_ip="+client_ip+"&ip="+ip+"&pub_ip="+pub_ip+"&location="+location+"&command_source="+command_source,600, 600);

		}
		function rtCallBack(){
			$('#table').datagrid('reload');
		}
		//修改方法
		function goEditLocation(clientId,address)
		{
			openDialogResize("<%=contextPath%>/springViews/router/address.jsp?clientId="+clientId+"&address="+address,600, 300);
		}
		
        function reboot(clientId){
        	var param="clientId="+clientId;
        	$.ajax({
				type:'POST',
				url:contextPath+"/r/manage/view/rebootCommand",
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.status == '1'){
						$.messager.alert("提示","成功下发！");
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
	<div id="tb" style="padding:3px;height:auto">
	</div>
</body>
</html>
