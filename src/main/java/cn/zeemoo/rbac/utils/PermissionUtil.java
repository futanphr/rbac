package cn.zeemoo.rbac.utils;

import java.lang.reflect.Method;

/**
 * 权限表达式工具
 *
 * @author zeemoo
 * @date 2018/7/10 23:21
 */
public class PermissionUtil {

    /**
     * 权限表达式获取
     * @param m
     * @return
     */
    public static String getPermissionExpr(Method m){
        String typeName = m.getDeclaringClass().getTypeName();
        String name = m.getName();
        return typeName+":"+name;
    }
}
