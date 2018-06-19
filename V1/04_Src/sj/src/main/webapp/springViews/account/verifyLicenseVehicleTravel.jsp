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
<title>行驶证审核</title>
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
				title:"行驶证审核",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=accountUrl%>/ment/getPageUserLicenseVehicleTravels',
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
					{title:'行驶证照片',field:'frontTravelImg',width:50,align:'center',formatter:function(value,rowData,rowIndex){
					    var frontTravelImg=rowData.frontTravelImg;
					    if(frontTravelImg==""){
					    	return "";
					    }
	                    var img = '<img style="width:30px; height:30px" src="' + frontTravelImg + '" onclick="javascript:window.open(this.src)">';
				      	return  img;
					}},
					{title:'审核操作',field:'opearting',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var userId=rowData.userId;
					    var frontTravelImg=rowData.frontTravelImg;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:editLicenseVehicleTravel('"+frontTravelImg+"','"+userId+"');\"> 编辑</span>";
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
		
		function editLicenseVehicleTravel(frontTravelImg,userId) {
            showMyWindow("行驶证操作",frontTravelImg,userId,800,600);
        }
        
	    function showMyWindow(title,frontTravelImg,userId, width, height) {  
	    	$('#frontTravelImg').attr('src',frontTravelImg);
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
				url:'<%=accountUrl%>/ment/verifyLicenseVehicleTravel',
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
				<img id="frontTravelImg" style="width:300px; height:200px;" onclick="javascript:window.open(this.src)">
			</div>
			<div>
				<form id="box" class="easyui-form" method="post"action=""> 
				<span style="color:red">*</span>车牌号: <input type="text" name="plateNo" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				<span style="color:red">*</span>发动机号: <input type="text" name="engineNo" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				<span style="color:red">*</span>所属人: <input type="text" name="ownerName" style="margin-top: 5px;">
				<br>汽车类型: <input type="radio" name="carType" value="1">小型自动档汽车<input type="radio" name="carType" value="2">小型汽车<input type="radio" name="carType" value="3">中型客车<input type="radio" name="carType" value="4">大型客车<input type="radio" name="carType" value="5">牵引车<input type="radio" name="carType" value="6">城市公交车<input type="radio" name="carType" value="7">大型货车<input type="radio" name="carType" value="8">低速载货汽车<input type="radio" name="carType" value="9">三轮汽车
				<br>运营类型: <input type="radio" name="useType" value="FYY">非营运<input type="radio" name="useType" value="YY">营运
				<br>车辆型号: <input type="text" name="modelType" style="margin-top: 5px;">
				<br>vin: <input type="text" name="vin" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				备注: <input type="text" name="remark" style="margin-top: 5px;">
				<br><span style="color:red">*</span>发证日期: <input type="date" name="issueDate" style="margin-top: 5px;">&nbsp;&nbsp;&nbsp;
				<span style="color:red">*</span>注册日期: <input type="date" name="regDate" style="margin-top: 5px;"><br>
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