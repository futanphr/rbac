package cn.zeemoo.rbac.utils;

import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * 参数处理工具类
 *
 * @author zhang.shushan
 * @date 2018/5/22
 */
@Slf4j
public class ParamUtil {

    public static void checkRequestParams(BindingResult bindinResult){
        if(bindinResult.hasErrors()){
            StringBuilder message = new StringBuilder();
            List<FieldError> fieldErrors = bindinResult.getFieldErrors();
            for (FieldError err:
                 fieldErrors) {
                message.append(err.getField())
                        .append(":")
                        .append(err.getDefaultMessage())
                        .append(";");
            }
            throw new ApiException(ResultEnum.PARAMS_ERR.getCode(),message.toString());
        }
    }
}
