<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<% 
	String master_id = request.getParameter("master_id") == null?"":request.getParameter("master_id");
%>
<html>
	<head>
		<title>用户数据权限管理</title>
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
			var slaveArr = new Array();
			$.ajax({
				type:'POST',
				url:'<%=contextPath %>/r/manage/group/getDataPriv?master_id=<%=master_id%>',
			    async:false,
				success:function(rtJson){
					if(rtJson.length  > 0){
						for(var i=0;i<rtJson.length;i++){
							slaveArr.push(rtJson[i].slave_id);
						}
					}
				}
			})
			$('#table').datagrid({
				title:"用户数据权限设置",
				iconCls:'icon-edit',
				url:'<%=contextPath %>/r/manage/group/listPageUser',
				loadMsg:'正在加载数据，请稍后......',
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
	                {field:'ck',checkbox:true,formatter:function(value,row,index){return true;}},
				    {title:'编号',field:'id',width:80,sortable:true,hidden:true}
				]],
				columns:[[
					{title:'用户名称',field:'username',width:100,align:'center'},
					{title:'真实姓名',field:'truename',width:100,align:'center'},
					{title:'所属部门',field:'deptName',width:100,align:'center'},
					{title:'角色',field:'privName',width:100,align:'center'}
				]],
				pageSize:10,
				pageList : [10,50,100],  
				pagination:true,
				onLoadSuccess:function(rtJson){
					if(rtJson.rtState== '1')
					{
						$.messager.alert('提示',rtJson.rtMsrg);
					}else{
						var data = $("#table").datagrid("getData");
						for(var i=0;i<data.total;i++){
							for(var j=0;j<slaveArr.length;j++){
								if(data.rows[i].id == slaveArr[j]){
					  				$("#table").datagrid("checkRow",i);
					  			}
							}
				  		}
					}
				},
				onDblClickRow:function(rowIndex,rowData){
					addOrEditUser(1,rowIndex);
				}
			});
			//--end 
		});
		
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
		
		function doSubmit(){
		  var slaves = "";
		  var rows = $("#table").datagrid("getChecked");
		  for(var i=0;i<rows.length;i++){
			 if(slaves!=""){
				 slaves+=";";
			 }
			 slaves += rows[i].id;
		  }
		  if(slaves == ""){
			  $.messager.alert('提示','请选择可以被查看的用户！');
			  return ;
		  }
		  var param = "master=<%=master_id%>&slaves="+slaves;
		  $.ajax({
			  type:'POST',
			  url:'<%=contextPath %>/r/manage/group/updateDataPriv',
			  data:param,
			  success:function(rtJson){
				  window.close();
			  }
		  });
		}
		
		</script>
	<body>
		<div id="table"  data-options="toolbar:'#tb',fit:true"></div>  
		  
	    <div id="tb" style="padding:3px;height:auto">
			<div style="float:left;">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok"  onclick="doSubmit();">选中用户</a>
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
