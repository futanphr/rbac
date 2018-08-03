package cn.zeemoo.rbac.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户-角色关联实体类
 *
 * @author zeemoo
 * @date 2018/7/28 1:57
 */
@Data
@Entity
@Table(name = "rabc_login_info_role")
public class LoginInfoRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleSn;
    private Long loginInfoId;
    private Date createTime;
    private Date modifyTime;

    public LoginInfoRole(Long loginInfoId, String roleSn) {
        this.roleSn=roleSn;
        this.loginInfoId=loginInfoId;
        this.createTime=new Date();
        this.modifyTime = new Date();
    }
}
