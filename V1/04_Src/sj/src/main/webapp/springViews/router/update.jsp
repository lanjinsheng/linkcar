<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<% String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");%>
<html>
	<head>
		<title>任务处理</title>
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
	<script type="text/javascript">
		var lastIndex;
		$(function(){
		       jQuery('#table').datagrid({
		    		url:'<%=contextPath%>/r/manage/view/onLineList',
					rownumbers:true,
					fitColumns:true,
					frozenColumns:[[
						{field:'ck',checkbox:true}
					]],
					columns:[[
								{title:'在线设备',field:'clientId',width:100,align:'center',editor:{
									type:'text'
								}},
								{title:'ip',field:'ip',width:100,align:'center',editor:{
									type:'text'
								}},
								{title:'当前版本',field:'last_ver',width:100,align:'center',editor:{
									type:'text'
								}}
		            ]],
					onBeforeLoad:function(){
						//jQuery(this).datagrid('rejectChanges');
					},
					onLoadSuccess:function(data){
						jQuery('#table').datagrid('selectAll');
						lastIndex = $('#table').datagrid('getRows').length-1;
					},
					onClickCell:function(rowIndex,field,value){
						if (lastIndex != rowIndex){
							jQuery('#table').datagrid('endEdit', lastIndex);
							jQuery('#table').datagrid('beginEdit', rowIndex);
							var ed = jQuery('#table').datagrid('getEditor', {index:rowIndex,field:field});
							if(ed == null){
								ed = jQuery('#table').datagrid('getEditor', {index:rowIndex,field:'clientId'});
							}
							jQuery(ed.target).select();
						}
						lastIndex = rowIndex;
					},
					onAfterEdit:function(rowIndex, rowData, changes){
					},
					toolbar:[
						{
							id:'add',
							text:'增加在线信息',
							disabled:false,
							iconCls:'icon-add',
							handler:function(){
								
								addRow();
							}
						},'-',{
						id:'delete',
						text:'删除',
						disabled:false,
						iconCls:'icon-remove',
						handler:function(){
							jQuery('#table').datagrid('acceptChanges');
							lastIndex =-1;
							var rows = jQuery('#table').datagrid('getSelections');
							alert(rows.length);
							if (rows.length > 0){
								for(var i=0;i<rows.length;i++){
									var row = rows[i];
									var index = jQuery('#table').datagrid('getRowIndex', row);
									jQuery('#table').datagrid('deleteRow', index);
								}
							}
							var data = jQuery('#table').datagrid('getData');
							jQuery('#table').datagrid('loadData',data);
						}
					}
				   ]
				});
		});
		
		
		function addRow(){
			$('#table').datagrid('endEdit', lastIndex);
			$('#table').datagrid('appendRow',{
			});
			var data = $('#table').datagrid('getData');
			$('#table').datagrid('loadData',data);
			lastIndex = $('#table').datagrid('getRows').length-1;
			$('#table').datagrid('selectRow', lastIndex);
			$('#table').datagrid('beginEdit', lastIndex);
		}
		function jsonToStr(o){
			return JSON.stringify(o);
		}		
		function addGDAdvances(obj){
			//自动提交数据
			$('#table').datagrid('acceptChanges');
			lastIndex =-1;
			//自动提交数据
			if($('#ff').form('validate')){
				var products="";
				var jsondata=jQuery('#table').datagrid('getSelections');
				for(var i=0;i<jsondata.length;i++){
					if(products!=""){
						products+=";";
					}
					products+=jsonToStr(jsondata[i]);
					
				}
				var ftpUrl = encodeURIComponent($('#ftpUrl').val());
				var ftpUser =$('#ftpUser').textbox('getValue');
				var ftpMM = $('#ftpMM').textbox('getValue');
				var updateTime ='';
				var param="ftpUser="+ftpUser+"&ftpMM="+ftpMM+"&updateTime"+updateTime+"&ftpUrl="+ftpUrl+"&products="+products;
				$.ajax({
					type:'POST',
					url:contextPath+"/r/manage/view/updateCommand",
					data:param,
					dataType:'json',
					success:function(rtJson){
						if(rtJson.status == '1'){
							$.messager.alert("提示","成功");
							eval('window.opener.<%=callback%>();');
							window.close();
						}
						else{
							$.messager.alert("提示",rtJson.errorMsg);
							//window.close();
						}
					}
				});
			}
		}
	
	</script>
	</head>
	<body>
		<div style="width: 97%;padding-left:10px">
		<form id="ff">
			<table  style="border-collapse: collapse;" width="100%" border="1px" bordercolor="#0eb83a">
				<tr>
					<td align="right" class="td1" nowrap>ftp版本全路径：</td>
					<td class="td2"  colspan='3'>
						<textarea id="ftpUrl" name="ftpUrl"  style="height:100%;width:99%" rows="1"></textarea>
					</td>
				</tr>
				<tr>
					<td align="right" class="td1" nowrap>ftp用户名：</td>
					<td class="td2"  >
						 <input class="easyui-textbox" name="ftpUser" id="ftpUser" style="height:20px"></input>
					</td>
					<td align="right" class="td1" nowrap>ftp密码：</td>
					<td class="td2"  >
						<input class="easyui-textbox" name="ftpMM" id="ftpMM"  style="height:20px"></input>
					</td>
				</tr>
				<!--  
				<tr>
					<td align="right" class="td1" nowrap>升级你时间(2016-03-01 23:00:00)：</td>
					<td class="td2"  colspan='3'>
						<input class="easyui-textbox" name="updateTime" id="updateTime" style="height:20px;width:99%"></input>
					</td>
				</tr>	
				-->	   
				<tr>
					<td align="right" class="td1" nowrap></td>
					<td class="td2" nowrap colspan='3'>
						<div align="left">
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false" onclick="addGDAdvances(this);">提交</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false" onclick="javascript:window.close();">关闭</a>
						</div>
					</td>
				</tr>

				<tr>
					<td colspan="4"  nowrap>
						<div id="table"></div>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div id="tb" style="padding:3px;height:auto">
			
		</div>
		<div id="tbu" style="padding:3px;height:auto">
			
		</div>
			<div id="mtb" style="padding:3px;height:auto">
			
		</div>
		<div id="tb1" style="padding:3px;height:auto">
			
		</div>
	</body>
</html>