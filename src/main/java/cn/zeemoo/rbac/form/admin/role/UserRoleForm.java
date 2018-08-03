package cn.zeemoo.rbac.form.admin.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author zeemoo
 * @date 2018/7/28 1:31
 */
@Data
public class UserRoleForm {
    @NotNull
    private Long userId;

    @Pattern(regexp = "\\[\\S*\\]", message = "必须按json数组格式传")
    private String roleSns;
}
