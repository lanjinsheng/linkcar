<!doctype html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
  String contextPath = request.getContextPath();
  if (contextPath.equals("")) {
    contextPath = "/obdserver";
  }
%>
<script type="text/javascript">
var contextPath = "<%=contextPath %>";
function openDialogResize(URL,width,height)
{
  window.open(URL,"","height="+height+",width="+width+",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=200,left=200,resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no");
}
</script>

  <link rel="stylesheet" type="text/css" href="<%=contextPath %>/public/jquery-easyui-1.4.3/themes/gray/easyui.css" />
  <link rel="stylesheet" type="text/css" href="<%=contextPath %>/public/jquery-easyui-1.4.3/themes/icon.css" />
  <link rel="stylesheet" type="text/css" href="<%=contextPath %>/public/jquery-easyui-1.4.3/themes/color.css" />
  <script type="text/javascript" src="<%=contextPath %>/public/jquery-easyui-1.4.3/jquery.min.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/jquery-easyui-1.4.3/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/jquery-easyui-1.4.3/locale/easyui-lang-zh_CN.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/jquery-easyui-1.4.3/datagrid-detailview.js"></script>
  <script type="text/javascript" src="<%=contextPath %>/public/js/json2.js"></script>