package cn.zeemoo.rbac.mapper;

import cn.zeemoo.rbac.domain.LoginInfoRole;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface LoginInfoRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginInfoRole record);

    LoginInfoRole selectByPrimaryKey(Long id);

    List<LoginInfoRole> selectAll();

    int updateByPrimaryKey(LoginInfoRole record);

    List<LoginInfoRole> selectAllByLoginInfoId(Long userId);

    /**
     * 批量删除，集合不能为空，否则抛出异常
     * @param loginInfoRoles 用户-角色集合
     * @return 影响行数
     */
    int deleteInBatch(@Param(value="loginInfoRoles") List<LoginInfoRole> loginInfoRoles);

    /**
     * 批量新增，集合不能为空，否则抛出异常
     * @param loginInfoRoles
     * @return
     */
    int insertAll(@Param("loginInfoRoles") List<LoginInfoRole> loginInfoRoles);
}