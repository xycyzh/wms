package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Client;
import cn.wolfcode.wms.mapper.ClientMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private ClientMapper clientMapper;

    @Override
    public List<Client> selectAll() {
        return clientMapper.selectAll();
    }

    @Override
    public Client get(Long id) {
        return clientMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(Client client) {
        if (client.getId() != null) {
            clientMapper.updateByPrimaryKey(client);
        } else {
            clientMapper.insert(client);
        }
    }

    @Override
    public void delete(Long id) {
        clientMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = clientMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<Client> list = clientMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }
}
