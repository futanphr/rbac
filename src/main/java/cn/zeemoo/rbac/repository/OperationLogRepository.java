package cn.zeemoo.rbac.repository;

import cn.zeemoo.rbac.domain.OperationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 用户操作记录数据操作类
 *
 * @author zeemoo
 * @date 2018/7/31 16:36
 */
public interface OperationLogRepository extends JpaRepository<OperationLog, String>, JpaSpecificationExecutor<OperationLog> {

    /**
     * 按用户id分页查询
     * @param userId
     * @param pageable
     * @return
     */
    Page<OperationLog> findAllByUserId(Long userId, Pageable pageable);
}
