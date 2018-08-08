package cn.zeemoo.rbac.aspect;

import cn.zeemoo.rbac.annotation.PermMark;
import cn.zeemoo.rbac.domain.OperationLog;
import cn.zeemoo.rbac.domain.Permission;
import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.exception.PermissionException;
import cn.zeemoo.rbac.exception.RbacLoginAuthException;
import cn.zeemoo.rbac.mapper.OperationLogMapper;
import cn.zeemoo.rbac.mapper.PermissionMapper;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.utils.UserInfo;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * 权限拦截器
 *
 * @author zeemoo
 * @date 2018/7/11 23:51
 */
@Aspect
@Component
@Order(100)
public class PermissionAspect {

    @Autowired
    private PermissionMapper permissionMapper;

    @Value("${server.servlet.login-path}")
    private String login;

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Pointcut(value = "@annotation(permMark)")
    public void adminPointCut(PermMark permMark) {
    }

    @Before(value = "adminPointCut(permMark)")
    public void permissionBefore(JoinPoint joinPoint,PermMark permMark) throws Exception {
        ProceedingJoinPoint point = (ProceedingJoinPoint) joinPoint;

        Signature signature = point.getSignature();

        UserInfo userInfo = UserContext.getUserInfo();

        // 没有登录，抛出登录异常
        if (userInfo == null) {
            throw new RbacLoginAuthException(login);
        }

        String className = signature.getDeclaringTypeName();
        String name = signature.getName();
        String expr = className + ":" + name;

        //操作日志
        if (permMark.record()) {
            OperationLog operationLog = new OperationLog();
            operationLog.setCreateTime(new Date());
            operationLog.setExpr(expr);
            operationLog.setModifyTime(new Date());
            operationLog.setId(UUID.randomUUID().toString());
            operationLog.setUserId(userInfo.getId());
            operationLog.setName(permMark.value());
            //记录请求参数，方便超管追溯
            operationLog.setArgs(point.getArgs().length>0&&!point.getArgs()[0].getClass().equals(Model.class)?JSON.toJSONString(point.getArgs()[0]):"");
            operationLogMapper.insert(operationLog);
        }


        //超管不拦截
        if (userInfo.getIsAdmin()) {
            return;
        }

        Set<String> permissions = UserContext.getPermissions();
        if (!permissions.contains(expr)) {
            Permission byExpr = permissionMapper.selectByExpr(expr);
            Boolean redirect = byExpr.getRedirectOrNot();
            //如果是跳转页面的权限，跑出跳转无权限页面的异常
            if (redirect) {
                throw new PermissionException();
            } else {
                //否则返回无权限的操作信息和code
                throw new ApiException(ResultEnum.NO_PERMISSION);
            }
        }
    }

}
