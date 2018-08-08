package cn.zeemoo.rbac.domain;

import lombok.Data;

import java.util.Date;
/**
 * 角色权限实体类
 *
 * @author zeemoo
 * @date 2018/7/9 21:06
 */
@Data
public class RolePermission {
    private Long id;

    private String permissionExpr;

    private String roleSn;

    private Date createTime;

    private Date modifyTime;

    public RolePermission(String roleSn, String permissionExpr) {
        this.roleSn=roleSn;
        this.permissionExpr=permissionExpr;
        this.createTime=new Date();
        this.modifyTime=new Date();
    }
}