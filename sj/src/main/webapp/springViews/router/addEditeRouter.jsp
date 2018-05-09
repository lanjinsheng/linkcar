<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>增加设备管理</title>
<style>
	.td1{
		padding-top:10px;
		padding-bottom:5px;
		font-size: 12px;
		background-color: #c0ebd7;
		width:20%;
	}
	.td2{
		padding:5px;
	}
	.tdg{
	padding-top:10px;
	padding-bottom:5px;
	font-size: 12px;
	background-color: #c0ebd7;
	width:20%;
	}
	.tdb{
		padding:5px;
		width:30%;
	}
</style>
<script type="text/javascript">	
		
		function doSubmit(obj){
			$(obj).hide();
			$.messager.confirm("操作提示", "您确定要提交吗？", function (data) {  
				var location = encodeURIComponent($('#location').val());
				var client_ip =$('#client_ip').val();
				var ip =$('#ip').val();
				var pub_ip =$('#pub_ip').val();
				var command_source =$('#command_source').val();
	            if (data) {  
					$("#ff").ajaxSubmit({
							type:"post",
							url:"<%=contextPath%>/r/manage/view/addOrUpdateRouter",
							success:function(rtJson){
								$(obj).show();
								if (rtJson.status == '1'){
									eval('window.opener.rtCallBack();');
									window.close();
								} 
								else if (rtJson.status == '0')
								{
									$.messager.alert("操作提示！",rtJson.errorMsg);
								}
						}
				});
			   }
			   else
			   {
			   	$(obj).show();
			   }  
	        });  
	}
	
</script>
</head>
<body>
	<form id="ff" name="ff" method="post" enctype="multipart/form-data" >
		<div style="width:90%;padding-left:50px;margin-top:50px">
			<table style="border-collapse: collapse;" width="100%" border="1px"
				bordercolor="#0eb83a">
				<input id="clientId" name="clientId" type="hidden" value="<%=request.getParameter("clientId")%>"/>
				<tr>
					<td>
						<table style="border-collapse: collapse;" width="100%"
							border="1px" bordercolor="#0eb83a">
						     <tr>
								<td align="right" nowrap class="td1">设备号</td>
								<td class="td2" nowrap>
									<input id="icon" name="client_ip" style="width:300px;" value="<%=request.getParameter("client_ip")==null?"":request.getParameter("client_ip")%>"/>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap class="td1">ip</td>
								<td class="td2" nowrap>
									<input id="icon" name="ip" style="width:300px;" value="<%=request.getParameter("ip")==null?"":request.getParameter("ip")%>"/>
								</td>
							</tr>	
							<tr>
								<td align="right" nowrap class="td1">公网ip</td>
								<td class="td2" nowrap>
									<input id="icon" name="pub_ip" style="width:300px;" value="<%=request.getParameter("pub_ip")==null?"":request.getParameter("pub_ip")%>"/>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap class="td1">地址</td>
								<td class="td2" nowrap>
									<input id="icon" name="location" style="width:300px;" value="<%=request.getParameter("location")==null?"":request.getParameter("location")%>"/>
								</td>
							</tr>	
							<tr>
								<td align="right" nowrap class="td1">指令来源</td>
								<td class="td2" nowrap>
									<input id="icon" name="command_source" style="width:300px;" value="<%=request.getParameter("command_source")==null?"":request.getParameter("command_source")%>"/>
								</td>
							</tr>					
						</table>
					</td>
				</tr>
				<tr>
					<td nowrap align="center">
					<a	href="javascript:void(0)" class="easyui-linkbutton"
						data-options="iconCls:'icon-save',plain:false"
						onclick="doSubmit(this);">提交</a>
				    <a href="javascript:void(0)"
						class="easyui-linkbutton"
						data-options="iconCls:'icon-cancel',plain:false"
						onclick="javascript:window.close();">关闭</a>
					</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
