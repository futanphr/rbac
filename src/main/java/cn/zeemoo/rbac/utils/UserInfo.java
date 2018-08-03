package cn.zeemoo.rbac.utils;

import lombok.Data;

import java.util.Date;

/**
 * 用户信息
 *
 * @author zeemoo
 * @date 2018/7/27 21:12
 */
@Data
public class UserInfo {
    private Long id;
    private String username;
    private String realName;
    private String phone;
    private Boolean isBanned;
    private Date lastLoginTime;
    private String token;
    private Boolean isAdmin;
    //是否刚登录
    private Boolean loginJustNow;
}
