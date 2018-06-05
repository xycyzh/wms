package cn.wolfcode.wms.service;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.vo.SystemMenuVO;

import java.util.List;

public interface ISystemMenuService {
    List<SystemMenu> selectAll();

    SystemMenu get(Long id);

    void saveOrUpdate(SystemMenu systemMenu);

    void delete(Long id);

    PageResult query(QueryObject qo);

    List<SystemMenu> queryParentList(Long parentId);

    List<SystemMenuVO> loadMenuByParentSn(String parentSn);
}
