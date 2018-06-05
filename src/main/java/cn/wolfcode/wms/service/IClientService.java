package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;

import java.util.List;

public interface IClientService {
    List<Client> selectAll();

    Client get(Long id);

    void saveOrUpdate(Client client);

    void delete(Long id);

    PageResult query(QueryObject qo);
}
