package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.LoginInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * 登录信息数据操作类
 *
 * @author zeemoo
 * @date 2018/7/4 22:24
 */
public interface LoginInfoRepository extends JpaRepository<LoginInfo, Long>, JpaSpecificationExecutor<LoginInfo> {
    /**
     * 按用户名查询
     *
     * @param username
     * @return
     */
    Optional<LoginInfo> findByUsername(String username);

    /**
     * 按用户名和加密后的密码查询
     *
     * @param username
     * @param encode
     * @return
     */
    Optional<LoginInfo> findByUsernameAndPassword(String username, String encode);

    Optional<LoginInfo> findByIsAdminIsTrue();
}
