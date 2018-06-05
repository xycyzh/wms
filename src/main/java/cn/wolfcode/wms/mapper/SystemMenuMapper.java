package cn.wolfcode.wms.mapper;

import cn.wolfcode.wms.domain.SystemMenu;
import cn.wolfcode.wms.query.QueryObject;
import cn.wolfcode.wms.vo.SystemMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SystemMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemMenu record);

    SystemMenu selectByPrimaryKey(Long id);

    SystemMenu selectByPrimaryKey1(Long id);

    List<SystemMenu> selectAll();

    int updateByPrimaryKey(SystemMenu record);

    int queryForCount(QueryObject qo);

    List<SystemMenu> queryForList(QueryObject qo);

    int deleteByParentId(Long parentId);

    List<SystemMenu> selectByParentId(Long parentId);

    List<SystemMenuVO> loadMenuByParentSn(String parentSn);

    List<SystemMenu> querySystemMenuByRoleId(Long roleId);

    List<SystemMenuVO> querySystemMenuByEmployeeIdAndSn(@Param("employeeId") Long employeeId, @Param("parentSn") String parentSn);
}