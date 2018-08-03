package cn.zeemoo.rbac.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 角色实体类
 *
 * @author zeemoo
 * @date 2018/7/9 21:06
 */
@Data
@Entity
@Table(name="rbac_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String roleSn;
    private Date createTime;
    private Date modifyTime;

}
