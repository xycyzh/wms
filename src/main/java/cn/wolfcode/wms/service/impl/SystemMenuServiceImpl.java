package cn.wolfcode.wms.service.impl;

import cn.wolfcode.wms.domain.Employee;
import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.mapper.SystemMenuMapper;
import cn.wolfcode.wms.query.PageResult;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.service.ISystemMenuService;
import cn.wolfcode.wms.util.UserContext;
import cn.wolfcode.wms.vo.SystemMenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {
    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Override
    public List<SystemMenu> selectAll() {
        return systemMenuMapper.selectAll();
    }

    @Override
    public SystemMenu get(Long id) {
        return systemMenuMapper.selectByPrimaryKey(id);
    }

    @Override
    public void saveOrUpdate(SystemMenu systemMenu) {
        if (systemMenu.getId() != null) {
            systemMenuMapper.updateByPrimaryKey(systemMenu);
        } else {
            systemMenuMapper.insert(systemMenu);
        }
    }

    @Override
    public void delete(Long id) {
        //删除子菜单
        //int row = systemMenuMapper.deleteByParentId(id);
        //删除自己
        //systemMenuMapper.deleteByPrimaryKey(id);
        recursion(id);
    }

    private void recursion(Long id) {
        //找到自己的儿子
        List<SystemMenu> parent = systemMenuMapper.selectByParentId(id);
        if (parent == null) {
            //没找到儿子,删除自己就ok
            systemMenuMapper.deleteByPrimaryKey(id);
        } else {
            //找到了儿子
            //删除自己
            systemMenuMapper.deleteByPrimaryKey(id);
            //删除儿子
            for (SystemMenu systemMenu : parent) {
                systemMenuMapper.deleteByParentId(systemMenu.getId());
            }
            for (SystemMenu systemMenu : parent) {
                recursion(systemMenu.getId());
            }
        }
    }

    @Override
    public PageResult query(QueryObject qo) {
        int count = systemMenuMapper.queryForCount(qo);
        if (count == 0) {
            return new PageResult(qo.getPageSize());
        }
        List<SystemMenu> list = systemMenuMapper.queryForList(qo);
        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), count, list);
    }

//    /**
//     * 查询自己的爸爸
//     *
//     * @param parentId 爸爸的id
//     * @return 返回爸爸们的集合
//     */
//    @Override
//    public List<SystemMenu> queryParentList(Long parentId) {
//        List<SystemMenu> parentList = new ArrayList<>();
//        while (parentId != null) {
//            SystemMenu parent = systemMenuMapper.selectByPrimaryKey(parentId);
//            if (parent != null) {
//                parentList.add(parent);
//                parent = parent.getParent();
//                if (parent != null) {
//                    parentId = parent.getId();
//                }
//            }
//            if (parent == null) {
//                break;
//            }
//        }
//        Collections.reverse(parentList);
//        return parentList;
//    }

    /**
     * 查询自己的爸爸(用mybatis)
     *
     * @param parentId 爸爸的id
     * @return 返回爸爸们的集合
     */
    @Override
    public List<SystemMenu> queryParentList(Long parentId) {
        List<SystemMenu> parentList = new ArrayList<>();
        SystemMenu parent = systemMenuMapper.selectByPrimaryKey1(parentId);
        while (parent != null) {
            parentList.add(parent);
            parent = parent.getParent();
        }
        Collections.reverse(parentList);
        return parentList;
    }

    @Override
    public List<SystemMenuVO> loadMenuByParentSn(String parentSn) {
        //在service层控制相应角色的所拥有的角色的可见性
        //如果是管理员,则返回全部
        Employee user = UserContext.getUser();
        if (user.isAdmin()) {
            return systemMenuMapper.loadMenuByParentSn(parentSn);
        }
        //通过用户的id和sn来查询用户的角色的菜单
        return systemMenuMapper.querySystemMenuByEmployeeIdAndSn(user.getId(), parentSn);
    }
}
