package cn.zeemoo.rbac.form.admin.permission;

import cn.zeemoo.rbac.form.ListForm;
import lombok.Data;

/**
 * @author zeemoo
 * @date 2018/7/12 2:02
 */
@Data
public class PermissionListForm extends ListForm{
    private String keyword;
}
