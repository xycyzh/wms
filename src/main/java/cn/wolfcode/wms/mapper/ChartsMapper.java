package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.query.QueryObject;

import java.util.List;
import java.util.Map;

public interface ChartsMapper {
    List<Map<String, String>> queryOrderCharts(QueryObject qo);

    List<Map<String, Object>> querySaleCharts(QueryObject qo);
}
