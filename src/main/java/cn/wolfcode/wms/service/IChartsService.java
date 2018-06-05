package cn.wolfcode.wms.service;

import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.query.SaleChartQueryObject;

import java.util.List;
import java.util.Map;

public interface IChartsService {
    List<Map<String, String>> queryOrderCharts(QueryObject qo);

    List<Map<String, Object>> querySaleCharts(QueryObject qo);
}
