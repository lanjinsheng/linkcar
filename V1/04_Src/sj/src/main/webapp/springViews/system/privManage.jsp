<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>

<html>
	<head>
		<title>角色权限管理</title>
	</head>
	<script type="text/javascript">
	
		$(function(){
			$('#table').datagrid({
				title:'角色权限',
				idField:'id',
				iconCls:'icon-edit',
				rownumbers:true,
				url:'<%=contextPath %>/r/manage/group/listPagePriv',
				loadMsg:'正在加载数据，请稍后......',
				fitColumns:true,
				nowrap:false,
				frozenColumns:[[
	                {field:'ck',checkbox:true},
				    {title:'编号',field:'id',width:80,sortable:true,hidden:true}
				]],
				columns:[[
					{title:'排序号',field:'sort',width:50,align:'center'},
					{title:'角色名称',field:'name',width:100,align:'center'},
					{title:'编辑',field:'edit',width:50,align:'center',formatter:function(value,rowDate,rowIndex){
						return "<input type='button'  style='border:0px #ff0000 solid;width:25px;height:15px;cursor:pointer'class='icon-modify'  onClick='editPriv(\""+rowIndex+"\")'/>";
					}},
					{title:'设置权限',field:'setMenu',width:50,align:'center',formatter:function(value,rowDate,rowIndex){
						return "<input type='button'  style='border:0px #ff0000 solid;width:25px;height:15px;cursor:pointer'class='icon-details'  onClick='setMenu(\""+rowIndex+"\")'/>";
					}}
				]],
				pageSize:10,
				pageList : [10,50,100],  
				pagination:true,
				toolbar:[{
						id:'add',
						text:'新增',
						iconCls:"icon-add",
						handler:function(){
							openDialogResize("<%=contextPath %>/springViews/system/addPriv.jsp?callback=rtCallBack",800, 600);
						}
					},'-',
					{
					id:'delete',
					text:'删除',
					disabled:false,
					iconCls:'icon-remove',
					handler:function(){
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
										url:"<%=contextPath %>/r/manage/group/deletePriv",
										data:"ids="+deleteVar,
										dataType:"json",
										success:function(rtJson){
											if(rtJson.rtState == '0'){
												table_reload(rows.length);
											}
											else{
												warningInfo(rtJson.rtMsrg);
											}
										}
									});
								}
							});
					}
				}],
				onLoadSuccess:function(rtJson){
					if(rtJson.rtState== '1')
					{
						warningInfo(rtJson.rtMsrg);
					}
				}
			});
			//--end 
		});
		
		function rtCallBack(){
			$('#table').datagrid('reload');
		}
		function editPriv(rowIndex){
			var row = $('#table').datagrid("getRows")[rowIndex];
			var param = "id="+row.id+"&sort="+row.sort+"&name="+encodeURIComponent(row.name)+"&callback=rtCallBack";
			openDialogResize("<%=contextPath %>/springViews/system/editPriv.jsp?"+param,800, 600);
		}
		function setMenu(rowIndex){
			var row = $('#table').datagrid("getRows")[rowIndex];
			var param = "id="+row.id+"&name="+encodeURIComponent(row.name)+"&callback=rtCallBack";
			openDialogResize("<%=contextPath %>/springViews/system/setMenu.jsp?"+param,800, 600);
		}
	</script>
	<body>
		<div id="table"></div>
	</body>
</html>
