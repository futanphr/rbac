package cn.zeemoo.rbac.service;

import cn.zeemoo.rbac.form.ListForm;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserOperationLogVO;

import java.util.List;

/**
 * 操作日志业务接口
 *
 * @author zeemoo
 * @date 2018/7/31 21:53
 */
public interface IOperationLogService {

    /**
     * 分页查询当前用户操作日志
     * @param form
     * @return
     */
    ApiResult<List<UserOperationLogVO>> listUserSOperationLog(ListForm form);

}
