package cn.zeemoo.rbac.mapper;

import cn.zeemoo.rbac.domain.Role;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    /**
     * 角色列表查询
     * @param keyword
     * @return
     */
    List<Role> selectAllByRoleNameLikeOrRoleSnLike(String keyword);

    /**
     * 角色名查询
     * @param roleName
     * @return
     */
    Role selectByRoleName(String roleName);

    /**
     * 按角色编码查询
     * @param roleSn
     * @return
     */
    Role selectByRoleSn(String roleSn);
}