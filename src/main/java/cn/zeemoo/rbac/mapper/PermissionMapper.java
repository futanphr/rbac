package cn.zeemoo.rbac.mapper;

import cn.zeemoo.rbac.domain.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Long id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    /**
     * 查询列表
     *
     * @param keyword
     * @return
     */
    List<Permission> selectAllByName(String keyword);

    /**
     * 批量新增，当permissions为空或null时抛出异常
     *
     * @param permissions
     * @return
     */
    int insertAll(@Param("permissions") List<Permission> permissions);

    /**
     * 按表达式查询
     *
     * @param expr
     * @return
     */
    Permission selectByExpr(String expr);
}