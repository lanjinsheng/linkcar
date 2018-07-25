<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%@page import = "com.ljs.util.CommentUtil" %>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
String shopUrl = CommentUtil.shopUrl;
%>
<html>
<head>
<title>商品兑换管理</title>
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
				title:"虚拟商品兑换管理",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=shopUrl%>/ment/getVirtualOrderPageList',
				rownumbers:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'交易时间',field:'convertTime',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var convertTime=rowData.convertTime;
	                    return convertTime;
					}},
					{title:'奖励名称',field:'rewardName',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var rewardName=rowData.rewardName;
	                    return rewardName;
					}},
					{title:'订单ID',field:'convertId',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var convertId=rowData.convertId;
	                    return convertId;
					}},
					{title:'用户名称',field:'userName',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var userName=rowData.userName;
	                    return userName;
					}},
					{title:'钻石数量',field:'diamondNum',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var diamondNum=rowData.diamondNum;
	                    return diamondNum;
					}},
					{title:'电话号码',field:'phone',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var phone=rowData.phone;
	                    return phone;
					}},
					{title:'订单数量',field:'convertNum',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var convertNum=rowData.convertNum;
	                    return convertNum;
					}},
					{title:'订单状态',field:'convertStatus',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var t='无';
					    if(value == '1'){
					    	t="待发放";
					    }else if(value == '2'){
					    	t="待收货";
					    }else if(value == '3'){
					    	t="待确认";
					    }else if(value == '4'){
					    	t="已完成";
					    }
	                    return t;
					}},
					{title:'操作',field:'opearting',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var convertId=rowData.convertId;
					    
					    if(rowData.auctionType==3){
					    	//现金红包
					    	if(rowData.convertStatus==1){
					    		return "<span style=\"text-decoration:underline\" onclick=\"javascript:writeCode("+convertId+");\"> 填写兑换码</span>";
					    	}else if(rowData.convertStatus==3){
					    		return "<span style=\"text-decoration:underline\" onclick=\"javascript:sendReward("+convertId+");\"> 确认完成</span>";
					    	}else{
					    		return ;
					    	}
					    	
					    }else{
					    	if(rowData.convertStatus!=1||rowData.phone==null||rowData.phone==""){
					    		return ;
					   		}else{
					   			return "<span style=\"text-decoration:underline\" onclick=\"javascript:sendReward("+convertId+");\"> 发货</span>";
					   		}
					    }
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
		function sendReward(convertId){
			var person = "<%=person %>";
        	var param="convertId="+convertId+"&operatingUser="+person;
        	$.ajax({
				type:'POST',
				url:"<%=shopUrl%>/ment/sendVirtualReward",
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.rtState == '1'){
						$.messager.alert("提示","操作成功");
		                setTimeout(function () {
		                    window.location.reload();
		                }, 1000);
					}
					else{
						$.messager.alert("提示",rtJson.errorMsg);
						//window.close();
					}
				}
			});
        }
		
		function writeCode(convertId) {
            showMyWindow("填写兑换码",convertId,800,600);
        }
        
	    function showMyWindow(convertId, width, height) {  
	    	$('#convertId').val(convertId);
	        $('#myWindow').window(  
	                        {  
	                            title : title,  
	                            width : width,  
	                            height : height,  
	                            modal : true,
	                            minimizable : true,  
	                            maximizable : true,  
	                            shadow : false,  
	                            cache : false,  
	                            closed : false,
	                            closable : true,
	                            draggable :true,
	                            collapsible : true,  
	                            resizable : true,  
	                            loadingMessage : '正在加载数据，请稍等片刻......'  
	                        });  
	    }
	    function myFunction(){
	    	var param = $("#box").serialize();
	    	var person = "<%=person %>";
        	param=param+"&operatingUser="+person;
		    $.ajax({
				type:'POST',
				url:"<%=shopUrl%>/ment/sendVirtualReward",
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.rtState == '1'){
						$.messager.alert("提示","操作成功");
		                setTimeout(function () {
		                    window.location.reload();
		                }, 1000);
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
	<div id="myWindow" class="easyui-dialog" closed="true">
		<div class="center">
			<div>
				<form id="box" class="easyui-form" method="post"action=""> 
				<span style="color:red">*</span>兑换码: <input type="text" name="userName" style="margin-top: 5px;"><br>
				<input id="cdkey" type="hidden" name="cdkey" style="margin-top: 5px;">
				</form>
				<button onclick="myFunction()">提交</button>
			</div>
		</div>
	</div>
</body>
</html>
