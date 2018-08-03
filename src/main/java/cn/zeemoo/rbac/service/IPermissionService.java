package cn.zeemoo.rbac.service;

import cn.zeemoo.rbac.domain.Permission;
import cn.zeemoo.rbac.form.admin.permission.PermissionListForm;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;

import java.util.List;

/**
 * 权限业务接口类
 *
 * @author zeemoo
 * @date 2018/7/10 23:49
 */
public interface IPermissionService {

    /**
     * 查询权限列表
     * @param form
     * @return
     */
    ApiResult<List<PermissionVO>> list(PermissionListForm form);

    /**
     * 查出所有的权限
     * @return
     */
    List<String> findAllExpr();

    /**
     * 去重并保存
     * @param permissions
     */
    void saveAll(List<Permission> permissions);
}
