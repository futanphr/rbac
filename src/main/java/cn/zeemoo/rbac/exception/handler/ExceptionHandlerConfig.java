package cn.zeemoo.rbac.exception.handler;

import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.exception.PermissionException;
import cn.zeemoo.rbac.exception.RbacLoginAuthException;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.vo.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 控制器异常捕手
 *
 * @author zhang.shushan
 * @date 2018/5/22
 */
@ControllerAdvice
@Component
@Slf4j
public class ExceptionHandlerConfig {

    @ExceptionHandler(PermissionException.class)
    public String noPermission(PermissionException e) {
        return "/noPermission";
    }

    @ExceptionHandler(RbacLoginAuthException.class)
    public String loginAuth(RbacLoginAuthException e) {
        return "redirect:" + e.getRedirectUrl();
    }

    /**
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ApiResult apiExceptionHandler(Exception e) {
        log.error("【请求失败】{}\n\r", e.getStackTrace());
        e.printStackTrace();
        if (e instanceof ApiException) {
            ApiException exception = (ApiException) e;
            return new ApiResult().error(exception.getCode(), exception.getMessage());
        }
        return new ApiResult().error();
    }

}
