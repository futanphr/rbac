package cn.zeemoo.rbac.vo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 用户操作日志
 *
 * @author zeemoo
 * @date 2018/7/31 23:07
 */
@Data
public class UserOperationLogVO {

    @JsonFormat(pattern = "y-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String name;
}
