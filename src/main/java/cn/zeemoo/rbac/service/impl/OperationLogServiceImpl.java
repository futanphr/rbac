package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.OperationLog;
import cn.zeemoo.rbac.form.ListForm;
import cn.zeemoo.rbac.mapper.OperationLogMapper;
import cn.zeemoo.rbac.service.IOperationLogService;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.utils.UserInfo;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserOperationLogVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private OperationLogMapper operationLogMapper;

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
        PageHelper.startPage(form.getPage(),form.getLimit(),true);
        Page<OperationLog> all = (Page<OperationLog>) operationLogMapper.selectAllByUserId(userInfo.getId());
        return new ApiResult<>().success(all.getResult(), all.getTotal(),all.getPages());
    }
}
