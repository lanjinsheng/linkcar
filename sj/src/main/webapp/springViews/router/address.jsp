<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>地址管理</title>
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
				var address = encodeURIComponent($('#address').val());
				var clientId =$('#clientId').val();
	            if (data) {  
					$("#ff").ajaxSubmit({
							type:"post",
							url:"<%=contextPath%>/r/manage/view/updateAddress",
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
								<td align="right" nowrap class="td1">地址</td>
								<td class="td2" nowrap>
									<input id="icon" name="address" style="width:300px;" value="<%=request.getParameter("address")%>"/>
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
