package cn.zeemoo.rbac.enums;

import lombok.Getter;

/**
 * @author zhang.shushan
 * @date 2018/4/27
 */
@Getter
public enum ResultEnum {
    //结果code和信息
    PARAMS_ERR(1001, "参数错误")
    , NO_PERMISSION(9997, "无操作权限，如有需要请联系管理员")
    , AMINISTRATOR_ERR(9998, "不能对超级管理员进行此操作")
    , UNKNOWN_ERR(9999, "未知错误")
    , USER_NOT_EXIST(1002, "用户不存在")
    , USERNAME_ALREADY_BIND(1003, "用户名已经被使用")
    , PASSWORD_LENTH_INVALID(1004,"密码长度应在6-16之间" )
    , PASSWORD_TOO_WEEK(1005,"密码太弱，密码长度必须在6到16位之间,并同时包含大小写字母，数字，特殊字符（@#*&%_.）" )

    , ROLE_NAME_EXIST(1006,"角色名已存在" )
    , ROLE_SN_EXIST(1007, "角色编码已存在")
    , ROLE_NOT_EXIST(1008, "角色不存在")
    , LOGIN_ERR(1009, "用户名或密码错误")
    , USER_IS_BANNED(1010,"用户已被禁用，请联系管理员" )
    , PASSWORD_ERR(1011,"密码错误" );

    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
