<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>

<html>
	<head>
	<style>
			.td1{
				padding-top:10px;
				padding-bottom:5px;
				font-size: 12px;
				width:20%;
			}
			.td2{
				padding:5px;
			}
		</style>
	<script type="text/javascript">
		$(function(){
    		// 回车键事件 
			$("#name").keypress(function(e) { 
     	  		if(e.which == 13) { 
   					addOneNode();
       			} 
   			}); 
   		
			$('#tree').tree({
				url:'<%=contextPath %>/r/manage/group/listDepartment',
				method:'POST',
				animate:true,
				lines:true,
				onContextMenu: function(e,node){
					e.preventDefault();
					$(this).tree('select',node.target);
					$('#mm').menu('show',{
						left: e.pageX,
						top: e.pageY
					});
				}
			});
			$("#parent").combotree({
				url:'<%=contextPath %>/r/manage/group/listDepartment',
				method:'POST',
				animate:true,
				lines:true
			});
			$("#sort").numberbox({
				min:0
			});
		});
	</script>
	
	<script type="text/javascript">
	function departAdd(){
			$('#id').val('');
			$("#sort").numberbox('setValue',"");
			$("#name").val("");
			var t = $('#tree');
			var node = t.tree('getSelected');
			$('#parent').combotree('reset'); 	
			$('#parent').combotree('setValue',node.id); 
	}
			
		function departEdit(){
			var t = $('#tree');
			var node = t.tree('getSelected');
			$("#id").val(node.id);
			$("#name").val(node.text);
			$("#sort").numberbox('setValue',node.attributes.sort);
			$('#parent').combotree('reset'); 	
			$('#parent').combotree('setValue',node.attributes.parent_id); 	
		}
		
		function addOneNode(obj){
		 	var id=$("#id").val();
			var parent_id = $('#parent').combotree('getValue');
			if(!checkNullOREmpty(parent_id,'上级部门')){return;}
			var parent_name = $('#parent').combotree('getText');
		 	var sort=$("#sort").val();
		 	if(!checkNullOREmpty(sort,'部门排序号')){return;}
		 	var name=$("#name").val();
		  	if(!checkNullOREmpty(name,'部门名称')){return;}
	       //保存修改
	       $(obj).hide();
	        if(id == "" || id==null){
	        	$.ajax({
		    	type:'POST',
		    	url:'<%=contextPath %>/r/manage/group/addDepartNode',
		    	data:"parent_id="+parent_id+"&name="+name+"&parent_name="+parent_name+"&sort="+sort,
		    	dataType:'json',
		    	success:function(rtJson){
		    		if(rtJson.rtState == '0'){
		    			$('#tree').tree('reload');
		    			$("#parent").combotree('tree').tree('reload');
		    			$("form")[0].reset();
		    			$("#id").val("");
					}
					else{
						warningInfo(rtJson.rtMsrg);
					}
		    		$(obj).show();
		    	}
		   	 });
	        }
	        else{
	        	  $.ajax({
		    			type:'POST',
		    			url:'<%=contextPath %>/r/manage/group/updateDepartNode',
		    			data:"parent_id="+parent_id+"&name="+name+"&parent_name="+parent_name+"&sort="+sort+"&id="+id,
		    			dataType:'json',
		    			success:function(rtJson){
		    				if(rtJson.rtState == '0'){
		    					$('#tree').tree('reload');
		    					$("#parent").combotree('tree').tree('reload');
		    					$("form")[0].reset();
		    					$("#id").val("");
							}
							else{
								warningInfo(rtJson.rtMsrg);
							}
		    				$(obj).show();
		    			}
		    		});
	        }
		}

		function removeit(){
			var node = $('#tree').tree('getSelected');
			if(node.attributes.parent_id=='0'){
				warningInfo("公司无法删除");
				return;
			}
			
			$.messager.confirm('确认删除','删除操作将删除该节点极其所有子节点，是否继续？',function(msg){
						if(msg){
							var node = $('#tree').tree('getSelected');
							var childrenArray = $('#tree').tree('getChildren',node.target);
							var ids = node.id;
							if(childrenArray.length > 0){
								for(var i=0;i<childrenArray.length;i++){
									ids +=	","+childrenArray[i].id;							
								}
							}
							$.ajax({
								type:'POST',
								url:"<%=contextPath %>/r/manage/group/deleteDepartNode",
								data:"ids="+ids,
								dataType:"json",
								success:function(rtJson){
									if(rtJson.rtState == '0'){
										$('#tree').tree('reload');
		    							$("#parent").combotree('reset');
		    							$("#parent").combotree('tree').tree('reload');
		    							$("form")[0].reset();
		    							$("#id").val("");
									}
									else{
										warningInfo(rtJson.rtMsrg);
									}
								}
							});
						}
			});
		}
		
		function warningInfo(msg){
			$.messager.alert('提示：',msg);
		}
		function checkNullOREmpty(val,msg){
			if(val == '' || val == null){
				$.messager.alert("提示",msg+'不能为空');
				return false;
			}
			return true;
		}
	</script>
	</head>
	<body>
		<div class="easyui-layout" id="typeLayout" data-options="fit:true">
			 <div region="west"  style="width:370px;" title="部门列表" >
			 	<ul class="easyui-tree" id="tree" style="padding-top: 10px"></ul>
			 </div>
			 <div region="center" title="部门属性">
				 <form>
			 		<table style="border-collapse: collapse;margin-top: 50px;margin-left: 50px;" width="70%" border="1px" bordercolor="#0eb83a">
			 			<tr>
							<td align="right" nowrap class="td1">
								上级部门名称:
							</td>
							<td class="td2"  class="TableData">
								<input id="id" type="hidden"/>
								<input id="parent" class="easyui-combobox"  /><font color='red'>*</font>
							</td>
						</tr>
						<tr>
			 				<td align="right" nowrap class="td1">
								部门排序号:
							</td>
							<td class="td2"  class="TableData">
								<input id="sort" name="sort" ></input><font color='red'>*</font>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap class="td1">
								部门名称:
							</td>
							<td class="td2"  class="TableData">
								<input id="name" name="name"  ></input><font color='red'>*</font>
							</td>
						</tr>
						<tr>
							<td align="right" nowrap class="td1">
							</td>
							<td  nowrap >
								<input type="button" value="提交" onclick="addOneNode(this);" >
								<input type="reset" value="重置" >
							</td>
						</tr>
			 		</table>
			 		</form>
			 </div>
		</div>
		
		<div id="mm" class="easyui-menu" style="width:120px;">
			<div onclick="departAdd()" data-options="iconCls:'icon-edit'">添加子部门</div>
			<div onclick="departEdit()" data-options="iconCls:'icon-edit'">编辑</div>
			<div onclick="removeit()" data-options="iconCls:'icon-remove'">删除</div>
		</div>
	</body>
</html>
