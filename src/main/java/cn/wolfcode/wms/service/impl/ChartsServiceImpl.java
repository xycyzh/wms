package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.mapper.ChartsMapper;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IChartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChartsServiceImpl implements IChartsService {
    @Autowired
    private ChartsMapper chartsMapper;

    @Override
    public List<Map<String, String>> queryOrderCharts(QueryObject qo) {
        return chartsMapper.queryOrderCharts(qo);
    }

    @Override
    public List<Map<String, Object>> querySaleCharts(QueryObject qo) {
        return chartsMapper.querySaleCharts(qo);
    }
}
