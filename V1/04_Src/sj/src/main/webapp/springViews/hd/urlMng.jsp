<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>

<%@page import = "com.ljs.util.Constant" %>
<%
String colHost = Constant.colHost;
%>
<html>
<head>
<title>转发链接管理</title>
</head>
<script type="text/javascript">
		$(function(){
			$("#table").datagrid({
			 loader : function(param, success, error) {  
                   var opts = $(this).datagrid("options");  
		            if(!opts.url) {  
		                return false;  
		            }  
                $.ajax({  
                    type : opts.method,  
                    url : opts.url,  
                    dataType : 'json',  
                    contentType : 'application/json;charset=utf-8', // 设置请求头信息  
                    data : JSON.stringify(param),  
                    success : function(result) {         
                            success(result);                  
                    }  ,  
	                error: function() {  
	                    error.apply(this, arguments);  
	                }  
                });  
            },  
				title:"链接数据",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/listPageUrl?time=' + new Date().getTime(),
				rownumbers:true,
				contentType : 'application/json;charset=utf-8', 
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'更新时间',field:'ctime',width:100,align:'center'},
					{title:'二级域名',field:'ejym',width:100,align:'center',formatter:function(value,rowData,rowIndex){
	                    return rowData.sld;
					}},
					{title:'来源相对地址',field:'fromUrl',width:100,align:'center'},
					{title:'跳转地址',field:'toUrl',width:300,align:'center'},
					{title:'编辑',field:'logPath6',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var id=rowData.id;
					    var fromUrl=rowData.fromUrl;
					    var toUrl=rowData.toUrl;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:editRouter('"+id+"','"+fromUrl+"','"+toUrl+"');\"> 点击编辑</span>";
					}},
					{title:'源地址二维码',field:'ydzewm',width:100,align:'center',formatter:function(value,rowData,rowIndex){
					    var url=rowData.sld+rowData.fromUrl;
	                    return "<span style=\"text-decoration:underline\" onclick=\"javascript:createQrCode('"+url+"');\"> 点击生成二维码</span>";
					}}
					 
				]],
				toolbar:[
							{
								id:'add',
								text:'添加地址映射',
								disabled:false,
								iconCls:'icon-add',
								handler:function(){	
									addRouter();
								}
							}
				],
				pageSize:10,
				pageList : [2,10,50,100],  
				pagination:true
			});
		});
		function rtCallBack(){
			$('#table').datagrid('reload');
		}
	   function downLoadSJ(uuid){
		 alert("开发中");
		}
	   function addRouter(){
			openDialogResize("<%=contextPath%>/springViews/hd/addUrl.jsp",700, 600);
	}
	   function editRouter(id,fromUrl,toUrl){
		   fromUrl=encodeURIComponent(fromUrl);
		   toUrl=encodeURIComponent(toUrl);
		   openDialogResize("<%=contextPath%>/springViews/hd/addUrl.jsp?id="+id+"&fromUrl="+fromUrl+"&toUrl="+toUrl,700, 600);
	   }
	   
	    function createQrCode(url) {
            showMyWindow("二维码",url,400,420);
        }
        
	    function showMyWindow(title,url, width, height) {  
	        $('#myWindow').window(  
                {  
                    title : title,  
                    width : width,  
                    height : height,  
                    modal : true,
                    minimizable : true,  
                    maximizable : true,  
                    shadow : false,  
                    cache : false,  
                    closed : false,
                    closable : true,
                    draggable :true,
                    collapsible : true,  
                    resizable : true,  
                    left: 300,
					top:50,
                    loadingMessage : '正在加载数据，请稍等片刻......'  
                });
	        var qr = new QRious({
			element:document.getElementById('qrious'),
			size:370, level:'M', value:url
			});
	    }
		
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
	
	<div id="myWindow" class="easyui-dialog" closed="true">
			<img id="qrious">	  
	</div>
	<div id="tb" style="padding:3px;height:auto">
		<div align="right">
		</div>
	</div>
</body>
</html>
