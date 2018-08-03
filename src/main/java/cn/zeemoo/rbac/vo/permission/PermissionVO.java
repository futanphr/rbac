package cn.zeemoo.rbac.vo.permission;

import cn.zeemoo.rbac.utils.UserContext;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zeemoo
 * @date 2018/7/12 2:06
 */
@Data
public class PermissionVO {

    private Long id;
    private String name;
    private String expr;
    @JsonIgnore
    private Set<String> rolePermissions = new HashSet<>();

    @JsonProperty("LAY_CHECKED")
    public boolean getLayuiChecked() {
        return rolePermissions.contains(expr);
    }

}
