<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
%>

<html>
<head>
	<title>好车族管理－上海数瑾</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=contextPath%>/public/bootstrap/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
	<link href="<%=contextPath%>/public/bootstrap/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
	<link href="<%=contextPath%>/public/bootstrap/assets/css/main.css" rel="stylesheet" type="text/css" />
	<script>
		function doLogOut(){
			if (!window.alertMsrg){
			    window.alertMsrg = "轻轻地您走了，正如您轻轻地来......";
			  }
			  if (confirm(alertMsrg)) {
				  $.ajax({
						type:'POST',
						url:"<%=contextPath %>/r/manage/login/userLogout",
						success:function(rtJson){
								window.location.href="<%=contextPath %>/login.jsp";
						}
					});
			  }
		}
		//点击用户编辑
		function editUser(){
			openDialogResize("<%=contextPath %>/springViews/system/editUserTop.jsp?id=<%=person_id %>",800, 600);			
		}
	</script>

</head>
<body>

	<div class="header">

		<div class="dl-title">
			后台管理－上海数瑾
		</div>

		<div class="dl-log">
			欢迎您，<span class="dl-log-user"><a href="javascript:editUser();" style="color:white"><b><%=sessionMap.get("truename") %></b></a></span>
			<a href="javascript:doLogOut();" title="退出系统" class="dl-log-quit">[退出]</a>
		</div>
	</div>
	<div class="content">
		<div class="dl-main-nav">
			<div class="dl-inform">
				<div class="dl-inform-title">
					<s class="dl-inform-icon dl-up"></s>
					
				</div>
			</div>
			<ul id="J_Nav" class="nav-list ks-clear">
				<li class='nav-item dl-selected'><div class='nav-item-inner nav-home'>首页</div></li>
			</ul>
		</div>
		<ul id="J_NavContent" class="dl-tab-conten">
		</ul>
	</div>
	
	<script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/bui-min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/common/main-min.js"></script>
	<script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/config-min.js"></script>
	<script>
		$(function(){
			$.ajax({
				type:'POST',
				url:'<%=contextPath %>/r/manage/core/getMenu1',
				success:function(rtJson){
					if(rtJson.rtState == '0'){
						var htmlStr = "";
						var data = eval(rtJson.rtData);
						console.log(data.length);
						for(var i=0;i<data.length;i++){
							if(i==0){
								htmlStr = htmlStr+"<li class='nav-item dl-selected'><div class='nav-item-inner "+data[i].icon+"'>"+data[i].text+"</div></li>";
							}else{
								htmlStr = htmlStr+"<li class='nav-item'><div class='nav-item-inner "+data[i].icon+"'>"+data[i].text+"</div></li>";
							}
						}
						$('#J_Nav').html(htmlStr);
						
						$.ajax({
							type:'POST',
							url:'<%=contextPath %>/r/manage/core/getShowMenu',
							success:function(rtJson){
								new PageUtil.MainPage({
									modulesConfig : rtJson
								});
							}
						});
					}else{
						$.messager.alert('提示','菜单加载出错！');
					}
				}
			});
		});
	</script>


</body>
</html>