package cn.zeemoo.rbac.vo.user;

import lombok.Data;

import java.util.Date;

/**
 * @author zeemoo
 * @date 2018/7/7 0:23
 */
@Data
public class UserInfoVO {
    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 是否是超级管理员 true-是 false-否
     */
    private Boolean isAdmin;
    /**
     * 是否禁用 true-禁用 false-启用
     */
    private Boolean isBanned;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 上一次登录时间
     */
    private Date lastLoginTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
}
