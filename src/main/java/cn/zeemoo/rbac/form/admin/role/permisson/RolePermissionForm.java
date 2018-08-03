package cn.zeemoo.rbac.form.admin.role.permisson;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限分配请求参数
 *
 * @author zeemoo
 * @date 2018/7/26 17:25
 */
@Data
public class RolePermissionForm {
    @NotBlank(message = "角色编码必传")
    private String roleSn;
    @NotBlank(message = "权限表达式必传")
    @Pattern(regexp = "\\[\\S*\\]",message = "必须按json数组格式传")
    private String exprs;
}
