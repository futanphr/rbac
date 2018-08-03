package cn.zeemoo.rbac.exception;

import cn.zeemoo.rbac.enums.ResultEnum;
import lombok.Data;

/**
 * api接口异常类
 *
 * @author zhang.shushan
 * @date 2018/4/27
 */
@Data
public class ApiException extends RuntimeException {
    private Integer code;

    public ApiException(ResultEnum rs){
        super(rs.getMessage());
        this.code=rs.getCode();
    }

    public ApiException(Integer code, String message) {
        super(message);
        this.code=code;
    }

    @Override
    public String toString() {
        return "ApiException{code="+this.code+"，message="+getMessage()+"}";
    }
}
