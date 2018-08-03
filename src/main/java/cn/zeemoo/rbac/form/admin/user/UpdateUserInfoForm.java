package cn.zeemoo.rbac.form.admin.user;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author zeemoo
 * @date 2018/7/29 0:53
 */
@Data
public class UpdateUserInfoForm {

    @Pattern(regexp = "^1[356789]\\d{9}$",message = "请输入正确的手机号")
    private String phone;
}
