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
                    text: '销售报表-饼状图',
                    subtext: '${groupName}',
                    x: 'center'
                },
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient: 'vertical',
                    x: 'left',
                    data: ${groupTypes}
                },
                toolbox: {
                    show: true,
                    feature: {
                        mark: {show: true},
                        dataView: {show: true, readOnly: false},
                        magicType: {
                            show: true,
                            type: ['pie', 'funnel'],
                            option: {
                                funnel: {
                                    x: '25%',
                                    width: '50%',
                                    funnelAlign: 'left',
                                    max: ${maxAmount}
                                }
                            }
                        },
                        restore: {show: true},
                        saveAsImage: {show: true}
                    }
                },
                calculable: true,
                series: [
                    {
                        name: '销售额',
                        type: 'pie',
                        radius: '55%',
                        center: ['50%', '60%'],
                        data: ${totalAmount}
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
