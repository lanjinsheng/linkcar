<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/Server";
  }
%>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
function openDialogResize(URL,width,height)
{
  window.open(URL,"","height="+height+",width="+width+",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=200,left=200,resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no");
}
</script>

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