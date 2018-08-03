package cn.zeemoo.rbac.controller;

import cn.zeemoo.rbac.annotation.PermMark;
import cn.zeemoo.rbac.form.admin.permission.PermissionListForm;
import cn.zeemoo.rbac.service.IPermissionService;
import cn.zeemoo.rbac.utils.ParamUtil;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * 权限控制器
 *
 * @author zeemoo
 * @date 2018/7/12 1:57
 */
@Controller
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping("list")
    @ResponseBody
    @PermMark("权限列表")
    public ApiResult list(@Valid PermissionListForm form, BindingResult result) throws Exception{
        ParamUtil.checkRequestParams(result);
        ApiResult<List<PermissionVO>> list = permissionService.list(form);
        return list;
    }

    @GetMapping(value={"/", "main"})
    @PermMark(value="权限列表首页",record = false)
    public String main()throws Exception{
        return "permission/main";
    }
}
