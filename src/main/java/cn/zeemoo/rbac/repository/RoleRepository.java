package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 角色数据操作类
 *
 * @author zeemoo
 * @date 2018/7/9 21:44
 */
public interface RoleRepository extends JpaRepository<Role,Long>,JpaSpecificationExecutor<Role>{
    /**
     * 是否有这个角色名存在
     * @param roleName
     * @return
     */
    Optional<Role> findByRoleName(String roleName);

    /**
     * 是否有这个角色编码存在
     * @param roleSn
     * @return
     */
    Optional<Role> findByRoleSn(String roleSn);

}
