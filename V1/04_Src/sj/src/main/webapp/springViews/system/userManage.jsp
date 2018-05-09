<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>

<html>
	<head>
		<title>用户管理</title>
	</head>
	<script type="text/javascript">
	
		$(function(){
			$("#searchValue").keypress(function(e) { 
		    	// 回车键事件 
		     	if(e.which == 13) { 
		     		 doSearch();
		       	} 
		   	}); 
			$("#dept_id").combotree({
				url:'<%=contextPath %>/r/manage/group/listDepartment',
				method:'POST',
				animate:true,
				lines:true,
				onChange:function(){
					doSearch();
				}
			});
			//加载角色
			$("#priv_id").combobox({
				url:"<%=contextPath %>/r/manage/group/listPriv",
				valueField:'id',
				textField:'name',
				onChange:function(){
					doSearch();
				}
			});
			$('#table').datagrid({
				iconCls:'icon-edit',
				url:'<%=contextPath %>/r/manage/group/listPageUser',
				loadMsg:'正在加载数据，请稍后......',
				fitColumns:true,
				frozenColumns:[[
	                {field:'ck',checkbox:true},
				    {title:'编号',field:'id',width:80,sortable:true,hidden:true}
				]],
				columns:[[
					{title:'排序号',field:'sort',width:50,align:'center'},
					{title:'用户名称',field:'username',width:200,align:'center'},
					{title:'真实姓名',field:'truename',width:100,align:'center'},
					{title:'所属部门',field:'deptName',width:100,align:'center'},
					{title:'角色',field:'privName',width:100,align:'center'},
					{title:'编辑',field:'edit',width:50,align:'center',formatter:function(value,rowDate,rowIndex){
						return "<input type='button'  style='border:0px #ff0000 solid;width:25px;height:15px;cursor:pointer'class='icon-modify'  onClick='addOrEditUser(1,\""+rowIndex+"\")'/>";
					}},
					{title:'数据权限',field:'setPriv',width:50,align:'center',formatter:function(value,rowDate,rowIndex){
						return "<input type='button'  style='border:0px #ff0000 solid;width:25px;height:15px;cursor:pointer'class='icon-details'  onClick='setMenu(\""+rowIndex+"\")'/>";
					}}
				]],
				pageSize:10,
				pageList : [10,50,100],  
				pagination:true,
				onLoadSuccess:function(rtJson){
					if(rtJson.rtState== '1')
					{
						$.messager.alert('提示',rtJson.rtMsrg);
					}
				},
				onDblClickRow:function(rowIndex,rowData){
					addOrEditUser(1,rowIndex);
				}
			});
			//--end 
		});
		
		function setMenu(rowIndex){
			var row = $('#table').datagrid("getRows")[rowIndex];
			var param = "master_id="+row.id;
			$.ajax({
				type:'POST',
				url:'<%=contextPath %>/r/manage/group/initDataPriv',
				data:param,
				success:function(rtJson){
					if(rtJson.rtState == '0'){
						openDialogResize("<%=contextPath %>/springViews/system/setDataPriv.jsp?"+param,1000, 800);
					}
				}
			});
		}
		
		function addOrEditUser(type,rowIndex){
			if(type==0){
				openDialogResize("<%=contextPath %>/springViews/system/addOrEditUser.jsp?callback=rtCallBack",800, 600);
			}else{
				var row = $('#table').datagrid("getRows")[rowIndex];
				openDialogResize('<%=contextPath %>/springViews/system/addOrEditUser.jsp?callback=rtCallBack&id='+row.id,800, 600);
			}
		}
		function rtCallBack(){
			$('#table').datagrid('reload');
		}
		
		function doSearch()
		{
			var dept_id = $("#dept_id").combotree('getValue');
			var priv_id = $("#priv_id").combotree('getValue');
			var searchValue = $('#searchValue').val();
			$('#table').datagrid('load',{
				searchValue:searchValue,
				dept_id:dept_id,
				priv_id:priv_id
			});
		}
		
		//伪删除
		function deleteUser1(){
			var rows=$('#table').datagrid('getSelections');
			var deleteVar="";
			if(rows.length==0)
			{
				$.messager.alert('提示','选中要删除的记录');  
				return ;
			}
			for(var i=0;i<rows.length;i++)
			{
				if(i==(rows.length-1))
				{
					deleteVar+=rows[i].id;
				}else
				{
					deleteVar+=rows[i].id+",";
				}
			}
			$.messager.confirm('确认','确认操作？',function(msg){
				if(msg){
					$.ajax({
						type:'POST',
						url:"<%=contextPath %>/r/manage/group/deleteUser1",
						data:"ids="+deleteVar,
						dataType:"json",
						success:function(rtJson){
							if(rtJson.rtState == '0'){
								table_reload(rows.length);
							}
							else{
								$.messager.alert('提示',rtJson.rtMsrg);
							}
						}
					});
				}
			});
		}
		function deleteUser(){
			var rows=$('#table').datagrid('getSelections');
			var deleteVar="";
			if(rows.length==0)
			{
				$.messager.alert('提示','选中要删除的记录');  
				return ;
			}
			for(var i=0;i<rows.length;i++)
			{
				if(i==(rows.length-1))
				{
					deleteVar+=rows[i].id;
				}else
				{
					deleteVar+=rows[i].id+",";
				}
			}
			$.messager.confirm('确认','您确认删除该记录信息？',function(msg){
				if(msg){
					$.ajax({
						type:'POST',
						url:"<%=contextPath %>/r/manage/group/deleteUser",
						data:"ids="+deleteVar,
						dataType:"json",
						success:function(rtJson){
							if(rtJson.rtState == '0'){
								table_reload(rows.length);
							}
							else{
								$.messager.alert('提示',rtJson.rtMsrg);
							}
						}
					});
				}
			});
		}
		</script>
	<body>
		<div id="table"  data-options="toolbar:'#tb'"></div>  
		  
	    <div id="tb" style="padding:3px;height:auto">
			<div style="float:left;">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addOrEditUser(0);">添加用户</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser1();">离职</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteUser();">删除</a>
			</div>
			<div align="right">
				所属部门：<input  id="dept_id" name="dept_id" class="easyui-combotree">&nbsp;&nbsp; 
				所属角色：<input  id="priv_id" name="priv_id" class="easyui-combotree">&nbsp;&nbsp; 
				查询条件：<input id="searchValue" name="searchValue" style="width:150px" title="用户名称或者用户真实姓名"/>
				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="doSearch();"></a>
			</div>
		</div>
		
	</body>
</html>
