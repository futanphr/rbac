package cn.zeemoo.rbac.vo.role;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zeemoo
 * @date 2018/7/9 22:13
 */
@Data
public class RoleVO {
    private Long id;
    private String roleName;
    private String roleSn;
    private Date createTime;
    private Date modifyTime;
}
