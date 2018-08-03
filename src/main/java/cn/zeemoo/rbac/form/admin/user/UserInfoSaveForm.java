package cn.zeemoo.rbac.form.admin.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 登录用户信息
 *
 * @author zeemoo
 * @date 2018/7/7 0:05
 */
@Data
public class UserInfoSaveForm {
    /**
     * id
     */
    private Long id;
    /**
     * 用户名
     */
    @NotBlank(message = "用户名必填")
    @Pattern(regexp = "[\\w]{6,20}",message = "用户名必须为6位以上字母，数字，下划线的组合")
    private String username;
    /**
     * 真实姓名
     */
    @NotBlank(message = "真实姓名必填")
    private String realName;
    /**
     * 是否启用 true-启用 false-禁用
     */
    @NotNull(message = "是否启用？请给个明白态度")
    private Boolean isBanned=false;
    /**
     * 手机号
     */
    @NotBlank
    @Pattern(regexp = "^1[356789]\\d{9}$",message = "请填写正确的手机号")
    private String phone;

//    public Boolean getIsBanned(){
//        return this.isBanned;
//    }
//
//    public void setIsBanned(boolean isBaned){
//        this.isBanned=isBanned;
//    }

}
