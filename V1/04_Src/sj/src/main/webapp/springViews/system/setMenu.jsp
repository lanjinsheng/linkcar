<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>

<% 
String priv_id = request.getParameter("id") == null?"":request.getParameter("id");
String priv_name = request.getParameter("name") == null?"":request.getParameter("name");
String callback = request.getParameter("callback");
%>
<html>
	<head>
		<title>设置角色菜单权限</title>
	</head>
		<script type="text/javascript">
		$(function(){
			$("#tree").tree({
				url:'<%=contextPath %>/r/manage/group/getPrivMenuTree?priv_id=<%=priv_id%>',
				method:'POST',
				checkbox:true,
				animate:true,
				onLoadSuccess:function(node,rtJson){
					if(rtJson.rtState== '1')
					{
						$.messager.alert('提示：',rtJson.rtMsrg);
					}
				},
			});
		});
		
	function doSubmit(obj){
			var nodes = $('#tree').tree('getChecked');
			var treeId = '';
			for(var i=0; i<nodes.length; i++){
				if($('#tree').tree('isLeaf',nodes[i].target)){
					if (treeId!= '') treeId += ',';
					treeId += nodes[i].id;
				}
			}
			var para="priv_id=<%=priv_id%>&treeId="+treeId;
			jQuery(obj).hide();
			var url="<%=contextPath %>/r/manage/group/updatePrivMenu";
			jQuery.ajax({
				type : 'POST',
				url : url,
				data:para,
				success : function(jsonData){   
					if(jsonData.rtState == '0'){
						eval('window.opener.<%=callback%>();');
						window.close();
					}else{
						alert(rtJson.rtMsrg);
					}
					jQuery(obj).hide();
				}
			});
	}
	

	/*全选功能*/
	function chooseAll(){
		var nodes = $('#tree').tree('getRoots');
		for(var i =0;i<nodes.length;i++){
			var node = $('#tree').tree('find',nodes[i].id);
			$('#tree').tree('check',node.target);
		}
	}
	
	function clearAll(){
		var nodes = $('#tree').tree('getRoots');
		for(var i =0;i<nodes.length;i++){
			var node = $('#tree').tree('find',nodes[i].id);
			$('#tree').tree('uncheck',node.target);
		}
	}
	</script>
	<body>
	<table  style="border-collapse: collapse;" width="100%" border="1px" bordercolor="#0eb83a">
		<tr>
			<td nowrap>
				<span style="font-size:20px;font-weight: bold">设置角色菜单权限——(<%=priv_name %>)</sapan>
				<div id="OP_BTN" style="width:150px;position: fixed;top:8px;right:200px;">
					<input type="button" value="全选" onclick="chooseAll()"/>
					<input type="button" value="清空" onclick="clearAll()"/>
					<input type="button" onclick="doSubmit(this);" value="提交">
					<input type="button" onclick="javascript:window.close();" value="关闭">
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<ul id="tree"  class="easyui-tree" ></ul>
			</td>
		</tr>
		<tr>
		</tr>
	</table>
	</body>
</html>
