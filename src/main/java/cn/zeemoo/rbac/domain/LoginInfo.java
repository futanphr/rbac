package cn.zeemoo.rbac.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 登录信息类
 *
 * @author zeemoo
 * @date 2018/7/4 21:57
 */
@Data
@Entity
@Table(name = "rbac_login_info")
public class LoginInfo {
    public static Boolean BAN = false;
    public static Boolean FREE = true;
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 是否是超级管理员 true-是 false-否
     */
    private Boolean isAdmin;
    /**
     * 是否禁用 true-启用 false-禁用
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
     * 登录密令
     */
    private String token;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 登录错误次数
     */
    private Integer errorTimes;
}
