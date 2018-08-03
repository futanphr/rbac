package cn.zeemoo.rbac.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色权限中间表实体类
 *
 * @author zeemoo
 * @date 2018/7/26 15:55
 */
@Data
@Entity
@Table(name = "rbac_role_permission")
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleSn;
    private String permissionExpr;
    private Date createTime;
    private Date modifyTime;

    public RolePermission(String roleSn, String permissionExpr) {
        this.roleSn = roleSn;
        this.permissionExpr = permissionExpr;
        this.createTime = new Date();
        this.modifyTime = new Date();
    }
}
