<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/ap";
  }
%>
  <link rel="stylesheet" type="text/css" href="<%=contextPath %>/public/easyui1.4/themes/gray/easyui.css" />
  <link rel="stylesheet" type="text/css" href="<%=contextPath %>/public/easyui1.4/themes/icon.css" />
  <link rel="stylesheet" type="text/css" href="<%=contextPath %>/public/easyui1.4/themes/color.css" />
  <script type="text/javascript" src="<%=contextPath %>/public/easyui1.4/jquery.min.js"></script>
    <script type="text/javascript" src="<%=contextPath %>/public/easyui1.4/jquery.form.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/easyui1.4/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/easyui1.4/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/easyui1.4/datagrid-detailview.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/easyui1.4/jquery.easyui.patch.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/js/json2.js"></script>
   <script type="text/javascript" src="<%=contextPath %>/public/js/core.js"></script>
<% String callback = request.getParameter("callback") == null ? "" : request.getParameter("callback");%>
<html>
	<head>
		<title>项目值初始化</title>
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
	window.onLoad = function(){
 
	}


		function addGDAdvances(obj){
			var id ="<%=request.getParameter("id")%>";
			if($('#ff').form('validate')){
				var lft =$('#lft').textbox('getValue');
				var lbt = $('#lbt').textbox('getValue');
				var adt_divide_fmtf_live =$('#adt_divide_fmtf_live').textbox('getValue');
				var fmsf_live = $('#fmsf_live').textbox('getValue');
				var fmsf_day = $('#fmsf_day').textbox('getValue');
				var fmsf_hour = $('#fmsf_hour').textbox('getValue');
				var adt_rate = $('#adt_rate').textbox('getValue');
				var ap_sn_no_end = $('#ap_sn_no_end').textbox('getValue');
				var ap_sn_no_start = $('#ap_sn_no_start').textbox('getValue');
				var ap_sn_header = $('#ap_sn_header').textbox('getValue');
				var ap_sn_mid = $('#ap_sn_mid').textbox('getValue');
				var pj_desc = $('#pj_desc').textbox('getValue');
				var beginTime=$("#startDatetime").datebox("getValue");
				var endTime=$("#endDatetime").datebox("getValue");
				var param="id="+id+"&lft="+lft+"&lbt="+lbt+"&adt_divide_fmtf_live="+adt_divide_fmtf_live+"&fmsf_live="+fmsf_live+"&fmsf_day="+fmsf_day;
				param+="&fmsf_hour="+fmsf_hour+"&adt_rate="+adt_rate+"&ap_sn_no_end="+ap_sn_no_end+"&ap_sn_no_start="+ap_sn_no_start+"&ap_sn_header="+ap_sn_header;
				param+="&ap_sn_mid="+ap_sn_mid+"&pj_desc="+pj_desc+"&validTime_from="+beginTime+"&validTime_to="+endTime;
				$.ajax({
					type:'POST',
					url:"<%=contextPath%>/r/manage/view/updateProject",
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
	<body >
		<div style="width: 97%;padding-left:10px">
		<form id="ff">
			<table  style="border-collapse: collapse;" width="100%" border="1px" bordercolor="#0eb83a">
				<tr>
					<td align="right" class="td1" nowrap>项目名称：</td>
					<td class="td2"  colspan='3'>
						 <input class="easyui-textbox" name="pj_desc" id="pj_desc" style="height:20px;width:99%"></input>
					</td>
				</tr>
				<tr>
					<td align="right" class="td1" nowrap>SN头编码(4位)：</td>
					<td class="td2"  >
						 <input class="easyui-textbox" name="ap_sn_header" id="ap_sn_header" style="height:20px;width:50%"></input>
					</td>
					<td align="right" class="td1" nowrap>SN中间物料编码：</td>
					<td class="td2"  >
						<input class="easyui-textbox" name="ap_sn_mid" id="ap_sn_mid"  style="height:20px;width:50%"></input>
					</td>
				</tr>
			    <tr>
					<td align="right" class="td1" nowrap>SN尾部AP编号起始值(>=)：</td>
					<td class="td2"  >
						 <input class="easyui-textbox" name="ap_sn_no_start" id="ap_sn_no_start" style="height:20px;width:50%"></input>
						 <font size="2" color="#FF0000">(如:000001,数值型,不含字母与符号)</font>
					</td>
					<td align="right" class="td1" nowrap>SN尾部AP编号终止值(<=)：</td>
					<td class="td2"  >
						<input class="easyui-textbox" name="ap_sn_no_end" id="ap_sn_no_end"  style="height:20px;width:50%"></input>
						<font size="2" color="#FF0000">(如:000018,数值型,不含字母与符号)</font>
					</td>
				</tr>
				 <tr>
					<td align="right" class="td1" nowrap>FMTF：</td>
					<td class="td2"  >
						 <input class="easyui-textbox" name="adt_rate" id="adt_rate" style="height:20px;width:50%"></input>
					</td>
					<td align="right" class="td1" nowrap>FMSF_HOUR：</td>
					<td class="td2"  >
						<input class="easyui-textbox" name="fmsf_hour" id="fmsf_hour"  style="height:20px;width:50%"></input>
					</td>
				</tr> 
				 <tr>
					<td align="right" class="td1" nowrap>FMSF_DAY：</td>
					<td class="td2"  >
						 <input  class="easyui-textbox" name="fmsf_day" id="fmsf_day" style="height:20px;width:50%"></input>
					</td>
					<td align="right" class="td1" nowrap>FMSF_LIVE：</td>
					<td class="td2"  >
						<input class="easyui-textbox" name="fmsf_live" id="fmsf_live"  style="height:20px;width:50%"></input>
					</td>
				</tr> 
				<tr>
					<td align="right" class="td1" nowrap>LFT(秒)：</td>
					<td class="td2"  >
						 <input class="easyui-textbox" name="lft" id="lft" style="height:20px;width:50%"></input>
					</td>
					<td align="right" class="td1" nowrap>LBT(秒)：</td>
					<td class="td2"  >
						<input class="easyui-textbox" name="lbt" id="lbt"  style="height:20px;width:50%"></input>
					</td>
				</tr> 		
 
				 <tr>
					<td align="right" class="td1" nowrap>ADT_Divide_FMTF_LIVE：</td>
					<td class="td2" colspan='3' >
						 <input class="easyui-textbox" name="adt_divide_fmtf_live" id="adt_divide_fmtf_live" style="height:20px;width:50%"></input>
					</td>
			 
				</tr> 
				
				
							<tr>
					<td align="right" class="td1" nowrap>起始时间：</td>
					<td class="td2"  >
						<input id="startDatetime" name="startDatetime" class="easyui-datebox" data-options="required:true,editable:false" style="width:150px;"/>
					</td>
					<td align="right" class="td1" nowrap>终止时间：</td>
					<td class="td2"  >
						<input id="endDatetime" name="endDatetime" class="easyui-datebox" data-options="required:true,editable:false" style="width:150px;"/>
					</td>
				</tr> 					 
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
		
	</body>
	<script type="text/javascript">
		$("#lft").val("<%=request.getAttribute("lft")%>");
		$("#lbt").val("<%=request.getAttribute("lbt")%>");
		$("#adt_divide_fmtf_live").val("<%=request.getAttribute("adt_divide_fmtf_live")%>");
		$("#fmsf_live").val("<%=request.getAttribute("fmsf_live")%>");
		$("#fmsf_day").val("<%=request.getAttribute("fmsf_day")%>");
		$("#fmsf_hour").val("<%=request.getAttribute("fmsf_hour")%>");
		$("#adt_rate").val("<%=request.getAttribute("adt_rate")%>");
		$("#ap_sn_no_end").val("<%=request.getAttribute("ap_sn_no_end")%>");
		$("#ap_sn_no_start").val("<%=request.getAttribute("ap_sn_no_start")%>");
		$("#ap_sn_header").val("<%=request.getAttribute("ap_sn_header")%>");
		$("#ap_sn_mid").val("<%=request.getAttribute("ap_sn_mid")%>");
		$("#pj_desc").val("<%=request.getAttribute("pj_desc")%>");
		
		$("#startDatetime").val("<%=request.getAttribute("startDatetime")%>");
		$("#endDatetime").val("<%=request.getAttribute("endDatetime")%>");
		
		
	</script>
</html>