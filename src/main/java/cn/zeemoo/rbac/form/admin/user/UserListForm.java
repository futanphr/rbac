package cn.zeemoo.rbac.form.admin.user;

import cn.zeemoo.rbac.form.ListForm;
import lombok.Data;

/**
 * 查询用户列表
 *
 * @author zeemoo
 * @date 2018/7/7 1:03
 */
@Data
public class UserListForm extends ListForm {

    private String username;
}
