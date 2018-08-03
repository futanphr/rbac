package cn.zeemoo.rbac.vo;

import cn.zeemoo.rbac.domain.OperationLog;
import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.vo.user.UserOperationLogVO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * API信息返回体
 *
 * @author zhang.shushan
 * @date 2018/4/27
 */
@Setter
@Getter
public class ApiResult<T> {

    private Integer code;
    private String msg;
    private T data;
    private long count=0;
    private int totalPages;

    public ApiResult() {

    }
    /**
     * 操作失败返回
     * @param code
     * @param message
     * @return
     */
    public ApiResult error(Integer code,String message) {
        this.code=code;
        this.msg=message;
        return this;
    }

    public ApiResult error(){
        return error(9999,"系统繁忙");
    }
    public ApiResult success(){
        this.msg="操作成功";
        this.code=0;
        return this;
    }

    public ApiResult(ApiException e){
        this.code=e.getCode();
        this.msg=e.getMessage();
    }

    public ApiResult success(T data) {
        success();
        this.data=data;
        return this;
    }

    public ApiResult error(ResultEnum rs) {
        this.code=rs.getCode();
        this.msg=rs.getMessage();
        return this;
    }

    public ApiResult success(T data, long count) {
        success(data);
        this.count = count;
        return this;
    }

    public ApiResult success(T data, long count, int totalPages) {
        success(data,count);
        this.totalPages=totalPages;
        return this;
    }
}
