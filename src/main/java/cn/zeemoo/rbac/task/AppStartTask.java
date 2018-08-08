package cn.zeemoo.rbac.task;

import cn.zeemoo.rbac.annotation.PermMark;
import cn.zeemoo.rbac.domain.LoginInfo;
import cn.zeemoo.rbac.domain.Permission;
import cn.zeemoo.rbac.mapper.LoginInfoMapper;
import cn.zeemoo.rbac.service.IPermissionService;
import cn.zeemoo.rbac.utils.PasswordUtils;
import cn.zeemoo.rbac.utils.PermissionUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 应用启动事件
 *
 * @author zeemoo
 * @date 2018/7/10 22:01
 */
@Component
@Setter
public class AppStartTask implements ApplicationContextAware, CommandLineRunner {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Autowired
    private PasswordUtils passwordUtils;

    private ApplicationContext applicationContext;

    public void run(String... args) {
        scanPermission();
        createAdmin();
    }

    private void createAdmin() {
        LoginInfo byIsAdminIsTrue = loginInfoMapper.selectByIsAdminIsTrue();
        if (byIsAdminIsTrue!=null) {
            return;
        }
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setIsBanned(LoginInfo.FREE);
        loginInfo.setErrorTimes(0);
        loginInfo.setLastLoginTime(new Date());
        loginInfo.setPassword(passwordUtils.encode("XiaoMingTongXue@123"));
        loginInfo.setModifyTime(new Date());
        loginInfo.setCreateTime(new Date());
        loginInfo.setIsAdmin(true);
        loginInfo.setPhone("00000000000");
        loginInfo.setRealName("超级管理员");
        loginInfo.setUsername("wordless");
        loginInfoMapper.insert(loginInfo);
    }

    public void scanPermission() {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(Controller.class);
        Collection<Object> values = beansWithAnnotation.values();
        List<Permission> permissions = new ArrayList<>();
        values.forEach(o -> {
            //处理cglib代理对象导致的不能获取注解的问题
            String typeName = o.getClass().getTypeName();
            int i = typeName.indexOf("$");
            if (i != -1) {
                //根据名字重新获取字节码对象
                try {
                    Method[] methods = Class.forName(typeName.substring(0, i)).getDeclaredMethods();
                    Arrays.stream(methods).forEach(method -> {
                        PermMark annotation = method.getAnnotation(PermMark.class);
                        if (annotation != null) {
                            Permission permission = new Permission();
                            permission.setName(annotation.value());
                            boolean b = method.getAnnotation(ResponseBody.class) != null || o.getClass().getAnnotation(ResponseBody.class) != null;
                            permission.setRedirectOrNot(!b);
                            permission.setExpr(PermissionUtil.getPermissionExpr(method));
                            permissions.add(permission);
                        }
                    });
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        });

        if (!permissions.isEmpty()) {
            permissionService.saveAll(permissions);
        }
    }

}
