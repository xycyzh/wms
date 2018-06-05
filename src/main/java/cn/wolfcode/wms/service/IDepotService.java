package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Depot;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IDepotService {
    List<Depot> selectAll();

    Depot get(Long id);

    void saveOrUpdate(Depot depot);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
