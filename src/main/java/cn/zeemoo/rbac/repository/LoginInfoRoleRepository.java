package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.LoginInfoRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 用户角色中间表数据操作类
 *
 * @author zeemoo
 * @date 2018/7/28 2:00
 */
public interface LoginInfoRoleRepository extends JpaRepository<LoginInfoRole, Long>, JpaSpecificationExecutor<LoginInfoRole> {

    /**
     * 查出该用户的所有角色
     * @param loginInfoId
     * @return
     */
    List<LoginInfoRole> findAllByLoginInfoId(Long loginInfoId);
}
