package cn.zeemoo.rbac.service;

import cn.zeemoo.rbac.domain.LoginInfo;
import cn.zeemoo.rbac.form.admin.role.UserRoleForm;
import cn.zeemoo.rbac.form.admin.role.UserRoleListForm;
import cn.zeemoo.rbac.form.admin.user.ResetPasswordForm;
import cn.zeemoo.rbac.form.admin.user.UpdateUserInfoForm;
import cn.zeemoo.rbac.form.admin.user.UserInfoSaveForm;
import cn.zeemoo.rbac.form.admin.user.UserListForm;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserInfoVO;
import cn.zeemoo.rbac.vo.user.role.UserRolesVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 登录信息业务类接口
 *
 * @author zeemoo
 * @date 2018/7/4 22:34
 */
public interface ILoginInfoService {

    /**
     * 新增或编辑的数据操作
     *
     * @param form
     * @return
     */
    UserInfoVO save(UserInfoSaveForm form);

    /**
     * 查询用户列表
     *
     * @param form
     * @return
     */
    ApiResult<List<UserInfoVO>> list(UserListForm form);

    /**
     * 禁用或启用
     *
     * @param id
     * @return
     */
    boolean ban(Long id);

    /**
     * 删除一个用户
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 设置新密码
     *
     * @param id
     * @param password
     */
    void setPassword(Long id, String password);

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    void login(String username, String password);

    /**
     * 增加用户登录错误次数，防止暴力破解
     *
     * @param username
     */
    void addErrTimes(String username);

    /**
     * 分配角色
     *
     * @param form
     */
    void assignRole(@Valid UserRoleForm form);

    /**
     * 列举用户权限
     *
     * @param form
     * @return
     */
    List<UserRolesVO> userRoles(@Valid UserRoleListForm form);

    /**
     * 修改用户信息
     *
     * @param form
     */
    void updateUserInfo(@Valid UpdateUserInfoForm form);

    /**
     * 修改密码
     * @param form
     */
    void resetPassword(@Valid ResetPasswordForm form);

    /**
     * 保存或修改
     * @param loginInfo
     * @return
     */
    int save(LoginInfo loginInfo);
}
