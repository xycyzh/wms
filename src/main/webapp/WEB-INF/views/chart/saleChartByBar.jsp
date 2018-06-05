<%--
  Created by IntelliJ IDEA.
  User: xycceo
  Date: 2018/5/31
  Time: 下午12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/js/plugins/echarts/echarts-all.js"></script>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts图表
        window.onload = function () {
            var myChart = echarts.init(document.getElementById('main'));
            var option = {
                title: {
                    text: '销售报表-柱状图',
                    subtext: '${groupName}',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['销售总金额'],
                    x: 'left'
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {show: true, type: ['line', 'bar']},
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                xAxis: [
                    {
                        type: 'category',
                        data:${groupTypes}
                    }
                ],
                yAxis: [
                    {
                        type: 'value'
                    }
                ],
                series: [
                    {
                        name: '销售总金额',
                        type: 'bar',
                        data: ${totalAmount},
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    }
                ]
            };
            // 为echarts对象加载数据
            myChart.setOption(option);
        };
    </script>
</head>
<body>
<div id="main" style="height:580px;width: 780px"></div>
</body>
</html>
