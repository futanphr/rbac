package cn.zeemoo.rbac.form.admin.role;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zeemoo
 * @date 2018/7/9 22:09
 */
@Data
public class RoleSaveForm {
    private Long id;
    @NotBlank(message = "角色名不能为空")
    private String roleName;
    @NotBlank(message = "角色编码不能为空")
    private String roleSn;
}
