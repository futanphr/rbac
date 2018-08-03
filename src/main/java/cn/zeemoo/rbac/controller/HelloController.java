package cn.zeemoo.rbac.controller;

import cn.zeemoo.rbac.annotation.PermMark;
import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.form.ListForm;
import cn.zeemoo.rbac.service.ILoginInfoService;
import cn.zeemoo.rbac.service.IOperationLogService;
import cn.zeemoo.rbac.utils.ParamUtil;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.utils.UserInfo;
import cn.zeemoo.rbac.vo.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * 主页控制器
 *
 * @author zeemoo
 * @date 2018/7/4 22:36
 */
@Controller
public class HelloController {

    @Autowired
    private ILoginInfoService loginInfoService;

    @Autowired
    private IOperationLogService operationLogService;

    @PostMapping("login/msg/read")
    @ResponseBody
    public void loginMsgRead()throws Exception{
        UserInfo userInfo = UserContext.getUserInfo();
        userInfo.setLoginJustNow(false);
        UserContext.setUserInfo(userInfo);
    }

    @PostMapping("operation/log/list")
    @ResponseBody
    @PermMark(value = "用户操作日志",record = false)
    public ApiResult listMyOperationLog(@Valid ListForm form, BindingResult bindingResult){
        ParamUtil.checkRequestParams(bindingResult);
        return operationLogService.listUserSOperationLog(form);
    }

    @RequestMapping("logout")
    public String logout() throws Exception {
        UserContext.getSession().invalidate();
        return "redirect:/login_w";
    }

    @RequestMapping(value = {"", "/", "main"})
    @PermMark(value = "后台管理首页",record = false)
    public String main() throws Exception {
        return "main";
    }

    /**
     * 登录页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("login_w")
    public String login_w() throws Exception {
        return "login";
    }

    /**
     * 登录接口
     *
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping("sublogin")
    @ResponseBody
    public ApiResult sublogin(String username, String password) throws Exception {
        try {
            loginInfoService.login(username, password);
        } catch (ApiException e) {
            e.printStackTrace();
            if (e.getCode().equals(ResultEnum.LOGIN_ERR.getCode())) {
                loginInfoService.addErrTimes(username);
            }
            throw e;
        }
        return new ApiResult().success();
    }
}
