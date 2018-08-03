package cn.zeemoo.rbac.vo.user.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author zeemoo
 * @date 2018/7/28 19:17
 */
@Data
public class UserRolesVO {
    private Long id;
    private String roleName;
    private String roleSn;
    @JsonIgnore
    private Set<String> userRoles = new HashSet<>();

    @JsonProperty("LAY_CHECKED")
    public boolean getChecked(){
        return userRoles.contains(roleSn);
    }
}
