<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>增加转发管理</title>
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
				//var location = encodeURIComponent($('#location').val());
				var id =$('#id').val();
				var fromUrl =$('#fromUrl').val();
				var toUrl =$('#toUrl').val();
				//var command_source =$('#command_source').val();
	            if (data) {  
					$("#ff").ajaxSubmit({
							type:"post",
							url:"<%=contextPath%>/r/manage/view/insertOrUpdateUrl",
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
				<input id="id" name="id" type="hidden" value="<%=request.getParameter("id")%>"/>
				<tr>
					<td>
					  该映射使用的二级域名默认是http://hd.idata365.com,如需修改，请联系开发人员进行配置 
					</td>
				</tr>
				<tr>
					<td>
						<table style="border-collapse: collapse;" width="100%"
							border="1px" bordercolor="#0eb83a">
						     <tr>
								<td align="right" nowrap class="td1">相对URI</td>
								<td class="td2" nowrap>
									<input id="fromUrl" name="fromUrl" style="width:500px;" value="<%=request.getParameter("fromUrl")==null?"":request.getParameter("fromUrl")%>"/>
								</td>
							</tr>
							<tr>
								<td align="right" nowrap class="td1">转发目的地址</td>
								<td class="td2" nowrap>
								<input class="easyui-textbox" name="toUrl"  data-options="multiline:true" value="<%=request.getParameter("toUrl")==null?"":request.getParameter("toUrl")%>" style="width:500px;height:300px">
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
