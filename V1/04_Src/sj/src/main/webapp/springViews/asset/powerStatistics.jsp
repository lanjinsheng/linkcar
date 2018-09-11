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
		<div id="main" style="width: 1400px;height:700px;"></div>
		<script type="text/javascript">
			function myFun(day) {

				var myChart = echarts.init(document.getElementById('main'));
				// 显示标题，图例和空的坐标轴
				myChart.setOption({
					title: {
						text: '链车动力来源分析',
						subtext: '由红点云提供计算服务',
						x: 'center'
					},
					tooltip: {
						trigger: 'item',
						formatter: "{a} <br/>{b} : {c} ({d}%)"
					},
					legend: {
						orient: 'vertical',
						left: 'left',
						data: []
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
					series: [{
						name: '动力来源',
						type: 'pie',
						radius: '55%',
						center: ['50%', '60%'],
						data: [],
						itemStyle: {
							emphasis: {
								shadowBlur: 10,
								shadowOffsetX: 0,
								shadowColor: 'rgba(0, 0, 0, 0.5)'
							}
						}
					}]
				});

				myChart.showLoading(); //数据加载完之前先显示一段简单的loading动画

				var names = [];
				var values = [];
				$.ajax({
					type: "POST",
					url: "<%=assetUrl%>/ment/queryPowerStatistics",
					data: "day=" + day,
					dataType: "json",
					success: function(result) {
						if(result.rtState == '1') {
							for(var i = 0; i < result.rows.length; i++) {
								names.push(result.rows[i].name);
							}

							myChart.hideLoading(); //隐藏加载动画
							myChart.setOption({ //加载数据图表
								legend: {
									orient: 'vertical',
									left: 'left',
									data: names
								},
								series: [{
									name: '动力来源',
									type: 'pie',
									radius: '55%',
									center: ['50%', '60%'],
									data: result.rows,
									itemStyle: {
										emphasis: {
											shadowBlur: 10,
											shadowOffsetX: 0,
											shadowColor: 'rgba(0, 0, 0, 0.5)'
										}
									}
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