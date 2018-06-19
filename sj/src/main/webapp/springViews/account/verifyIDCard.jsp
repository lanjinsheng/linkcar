<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%@page import = "com.ljs.util.CommentUtil" %>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
String accountUrl = CommentUtil.accountUrl;
%>
<html>
<head>
<title>身份证审核</title>
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
				title:"身份证审核",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=accountUrl%>/ment/getUserIDCards',
				rownumbers:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'用户昵称',field:'nikeName',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var nikeName=rowData.nikeName;
				      	return  nikeName;
					}},
					{title:'电话号码',field:'phone',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var phone=rowData.phone;
				      	return  phone;
					}},
					{title:'身份证正面',field:'frontDrivingImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var frontDrivingImg=rowData.frontDrivingImg;
					    if(frontDrivingImg==""){
					    	return "";
					    }
				      	var img = '<img style="width:30px; height:30px" src="' + frontDrivingImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'身份证背面',field:'backDrivingImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var backDrivingImg=rowData.backDrivingImg;
					    if(backDrivingImg==""){
					    	return "";
					    }
	                    var img = '<img style="width:30px; height:30px" src="' + backDrivingImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'操作',field:'userId',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var userId=rowData.userId;
	                    var frontDrivingImg=rowData.frontDrivingImg;
	                    var backDrivingImg = rowData.backDrivingImg;
	                   return "<span style=\"text-decoration:underline\" onclick=\"javascript:editIDCard('"+frontDrivingImg+"','"+backDrivingImg+"','"+userId+"');\"> 编辑</span>";
					}}
				]],
				onClickCell:function(rowIndex,field,value){
				},
				toolbar:"#tb",
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});

		function doSearch(){
			var nikeName = $("#nikeName").val();
			nikeName = $.trim(nikeName);
			var phone = $("#phone").val();
			phone = $.trim(phone);
			$("#table").datagrid('load',{
				nikeName:nikeName,				
				phone:phone				
			});
		}
		
		function verifySuccess(userId){
			var person = "<%=person %>";
        	var param="userId="+userId+"&operatingUser="+person;
        	$.ajax({
				type:'POST',
				url:"<%=accountUrl%>/ment/verifyLicenseDriver",
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.rtState == '1'){
						window.location.reload();
					}
					else{
						$.messager.alert("提示",rtJson.errorMsg);
						//window.close();
					}
				}
			});
        }
		
		function editIDCard(frontDrivingImg,backDrivingImg,userId) {
            showMyWindow("身份证操作",frontDrivingImg,backDrivingImg,userId,800,600);
        }
        
	    function showMyWindow(title,frontDrivingImg, backDrivingImg,userId, width, height) {  
	    	$('#frontDrivingImg').attr('src',frontDrivingImg);
	    	$('#backDrivingImg').attr('src',backDrivingImg);
	    	$('#userId').val(userId);
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
				url:'<%=accountUrl%>/ment/verifyIDCard',
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
			<div  style="margin-top: 20px;">
				<img id="frontDrivingImg" style="width:300px; height:200px;" onclick="javascript:window.open(this.src)">
				<img id="backDrivingImg" style="width:300px; height:200px;" onclick="javascript:window.open(this.src)">
			</div>
			<div>
				<form id="box" class="easyui-form" method="post"action=""> 
				<span style="color:red">*</span>姓名: <input type="text" name="userName" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				民族: <input type="text" name="nation" style="margin-top: 5px;"><br>
				&nbsp;性别: <input type="radio" name="sex" value="男">男<input type="radio" name="gender" value="女">女&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;
				出生: <input type="date" name="birthday" style="margin-top: 5px;"><br>
				&nbsp;住址: <input type="text" name="address" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				<span style="color:red">*</span>公民身份证号码: <input type="text" name="cardNumber" style="margin-top: 5px;"><br>
				&nbsp;机关: <input type="text" name="office" style="margin-top: 5px;"><br>
				<span style="color:red">*</span>有效起始日期: <input type="date" name="firstDay" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				<span style="color:red">*</span>证件失效日期: <input type="date" name="lastDay" style="margin-top: 5px;"><br>
				<input id="userId" type="hidden" name="userId" style="margin-top: 5px;">
				</form>
				<button onclick="myFunction()">提交</button>
			</div>
		</div>
	</div>
	<div id="tb" style="padding:3px;height:auto">
		<div align="right">
			用户昵称：<input  id="nikeName" name="nikeName" >&nbsp;&nbsp; 
			用户电话：<input  id="phone" name="phone" >&nbsp;&nbsp;
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch();"></a>
		</div>
	</div>
</body>
</html>
