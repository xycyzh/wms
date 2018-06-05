package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.mapper.DepotMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IDepotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepotServiceImpl implements IDepotService {
    @Autowired
    private DepotMapper depotMapper;

    @Override
    public List<Depot> selectAll() {
        return depotMapper.selectAll();
    }

    @Override
    public Depot get(Long id) {
        return depotMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Depot depot) {
        if (depot.getId() != null) {
            depotMapper.updateByPrimaryKey(depot);
        } else {
            depotMapper.insert(depot);
        }
    }

    @Override
    public void delete(Long id) {
        depotMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = depotMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<Depot> list = depotMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }
}
