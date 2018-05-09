<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta charset="utf-8">
    <title>折线图</title>
 <style>
 body{ text-align:center} 
.div1{ margin:0 auto; width:600px; height:30px} 
 </style>
   <link rel="stylesheet" type="text/css" href="<%=path%>/springViews/statistics/css/lanrenzhijia.css" />
      <!-- 引入 echarts.js -->
    <script src="<%=path%>/springViews/statistics/echarts.js"></script>
    <script type="text/javascript" src="<%=path%>/springViews/statistics/js/lanrenzhijia.js"></script>
</head>
<body>

<center>
<form action="#">
  <!-- add class="tcal" to your input field -->
  <div align="left">
  <font size="1" color="red">
注:1、时间区间在同一天的，将按小时分布来统计<br/>
 &nbsp;&nbsp; &nbsp;2、时间区间小于等于30天的，将按天分布来统计<br/>
 &nbsp;&nbsp;&nbsp; 3、时间区间大于30天的，将按月分布来统计<br/>
  </font>
  </div>
  <%
  String pc=request.getAttribute("placeCode")==null?"":request.getAttribute("placeCode").toString();
  String []a=pc.split(",");
  String []b=request.getAttribute("wanIp").toString().split(",");
  String []c=request.getAttribute("status").toString().split(",");
  %>
  <%for(int t=0;t<a.length;t++){ %>
   <div class="div1">
             设备:<%=a[t]%>&nbsp;&nbsp;
 	WAN IP:<%=b[t]%>&nbsp;&nbsp;
 	状态:<%=c[t]%>
   </div>
  <%}%>
  <div>
  <input type="checkbox" id="uADT" value="1" >智能过滤
   &nbsp;&nbsp;&nbsp;&nbsp;时间区间： <input type="text" name="date1" id="date1" class="tcal" value="" />
 ~<input type="text" name="date2" id="date2" class="tcal" value="" />
   <input type="button" name="sub"  onclick="goSub()" value="提交"/>
   <input type="button" name="change" onclick="goADT()" value="平均停留时间展示"/>
  </div>

  <input type="hidden" name="isADT" id="isADT" value="<%=request.getAttribute("isADT")%>"/>
  
</form>

    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1024px;height:600px;"></div>
    <script type="text/javascript">
    document.getElementById("date1").value="<%=request.getAttribute("fromDay")%>";
    document.getElementById("date2").value="<%=request.getAttribute("toDay")%>";
    if(<%=request.getAttribute("isADT")%>==1){
    	 document.getElementById("uADT").checked=true;
    }
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
    title: {
        text: '<%=request.getAttribute("showText")%>',
        subtext: '测试使用'
    },
    tooltip: {
        trigger: 'axis'
    },
    toolbox: {
        show: true,
        feature: {
            saveAsImage: {}
        }
    },
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: <%=request.getAttribute("a")%>,
        axisLabel:{
            rotate:45, //刻度旋转45度角
            interval:0,
            textStyle:{
               color:"red",
               fontSize:12
          }
        }
    },
    yAxis: {
        type: 'value',
        axisLabel: {
            formatter: '{value}'
        }
    },
    visualMap: {
        show: false,
        dimension: 0,
        pieces: [{
            lte: 6,
            color: 'green'
        }, {
            gt: 6,
            lte: 8,
            color: 'green'
        }, {
            gt: 8,
            lte: 14,
            color: 'green'
        }, {
            gt: 14,
            lte: 17,
            color: 'green'
        }, {
            gt: 17,
            color: 'green'
        }]
    },
    series: [
        {
            name:'用户量',
            type:'line',
            smooth: true,
            data:<%=request.getAttribute("b")%>,
            markArea: {
                data: []
            }
        }
    ]
};
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
    <script type="text/javascript">
    function goADT(){
    	var date1=document.getElementById("date1").value;
    	var date2=document.getElementById("date2").value;
    	var isADT=0;
    	if(document.getElementById("uADT").checked){
    		  isADT=1;
    	}
    	location.href="<%=path%>/r/statistics/dayViewsADT1?fromDay="+date1+"&toDay="+date2+"&placeCode=<%=request.getAttribute("placeCode")%>&isADT="+isADT;

    }
    function goSub(){
    	var date1=document.getElementById("date1").value;
    	var date2=document.getElementById("date2").value;
    	var isADT=0;
    	if(document.getElementById("uADT").checked){
    		  isADT=1;
    	}
    	location.href="<%=path%>/r/statistics/dayViews1?fromDay="+date1+"&toDay="+date2+"&placeCode=<%=request.getAttribute("placeCode")%>&isADT="+isADT;
    }
    
    
    </script>
</body>
</html>