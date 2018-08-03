package cn.zeemoo.rbac.form.admin.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author zeemoo
 * @date 2018/7/29 2:09
 */
@Data
public class ResetPasswordForm {
    @NotBlank
    private String newPassword;
    @NotBlank
    private String oldPassword;
}
