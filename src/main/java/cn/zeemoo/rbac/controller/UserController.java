package cn.zeemoo.rbac.controller;

import cn.zeemoo.rbac.annotation.PermMark;
import cn.zeemoo.rbac.form.admin.role.UserRoleListForm;
import cn.zeemoo.rbac.form.admin.user.ResetPasswordForm;
import cn.zeemoo.rbac.form.admin.user.UpdateUserInfoForm;
import cn.zeemoo.rbac.form.admin.user.UserInfoSaveForm;
import cn.zeemoo.rbac.form.admin.user.UserListForm;
import cn.zeemoo.rbac.service.ILoginInfoService;
import cn.zeemoo.rbac.utils.ParamUtil;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserInfoVO;
import cn.zeemoo.rbac.form.admin.role.UserRoleForm;
import cn.zeemoo.rbac.vo.user.role.UserRolesVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户管理
 *
 * @author zeemoo
 * @date 2018/7/5 23:40
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private ILoginInfoService userService;

    @PostMapping("password/reset")
    @ResponseBody
    @PermMark("修改个人密码")
    public ApiResult resetPassword(@Valid ResetPasswordForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        userService.resetPassword(form);
        return new ApiResult().success();
    }

    @PostMapping("userInfo/update")
    @ResponseBody
    @PermMark("修改个人信息")
    public ApiResult updateUserInfo(@Valid UpdateUserInfoForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        userService.updateUserInfo(form);
        return new ApiResult().success();
    }

    @PostMapping("role/all")
    @ResponseBody
    @PermMark("用户-角色列表")
    public ApiResult userRoles(@Valid UserRoleListForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        List<UserRolesVO> userRoles = userService.userRoles(form);
        return new ApiResult().success(userRoles);
    }

    @PostMapping("role/assign")
    @ResponseBody
    @PermMark("用户的角色分配")
    public ApiResult assignRole(@Valid UserRoleForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        userService.assignRole(form);
        return new ApiResult().success();
    }

    @PostMapping("setpwd")
    @ResponseBody
    @PermMark("用户密码重置")
    public ApiResult setPassword(@RequestParam(defaultValue = "0") Long id, @RequestParam(defaultValue = "") String password) throws Exception {
        userService.setPassword(id, password);
        return new ApiResult().success();
    }

    @PostMapping("delete")
    @ResponseBody
    @PermMark("用户删除")
    public ApiResult delete(@RequestParam(defaultValue = "0") Long id) throws Exception {
        userService.delete(id);
        return new ApiResult().success();
    }

    @PostMapping("ban")
    @ResponseBody
    @PermMark("用户禁用")
    public ApiResult ban(@RequestParam(defaultValue = "0") Long id) throws Exception {
        boolean isBanned = userService.ban(id);
        return new ApiResult().success(isBanned);
    }

    @PostMapping("save")
    @ResponseBody
    @PermMark("用户添加/编辑")
    public ApiResult save(@Valid UserInfoSaveForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        userService.save(form);
        return new ApiResult().success();
    }

    @PostMapping("list")
    @ResponseBody
    @PermMark(value="用户列表",record = false)
    public ApiResult list(@Valid UserListForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        ApiResult<List<UserInfoVO>> list = userService.list(form);
        return list;
    }

    @RequestMapping(value = {"/", "main"})
    @PermMark(value="用户管理首页",record = true)
    public String main() {
        return "user/main";
    }
}
