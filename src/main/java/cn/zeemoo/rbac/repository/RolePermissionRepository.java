package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 角色权限数据操作类
 *
 * @author zeemoo
 * @date 2018/7/26 16:57
 */
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long>, JpaSpecificationExecutor<RolePermission> {

    /**
     * 查出角色所拥有的所有权限
     * @param roleSn
     * @return
     */
    List<RolePermission> findAllByRoleSn(String roleSn);
}
