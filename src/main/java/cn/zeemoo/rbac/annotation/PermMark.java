package cn.zeemoo.rbac.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限注解
 *
 * @author zeemoo
 * @date 2018/7/10 21:47
 */
@Target(value={ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PermMark {

    /**
     * 权限名称
     * @return
     */
    String value();

    /**
     * 是否需要记录 true-需要 false不需要
     * @return
     */
    boolean record() default true;

}
