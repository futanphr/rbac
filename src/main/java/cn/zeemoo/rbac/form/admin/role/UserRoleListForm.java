package cn.zeemoo.rbac.form.admin.role;

import cn.zeemoo.rbac.form.ListForm;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author zeemoo
 * @date 2018/7/28 19:12
 */
@Data
public class UserRoleListForm extends ListForm {

    @NotNull
    private Long userId;

}
