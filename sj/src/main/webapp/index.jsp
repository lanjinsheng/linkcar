<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>RouterServer</title>
</head>
<body>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
if(path.equals("") && request.getServerName().equalsIgnoreCase("mngtomcat")){
	response.sendRedirect("http://mng.idata365.com/Server");
}else if(request.getServerName().equalsIgnoreCase("hdtomcat")){
	response.sendRedirect("http://www.baidu.com");
}
%>
<%=basePath%>
</body>
</html>