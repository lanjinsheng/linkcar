<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/public/header.jsp"%>
<%@page import = "com.ljs.util.CommentUtil" %>
<%
Map<String, Object> sessionMap = (Map<String, Object>) session.getAttribute("LOGIN_USER");
String person_id = String.valueOf(sessionMap.get("id"));
String person = String.valueOf(sessionMap.get("truename"));
String assetUrl = CommentUtil.assetUrl;
%>
<html>
	<style type="text/css">
		#button {
			background-color: transparent;
			color: green;
			border: 3px solid bule;
			padding: 3px 25px;
			text-align: center;
			text-decoration: none;
			display: inline-block;
			font-size: 10px;
			margin: 4px 2px;
			cursor: pointer;
			border-radius: 25px;
		}
	</style>

	<head>
		<title>动力数据查询</title>
	</head>

	<body style="height: 97%">
		<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		<button id="button" onclick="myFun(0)">今天</button>
		<button id="button" onclick="myFun(1)">昨天</button>
		<button id="button" onclick="myFun(7)">最近7天</button>
		<button id="button" onclick="myFun(30)">最近30天</button>
		<div id="main" style="width: 600px;height:400px;"></div>
		<script type="text/javascript">
			function myFun(day) {

				var myChart = echarts.init(document.getElementById('main'));
				// 显示标题，图例和空的坐标轴
				myChart.setOption({
					title: {
						text: '链车动力分布情况'
					},
					tooltip: {},
					legend: {
						data: ['动力数据']
					},
					toolbox: {
						show: true,
						feature: {
							dataView: {
								show: true,
								readOnly: false
							},
							saveAsImage: {
								show: true
							}
						}
					},
					calculable: true,
					xAxis: {
						data: []
					},
					yAxis: {},
					series: [{
						name: '动力数据',
						type: 'bar',
						data: []
					}]
				});

				myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画

				var types = []; //类别数组（实际用来盛放X轴坐标值）
				var nums = []; //销量数组（实际用来盛放Y坐标值）
				$.ajax({
					type: "POST",
					url: "<%=assetUrl%>/ment/queryPowerStatistics", //请求发送
					data: "day=" + day,
					dataType: "json", //返回数据形式为json
					success: function(result) {
						//请求成功时执行该函数内容，result即为服务器返回的json对象
						if(result.rtState == '1') {
							for(var i = 0; i < result.rows.length; i++) {
								types.push(result.rows[i].type); //挨个取出类别并填入类别数组
							}
							for(var i = 0; i < result.rows.length; i++) {
								nums.push(result.rows[i].num); //挨个取出销量并填入销量数组
							}
							myChart.hideLoading(); //隐藏加载动画
							myChart.setOption({ //加载数据图表
								xAxis: {
									data: types
								},
								series: [{
									// 根据名字对应到相应的系列
									name: '动力数据',
									data: nums
								}]
							});

						}

					},
					error: function(errorMsg) {
						//请求失败时执行该函数
						alert("图表请求数据失败!");
						myChart.hideLoading();
					}
				})

			}
		</script>
	</body>

</html>