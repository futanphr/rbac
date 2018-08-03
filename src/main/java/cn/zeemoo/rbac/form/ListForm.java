package cn.zeemoo.rbac.form;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 请求列表数据基础请求类
 *
 * @author zeemoo
 * @date 2018/7/7 1:04
 */
@Data
public class ListForm {
    @Min(value = 1,message = "页码最小为1")
    int page=1;
    @Min(value = 1,message = "一页最少显示一行")
    int limit=10;

    public int getCurrentPage(){
        return page-1;
    }
}
