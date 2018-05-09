<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>

<html>
	<head>
	</head>
	<script type="text/javascript">
		$(function(){
		    $('#tt').treegrid({  
		    	fitColumns:true,
				url: '<%=contextPath %>/r/manage/core/getMenus',
				animate:true,
				collapsible:true,
				idField: 'seq_id',
				treeField: 'text',
		        columns:[[  
		            {field:'seq_id',hidden:true},  
		            {field:'_parentId',hidden:true},  
		            {field:'parent_name',hidden:true},  
		            {field:'text',title:'text(123)',width:200},  
		            {field:'sort',title:'排序号(123)',width:100},  
		            {field:'icon',title:'图标(1)',width:100,formatter: function(value,row,index){
		            	if(row.type != 1){
		            		return "";
		            	}
		            	return value;
		            }},  
		            {field:'type',hidden:true},  
		            {field:'id',title:'id(13)',width:100,formatter: function(value,row,index){
		            	if(row.type != 2){
		            		return value;
		            	}
		            	return "";
		            }},  
		            {field:'home_page',title:'homePage(1)',width:100,formatter: function(value,row,index){
		            	if(row.type != 1){
		            		return "";
		            	}
		            	return value;
		            }},  
		            {field:'collapsed',title:'collapsed(12)',width:100,formatter: function(value,row,index){
		            	if(row.type != 3){
		            		return value;
		            	}
		            	return "";
		            }},  
		            {field:'closeable',title:'closeable(3)',width:100,formatter: function(value,row,index){
		            	if(row.type == 3){
		            		return value;
		            	}
		            	return "";
		            }},  
		            {field:'href',title:'href(3)',width:400,formatter: function(value,row,index){
		            	if(row.type == 3){
		            		return value;
		            	}
		            	return "";
		            }},
		            {field:'operation',title:'操作',width:100,formatter: function(value,row,index){
		            	var type = 1;
		            	var seq_id = row.seq_id;
		            	var text = row.text;
		            	if(row.type == 1){
		            		type = 2;
		            	}
		            	else if(row.type == 2){
		            		type = 3;
		            	}else{
		            		return "<input type='button' title='编辑' style='border:0px #ff0000 solid;cursor:pointer' class='icon-edit' onClick='editNode(\""+seq_id+"\")'/>"
		            		      +"&nbsp;<input type='button'  title='删除' style='border:0px #ff0000 solid;cursor:pointer' class='icon-remove' onClick='deleteNode(\""+seq_id+"\")'/>";
		            	}
		            	return "<input type='button'  title='添加子节点' style='border:0px #ff0000 solid;cursor:pointer' class='icon-add' onClick='addNode(\""+seq_id+"\",\""+text+"\",\""+type+"\")'/>"
		            		  +"&nbsp;<input type='button' title='编辑' style='border:0px #ff0000 solid;cursor:pointer' class='icon-edit' onClick='editNode(\""+seq_id+"\")'/>"
		            		  +"&nbsp;<input type='button' title='删除' style='border:0px #ff0000 solid;cursor:pointer' class='icon-remove' onClick='deleteNode(\""+seq_id+"\")'/>";
		            }}
		        ]],
		        toolbar:[{
						id:'add',
						text:'新增一级菜单',
						iconCls:"icon-add",
						handler:function(){
							openWindow();
						}
					}]
		    });  
		    initForm();
		});
		function initForm(){
			$('#ff').form({  
		    	url: '<%=contextPath %>/r/manage/core/addMenu',
		    	contentType:"application/x-www-form-urlencoded; charset=utf-8",
		        success:function(data){  
		        	var rtJson = eval('(' + data + ')');
		        	if(rtJson.rtState == '0'){
		        		$('#ff').form('clear');
		        		$('#addWindow').window('close');
		        		$('#tt').treegrid('reload');
		        	}else{
		        		$.messager.alert('提示','添加出错！');
		        	}
		        }  
		    });  
		}
		function deleteNode(id){
			$.messager.confirm('确认删除','删除操作将删除该节点极其所有子节点，是否继续？',function(msg){
					if(msg){
						$.ajax({
							type:'POST',
							url:'<%=contextPath %>/r/manage/core/deleteMenu?id='+id,
							success:function(rtJson){
								if(rtJson.rtState=='0'){
									warningInfo(rtJson.rtMsrg);
									$('#tt').treegrid('reload');
								}
							}
						});
					}
				}
			);
		}
		function addNode(parent_id,parent_name,type){
			openWindow();
			$('#parent_id').val(parent_id);
			$('#parent_name').val(parent_name);
			$('#type').val(type);
		}
		function editNode(id){
			openWindow();
			$('#ff').form('load','<%=contextPath %>/r/manage/core/getMenu?id='+id);
			
			$('#ff').form({  
		    	url: '<%=contextPath %>/r/manage/core/updateMenu?seq_id='+id,
		    	contentType:"application/x-www-form-urlencoded; charset=utf-8",
		        success:function(data){  
		        	var rtJson = eval('(' + data + ')');
		        	if(rtJson.rtState == '0'){
		        		$('#ff').form('clear');
		        		$('#addWindow').window('close');
		        		$('#tt').treegrid('reload');
		        	}else{
		        		$.messager.alert('提示','添加出错！');
		        	}
		        	initForm();
		        }  
		    });  
		}
		function openWindow(){
			$('#addWindow').window('open');
			$('#parent_id').val("0");
			$('#parent_name').val("根节点");
			$('#collapsed').combobox('setValue',"0");
			$('#closeable').combobox('setValue',"1");
			$('#type').val("1");
		}
		function add(){
			$('#ff').submit();
		}
	</script>
	<body>
		  <table id="tt" title="菜单管理" class="easyui-treegrid" style="height: 99%"></table>
		   <div id="addWindow" class="easyui-window" title="添加菜单" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:600px;height:400px;padding:10px;">
			    	<form id="ff" method="post">
			    	<input  type="hidden" name="parent_id" id="parent_id" value="0"></input>
			    	<input  type="hidden" name="parent_name" id="parent_name" value="根节点"></input>
			    	<input  type="hidden" name="type" id="type" value="1"></input>
			    	<table>
			    		<tr>
			    			<td>text(123):</td>
			    			<td><input  type="text" name="text" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>排序号(123):</td>
			    			<td><input class="easyui-numberbox" type="text" name="sort" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>图标(1):</td>
			    			<td><input  type="text" name="icon" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>id(13):</td>
			    			<td><input  type="text" name="id" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>homePage(1):</td>
			    			<td><input  type="text" name="home_page" ></input></td>
			    		</tr>
			    		<tr>
			    			<td>collapsed(12):</td>
			    			<td>
			    				<input class="easyui-combobox" id="collapsed" name="collapsed" data-options="valueField:'id',textField:'text',data:[{id:'0',text:'false'},{id:'1',text:'true'}]" />  
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>closeable(3):</td>
			    			<td>
			    				<input class="easyui-combobox" id="closeable" name="closeable" data-options="valueField:'id',textField:'text',data:[{id:'0',text:'false'},{id:'1',text:'true'}]" />
			    			</td>
			    		</tr>
			    		<tr>
			    			<td>href(3):</td>
			    			<td><input  type="text" name="href"  size="50"></input></td>
			    		</tr>
			    		<tr>
			    			<td></td>
			    			<td>
			    				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false" onclick="add();">提交</a>
			    				<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:false" onclick="javascript:$('#addWindow').window('close');">关闭</a>
			    			</td>
			    		</tr>
			    	</table>
			    	</form>
			</div>
	</body>
</html>
