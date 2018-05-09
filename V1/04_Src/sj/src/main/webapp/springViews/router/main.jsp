<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<!DOCTYPE HTML>
<html>
 <head>
  <title>后台管理系统</title>
   <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="<%=contextPath%>/public/bootstrap/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
	<link href="<%=contextPath%>/public/bootstrap/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
	<link href="<%=contextPath%>/public/bootstrap/assets/css/main.css" rel="stylesheet" type="text/css" />
 </head>
 <body>

  <div class="header">
    
      <div class="dl-title">
          <span class="lp-title-port">路由</span><span class="dl-title-text">后台管理系统</span>
      </div>

    <div class="dl-log">欢迎您，<span class="dl-log-user">DZT</span><a href="#" title="退出系统" class="dl-log-quit">[退出]</a>
    </div>
  </div>
   <div class="content">
    <div class="dl-main-nav">
      <div class="dl-inform">
      	<div class="dl-inform-title"></div>
      </div>
      
      <ul id="J_Nav"  class="nav-list ks-clear">
        <li class="nav-item dl-selected"><div class="nav-item-inner nav-home">首页</div></li>
      </ul>
    </div>
    <ul id="J_NavContent" class="dl-tab-conten">

    </ul>
   </div>
   
   <script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/jquery-1.8.1.min.js"></script>
   <script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/bui-min.js"></script>
   <script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/common/main-min.js"></script>
   <script type="text/javascript" src="<%=contextPath %>/public/bootstrap/assets/js/config-min.js"></script>

  <script>
    BUI.use('common/main',function(){
      var config = [{
          id:'menu', 
          homePage : 'boxStatus',
          menu:[{
              text:'设备数据',
              items:[
                {id:'hardwareTestId',text:'收发日志管理',href:'<%=contextPath%>/springViews/router/phoneDrive.jsp'},
                {id:'boxStatus',text:'设备信息查看',href:'<%=contextPath%>/springViews/router/routerBase.jsp'},
                {id:'boxTab',text:'升级信息管理',href:'<%=contextPath%>/springViews/router/dataTasks.jsp'},
                {id:'boxLoginLogs',text:'登入日志查看',href:'<%=contextPath%>/springViews/router/routerLoginLogs.jsp'}
              ]
            },
            {
              text:'指令配置管理',
                items:[
                  {id:'tplConfig',text:'指令配置模板管理',href:'<%=contextPath%>/springViews/commandCnf/tplConfig.jsp'},
                  {id:'clientConfig',text:'设备指令维护',href:'<%=contextPath%>/springViews/commandCnf/clientConfig.jsp'}
                ]
              },
              {
                  text:'内存数据',
                    items:[
                      {id:'online1',text:'在线数据管理',href:'<%=contextPath%>/springViews/router/onlineDatas.jsp'},
                      {id:'online2',text:'缓存Map查看',href:'<%=contextPath%>/springViews/router/onlineDatas2.jsp'}
                    ]
               }
            
            ]
          }];
        new PageUtil.MainPage({
        modulesConfig : config
      });
    });
  </script>
 </body>
</html>