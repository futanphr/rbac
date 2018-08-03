package cn.zeemoo.rbac.form.admin.role.permisson;

import cn.zeemoo.rbac.form.ListForm;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author zeemoo
 * @date 2018/7/28 18:34
 */
@Data
public class RolePermissionListForm extends ListForm {

    @NotBlank(message = "角色编码必传")
    private String roleSn;
}
