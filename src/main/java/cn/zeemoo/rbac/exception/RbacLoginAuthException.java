package cn.zeemoo.rbac.exception;

import lombok.Data;

/**
 * @author zeemoo
 * @date 2018/7/28 15:16
 */
@Data
public class RbacLoginAuthException extends RuntimeException {

    private String redirectUrl;

    public RbacLoginAuthException(String redirectUrl) {
        super("无登录凭证");
        this.redirectUrl = redirectUrl;
    }
}
