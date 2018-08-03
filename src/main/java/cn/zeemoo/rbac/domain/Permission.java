package cn.zeemoo.rbac.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 权限实体类
 *
 * @author zeemoo
 * @date 2018/7/10 22:09
 */
@Data
@Entity
@Table(name="rbac_permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String expr;
    private Boolean redirectOrNot;
}
