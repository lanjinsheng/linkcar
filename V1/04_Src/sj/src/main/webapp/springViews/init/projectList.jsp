<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<html>
<head>
<title>初始化项目参数配置</title>
</head>
<script type="text/javascript">
		$(function(){
			$("#box_id").keypress(function(e) { 
	    	// 回车键事件 
	     	if(e.which == 13) { 
	     		 doSearch();
	       	} 
	   	}); 
			$("#table").datagrid({
				title:"项目参数列表",
				iconCls:'icon-edit',
				loadMsg:'正在加载数据，请稍后......',
				url:'<%=contextPath%>/r/manage/view/listPageProject?time=' + new Date().getTime(),
				rownumbers:true,
				singleSelect:true,
				nowrap:false,
				fitColumns:true,
				idField:'id',
				frozenColumns:[[
				    {title:'主键',field:'id',hidden:true}
				]],
				columns:[[
					{title:'项目名称',field:'pj_desc',width:200,align:'center'},
					{title:'SN_HEADER',field:'ap_sn_header',width:100,align:'center'},
					{title:'SN_MID',field:'ap_sn_mid',width:200,align:'center'},
					{title:'SN_NO_FROM',field:'ap_sn_no_start',width:100,align:'center'},
					{title:'SN_NO_END',field:'ap_sn_no_end',width:100,align:'center'},
					{title:'FMTF',field:'adt_rate',width:50,align:'center'},
					{title:'FMSF_HOUR',field:'fmsf_hour',width:100,align:'center'},
					{title:'FMSF_DAY',field:'fmsf_day',width:100,align:'center'},
					{title:'FMSF_LIVE',field:'fmsf_live',width:100,align:'center'},
					{title:'LFT',field:'lft',width:50,align:'center'},
					{title:'LBT',field:'lbt',width:50,align:'center'},
					{title:'ADT_Divide_FMTF_LIVE',field:'adt_divide_fmtf_live',width:100,align:'center'},
					{title:'编辑',field:'edit',width:50,align:'center',formatter:function(value,row,index){
						return "<a href='javascript:goEditProject(\""+row.id+"\")'><font color='#dd0000'>编辑</font></a> &nbsp;&nbsp;";
						
					}},
				]],
				toolbar:[
							{
								id:'add',
								text:'增加项目',
								disabled:false,
								iconCls:'icon-add',
								handler:function(){
									
									openW();
								}
							}
					   ],
				pageSize:50,
				pageList : [10,50,100],  
				pagination:true
			});
		});
        function openW(id){
        	window.open("<%=contextPath%>/r/manage/view/getProjectById?callback=reload");
        }
       function goEditProject(id){
    	   window.open("<%=contextPath%>/r/manage/view/getProjectById?id="+id+"&callback=reload");
       }
		function reload(){
			$('#table').datagrid('reload');
		}
		
		
</script>
<body style="height: 97%">
	<div id="table" data-options="fit:true"></div>
	<div id="tb" style="padding:3px;height:auto">
	</div>
</body>
</html>
