package cn.zeemoo.rbac.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 操作记录实体类
 *
 * @author zeemoo
 * @date 2018/7/31 16:27
 */
@Data
@Entity
@Table(name="rbac_operation_log")
public class OperationLog {

    @Id
    private String id;
    private String expr;
    private Long userId;
    private String name;
    private String args;
    private Date createTime;
    private Date modifyTime;
}
