package cn.zeemoo.rbac.mapper;

import cn.zeemoo.rbac.domain.RolePermission;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotBlank;
import java.util.List;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    List<RolePermission> selectAll();

    int updateByPrimaryKey(RolePermission record);

    /**
     * 查询角色集合内的角色-权限关联实体，此处也可以修改成返回值为<pre>Set<String></pre>,
     * 代码进行相应的删改。查询是必须先判断roleSns，如果为空或null，抛出异常
     *
     * @param roleSns
     * @return
     */
    List<RolePermission> selectAllByRoleSnIn(List<String> roleSns);

    /**
     * 查询某个角色编码的角色-权限关联实体，此处也可以修改成返回值为<pre>Set<String></pre>,
     * 代码进行相应的删改。
     *
     * @param roleSn
     * @return
     */
    List<RolePermission> selectAllByRoleSn(String roleSn);

    /**
     * 批量删除
     * @param rolePermissions
     * @return
     */
    int deleteInBatch(@Param("rolePermissions") List<RolePermission> rolePermissions);

    /**
     * 批量保存
     * @param collect
     * @return
     */
    int insertAll(List<RolePermission> collect);
}