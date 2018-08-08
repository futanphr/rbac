package cn.zeemoo.rbac.domain;

import lombok.Data;

import java.util.Date;

/**
 * 操作记录实体类
 *
 * @author zeemoo
 * @date 2018/7/31 16:27
 */
@Data
public class OperationLog {

    private String id;
    private String expr;
    private Long userId;
    private String name;
    private String args;
    private Date createTime;
    private Date modifyTime;
}