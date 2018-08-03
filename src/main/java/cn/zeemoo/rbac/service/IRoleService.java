package cn.zeemoo.rbac.service;

import cn.zeemoo.rbac.domain.Role;
import cn.zeemoo.rbac.form.admin.role.RoleListForm;
import cn.zeemoo.rbac.form.admin.role.RoleSaveForm;
import cn.zeemoo.rbac.form.admin.role.permisson.RolePermissionForm;
import cn.zeemoo.rbac.form.admin.role.permisson.RolePermissionListForm;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import cn.zeemoo.rbac.vo.role.RoleVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色业务接口
 *
 * @author zeemoo
 * @date 2018/7/9 21:50
 */
public interface IRoleService {
    /**
     * 新增或修改一个角色
     *
     * @param form
     * @return
     */
    RoleVO save(@Valid RoleSaveForm form);

    /**
     * 返回角色列表
     *
     * @param form
     * @return
     */
    ApiResult<List<RoleVO>> list(@Valid RoleListForm form);

    /**
     * 删除一条数据
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 角色分配权限
     *
     * @param form
     */
    void assignPermissions(@Valid RolePermissionForm form);

    /**
     * 角色权限回显
     *
     * @param form
     * @return
     */
    List<PermissionVO> rolePermissions(@Valid RolePermissionListForm form);

    /**
     * 查出所有的角色
     *
     * @return
     */
    List<Role> findAll();
}
