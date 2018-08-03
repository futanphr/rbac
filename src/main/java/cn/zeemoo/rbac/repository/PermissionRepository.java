package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 权限数据操作类
 *
 * @author zeemoo
 * @date 2018/7/10 23:48
 */
public interface PermissionRepository extends JpaRepository<Permission,Long>,JpaSpecificationExecutor<Permission> {
    Optional<Permission> findByExpr(String expr);
}
