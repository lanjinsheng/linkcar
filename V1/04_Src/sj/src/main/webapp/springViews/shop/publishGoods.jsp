<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%@page import = "com.ljs.util.CommentUtil" %>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
String shopUrl = CommentUtil.shopUrl;

%>
<html>
<head>
<title>商品发布</title>
</head>
<script type="text/javascript">
		$(function(){
	     	
		});

	    function myFunction(){
	    	var param = $("#ff").serialize();
	    	var person = "<%=person %>";
        	param=param+"&operatingUser="+person;
			var formData = new FormData($("#ff")[0]);
        	var imgs = "";
      		$.ajax({
				 type:'POST',
				url:'<%=shopUrl%>/ment/openUploadAuctionImg',
				type: 'POST', 
			    data: formData, 
			    dataType:'json',
			    async: false, 
			    cache: false, 
			    contentType: false, 
			    processData: false, 
			    success: function(data) {
			     	param=param+"&imgs="+data.rows;
			    }, 
			    error: function(data) {
			     	$.messager.alert("提示","上传失败");
			    } 
			});
        	
        	
        	$.ajax({
				type:'POST',
				url:'<%=shopUrl%>/ment/publishAuctionPage',
				data:param,
				dataType:'json',
				success:function(rtJson){
					if(rtJson.rtState == '1'){
						$.messager.alert("提示","操作成功");
		                setTimeout(function () {
		                    window.location.reload();
		                }, 1000);
					}
					else{
						$.messager.alert("提示","操作失败");
						//window.close();
					}
				}
			});
			
		}
</script>
<body style="height: 97%">
		<form id="ff" method="post" enctype="multipart/form-data">
		<div style="width:90%;padding-left:50px;margin-top:50px">
			<table  style="border-collapse: collapse;" width="100%" border="1px" bordercolor="#0eb83a">
				
				<tr>
					<td align="right"  nowrap class="td1">
						商品名：
					</td>
					<td class="td2" nowrap>
						<input type="text" id="title" name="title" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td align="right" nowrap class="td1">
						商品描述：
					</td>
					<td class="td2" nowrap>
						<input type="text" id="desc" name="desc" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td align="right"  class="td1" nowrap>
						商品图片：
					</td>
					<td  class="td2" nowrap>
						<input type="file" id="file" name="file"  data-options="required:true">
					</td>
				</tr>
				<tr>
					<td align="right"  class="td1" nowrap>
						起拍价：
					</td>
					<td  class="td2" nowrap>
						<input type="text" id="startValue" name="startValue" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td align="right"  class="td1" nowrap>
						加价幅度：
					</td>
					<td  class="td2" nowrap>
						<input type="text" id="difference" name="difference" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td align="right"  class="td1" nowrap>
						类型：
					</td>
					<td  class="td2" nowrap>
						<select id="type" name="type" data-options="required:true">
								<option value="1">
									电子码券
								</option >
								<option value="2">
									实物奖励
								</option>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right"  class="td1" nowrap>
						开始时间：
					</td>
					<td class="td2" colspan="3">
						<input id="startTime" type="text" name="startTime" placeholder="yyyy-MM-dd HH:mm:ss" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				<tr>
					<td align="right"  class="td1" nowrap>
						结束时间：
					</td>
					<td class="td2" colspan="3">
						<input id="endTime" type="text" name="endTime" placeholder="yyyy-MM-dd HH:mm:ss" class="easyui-validatebox" data-options="required:true">
					</td>
				</tr>
				
				<tr>
					<td align="right" nowrap class="td1">
					</td>
					<td  nowrap colspan="5">
						<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:false" onclick="myFunction();">保存</a>
					</td>
				</tr>
				
 			</table>
		</div>
		</form>
		
</body>
</html>
