<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<head>
    <meta charset="utf-8">
    <title>柱状图</title>
    <!-- 引入 echarts.js -->
   <script src="<%=path%>/springViews/statistics/echarts.js"></script>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        var option = {
        	    title : {
        	        text: '某设备用户统计量',
        	        subtext: '按天计算'
        	    },
        	    tooltip : {
        	        trigger: 'axis'
        	    },
        	    legend: {
        	        data:['昨天值','今天值']
        	    },
        	    toolbox: {
        	        show : true,
        	        feature : {
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    calculable : true,
        	    xAxis : [
        	        {
        	            type : 'category',
        	            data : ['1点','2点','3点','4点','5点','6点','7点','8点','9点','10点','11点','12点','13点','14点','15点','16点','17点','18点','19点','20点','21点','22点','23点','24点']
        	        }
        	    ],
        	    yAxis : [
        	        {
        	            type : 'value'
        	        }
        	    ],
        	    series : [
        	        {
        	            name:'今日量',
        	            type:'bar',
        	            data:[20, 49, 70, 23, 25, 76, 135, 162, 32, 20, 64, 33,20, 49, 70, 23, 25, 76, 135, 162, 32, 20, 64, 33],
        	            markPoint : {
        	                data : [
        	                    {type : 'max', name: '最大值'},
        	                    {type : 'min', name: '最小值'}
        	                ]
        	            },
        	            markLine : {
        	                data : [
        	                    {type : 'average', name: '平均值'}
        	                ]
        	            }
        	        },
        	        {
        	            name:'昨日量',
        	            type:'bar',
        	            data:[12, 20, 50, 22, 52, 76, 102, 102, 42, 50, 35, 30,20, 49, 70, 23, 25, 76, 100, 183, 32, 20, 64, 33],
        	            markPoint : {
        	                data : [
        	                    {name : '天最高', value : 162, xAxis: 7, yAxis: 183},
        	                    {name : '天最低', value : 20, xAxis: 11, yAxis: 12}
        	                ]
        	            },
        	            markLine : {
        	                data : [
        	                    {type : 'average', name : '平均值'}
        	                ]
        	            }
        	        }
        	    ]
        	};
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
</body>
</html>