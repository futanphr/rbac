package cn.zeemoo.rbac.config.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤器配置参数对象
 *
 * @author zeemoo
 * @date 2018/7/29 16:20
 */
@Component
@ConfigurationProperties(prefix = "filter.xss.exclude")
@Data
public class XssShieldFilterCO {
    private List<String> paths = new ArrayList<>();
}
