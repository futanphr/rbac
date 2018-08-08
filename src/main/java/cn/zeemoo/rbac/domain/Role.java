package cn.zeemoo.rbac.domain;

import lombok.Data;

import java.util.Date;

/**
 * 角色实体类
 *
 * @author zeemoo
 * @date 2018/7/9 21:06
 */
@Data
public class Role {

    private Long id;
    private String roleName;
    private String roleSn;
    private Date createTime;
    private Date modifyTime;

}