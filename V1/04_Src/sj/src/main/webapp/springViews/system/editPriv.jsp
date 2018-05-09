<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%
	String id = request.getParameter("id");
	String sort = request.getParameter("sort");
	String name = request.getParameter("name");
	String callback = request.getParameter("callback");
%>
<html>
	<head>
		<title>添加角色</title>
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
		</style>
	</head>
	<script type="text/javascript">	
	$(function(){
		$('#name').val('<%=name%>');
		$('#sort').numberbox('setValue','<%=sort%>');
		
		$("#name").keypress(function(e) { 
    	// 回车键事件 
     	  if(e.which == 13) { 
   			doSubmit();
       		} 
   		}); 
	});
         function doSubmit(obj){
			if($("#sort").val() == ""){
				$.messager.alert('提示','排序号不能为空');
				$("#sort").focus();
				return;
			}
			if($("#name").val() == ""){
				$.messager.alert('提示','角色名称不能为空');
				$("#name").focus();
				return;
			}
			var param = "id=<%=id%>&sort="+$("#sort").val()+"&name="+$('#name').val();
			$(obj).hide();
			$.ajax({
				url:"<%=contextPath %>/r/manage/group/updatePriv",
				type:'POST',
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.rtState == '0'){
						eval('window.opener.<%=callback%>();');
						window.close();
					}
					else{
						$.messager.alert('提示',rtJson.rtMsrg);
					}
					$(obj).show();
				}
			});
		}
	</script>
	<body>
		<table width="90%">
		<tr>
			<td class="TableData" style="padding-left:50px" >
				<span style="padding-bottom: 10px;color:orange;font-weight: bolder;">新建角色</span>
			</td>
		</table>
		<div style="width: 90%;padding-left:50px">
			<table  style="border-collapse: collapse;" width="100%" border="1px" bordercolor="#0eb83a">
				<tr>
					<td align="right"  nowrap class="td1">
						排序号：
					</td>
					<td class="td2" nowrap>
						<input type="text" id="sort" name="sort" class="easyui-numberbox">
						<font color="red" size="2px">*</font>
					</td>
					</tr>
					<tr>
					<td align="right" nowrap class="td1">
						角色名称：
					</td>
					<td class="td2" nowrap>
						<input type="text" id="name" name="name" >
						<font color="red" size="2px">*</font>
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="td1">
					</td>
					<td  nowrap >
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false" onclick="doSubmit(this);">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false" onclick="javascript:window.close();">关闭</a>
					</td>
				</tr>
 			</table>
		</div>
	</body>
</html>