package cn.zeemoo.rbac.domain;

import lombok.Data;

/**
 * 权限实体类
 *
 * @author zeemoo
 * @date 2018/7/28 1:57
 */
@Data
public class Permission {
    private Long id;

    private String expr;

    private String name;

    private Boolean redirectOrNot;

}