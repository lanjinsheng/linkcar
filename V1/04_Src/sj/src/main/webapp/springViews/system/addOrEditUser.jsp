<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%
    String callback = request.getParameter("callback");
    String id = request.getParameter("id") == null ? "" : request.getParameter("id");
    boolean isAdd = "".equals(id);
%>

<html>
	<head>
		<title><%=isAdd?"新建用户":"编辑用户" %></title>
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
				width:30%;
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
	</head>
	<script type="text/javascript">	
		$(function(){
			//加载部门
			$("#dept_id").combotree({
				url:'<%=contextPath %>/r/manage/group/listDepartment',
				method:'POST',
				animate:true,
				lines:true
			});
			//加载角色
			$("#priv_id").combobox({
				url:"<%=contextPath %>/r/manage/group/listPriv",
				valueField:'id',
				textField:'name'
			});
			<%=isAdd?"":"$('#fm').form('load','"+contextPath+"/r/manage/group/getUserById?id="+id+"');"%>
		});
		
		function doSubmit(obj){
			$("#fm").form('submit',{
				url:"<%=contextPath %>/r/manage/group/<%=isAdd ? "addUser" : "updateUser?id=" + id%>",
				onSubmit:function(){
					var isValid = $(this).form('validate');
					if(isValid){
						$(obj).hide();
					}
					return isValid;
				},
				success:function(rtJson){
					rtJson = eval('(' + rtJson + ')'); 
					if (rtJson.rtState == '0'){
						eval('window.opener.<%=callback%>();');
						window.close();
					}else{
						$(obj).show();
						warningInfo(rtJson.rtMsrg);
					}
				}
			});
		}
	</script>
	<body>
		<form id="fm" method="post">
		<div style="width:90%;padding-left:50px;margin-top:50px">
			<table  style="border-collapse: collapse;" width="100%" border="1px" bordercolor="#0eb83a">
				<tr>
					<td align="right"  nowrap class="td1">
						用户名称：
					</td>
					<td class="td2" nowrap>
						<input type="text" id="username" name="username" class="easyui-validatebox" data-options="required:true">
					</td>
					<td align="right" nowrap class="td1">
						真实姓名：
					</td>
					<td class="td2" nowrap>
						<input type="text" id="truename" name="truename" class="easyui-validatebox" data-options="required:true">
					</td>
					<td align="right"  class="td1" nowrap>
						登录密码：
					</td>
					<td  class="td2" nowrap>
						<input type="password" id="password" name="password">
					</td>
				</tr>
				
				<tr>
				<td align="right"  class="td1" nowrap>
						排序号：
					</td>
					<td  class="td2" nowrap>
						<input id="sort" name="sort" class="easyui-numberbox"  data-options="required:true"/> 
					</td>
					<td align="right"  nowrap class="td1">
						所在部门：
					</td>
					<td class="td2" nowrap >
						<input id="dept_id" name="dept_id" class="easyui-combotree" style="width:160px;" data-options="required:true"/>
					</td>
					<td align="right" nowrap class="td1">
						角色名称：
					</td>
					<td class="td2" nowrap>
						<input id="priv_id" name="priv_id" class="easyui-combobox" style="width:160px;" data-options="required:true"/>
					</td>
				</tr>
				
				<tr>
					<td align="right"  class="td1" nowrap>
						性别：
					</td>
					<td  class="td2" nowrap>
						<select id="sex" name="sex">
								<option value="0">
									男
								</option >
								<option value="1">
									女
								</option>
						</select>
					</td>
					<td align="right"  class="td1" nowrap>
						出生日期：
					</td>
					<td class="td2" colspan="3">
						<input id="birthday" name="birthday" class="easyui-datebox" >
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="td1">
						TEL：
					</td>
					<td  class="td2" nowrap colspan="5"> 
						<input type="text" id="contact" name="contact" size="50">
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="td1">
						MAIL：
					</td>
					<td class="td2" nowrap colspan="5">
						<input type="text" id="email" name="email" size="50">
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="td1">
						FAX：
					</td>
					<td class="td2" nowrap colspan="5">
						<input type="text" id="fax" name="fax" size="50">
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="td1">
					</td>
					<td  nowrap colspan="5">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false" onclick="doSubmit(this);">保存</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false" onclick="javascript:window.close();">关闭</a>
					</td>
				</tr>
				
 			</table>
		</div>
		</form>
	</body>
</html>
