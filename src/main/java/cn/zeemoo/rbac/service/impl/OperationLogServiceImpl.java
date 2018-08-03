package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.OperationLog;
import cn.zeemoo.rbac.form.ListForm;
import cn.zeemoo.rbac.repository.OperationLogRepository;
import cn.zeemoo.rbac.service.IOperationLogService;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.utils.UserInfo;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserOperationLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户操作日志业务实现类
 *
 * @author zeemoo
 * @date 2018/7/31 23:12
 */
@Service
public class OperationLogServiceImpl implements IOperationLogService {

    @Autowired
    private OperationLogRepository repository;

    /**
     * 分页查询当前用户操作日志
     *
     * @param form
     * @return
     */
    @Override
    public ApiResult<List<UserOperationLogVO>> listUserSOperationLog(ListForm form) {
        UserInfo userInfo = UserContext.getUserInfo();
        Long id = userInfo.getId();
        Page<OperationLog> all = repository.findAllByUserId(id, PageRequest.of(form.getCurrentPage(), form.getLimit(), Sort.Direction.DESC, "createTime"));
        return new ApiResult<>().success(all.getContent(), all.getTotalElements(),all.getTotalPages());
    }
}
