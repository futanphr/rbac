package cn.zeemoo.rbac.controller;

import cn.zeemoo.rbac.annotation.PermMark;
import cn.zeemoo.rbac.form.admin.role.RoleListForm;
import cn.zeemoo.rbac.form.admin.role.RoleSaveForm;
import cn.zeemoo.rbac.form.admin.role.permisson.RolePermissionForm;
import cn.zeemoo.rbac.form.admin.role.permisson.RolePermissionListForm;
import cn.zeemoo.rbac.service.IRoleService;
import cn.zeemoo.rbac.utils.ParamUtil;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import cn.zeemoo.rbac.vo.role.RoleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 角色控制器
 *
 * @author zeemoo
 * @date 2018/7/9 21:52
 */
@Controller
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping("permission/assign")
    @ResponseBody
    @PermMark("角色的权限分配")
    public ApiResult assignPermissions(@Valid RolePermissionForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        roleService.assignPermissions(form);
        return new ApiResult().success();
    }

    @PostMapping("permission/all")
    @ResponseBody
    @PermMark("角色-权限列表查询")
    public ApiResult rolePermissions(@Valid RolePermissionListForm form, BindingResult bindingResult)throws Exception{
        ParamUtil.checkRequestParams(bindingResult);
        List<PermissionVO> rolePermissions = roleService.rolePermissions(form);
        return new ApiResult().success(rolePermissions,rolePermissions.size());
    }

    @PostMapping("delete")
    @ResponseBody
    @PermMark("角色删除")
    public ApiResult delete(@RequestParam(defaultValue = "0") Long id) throws Exception {
        roleService.delete(id);
        return new ApiResult().success();
    }

    @PostMapping("list")
    @ResponseBody
    @PermMark("角色列表")
    public ApiResult list(@Valid RoleListForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        ApiResult<List<RoleVO>> list = roleService.list(form);
        return list;
    }

    /**
     * 新增或编辑
     *
     * @param form
     * @param bindingResult
     * @return
     * @throws Exception
     */
    @PostMapping("save")
    @ResponseBody
    @PermMark("角色添加/编辑")
    public ApiResult save(@Valid RoleSaveForm form, BindingResult bindingResult) throws Exception {
        ParamUtil.checkRequestParams(bindingResult);
        RoleVO vo = roleService.save(form);
        return new ApiResult().success(vo);
    }

    /**
     * 主页控制器
     *
     * @return
     * @throws Exception
     */
    @GetMapping(value = {"/", "main"})
    @PermMark(value="角色管理首页",record = false)
    public String main() throws Exception {
        return "role/main";
    }
}
