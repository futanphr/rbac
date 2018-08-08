package cn.zeemoo.rbac.mapper;

import cn.zeemoo.rbac.domain.OperationLog;

import java.util.List;

public interface OperationLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(OperationLog record);

    OperationLog selectByPrimaryKey(String id);

    List<OperationLog> selectAll();

    int updateByPrimaryKey(OperationLog record);

    /**
     * 查询用户的日志
     * @param userId
     * @return
     */
    List<OperationLog> selectAllByUserId(Long userId);
}