package cn.zeemoo.rbac.config;

import cn.zeemoo.rbac.config.domain.XssShieldFilterCO;
import cn.zeemoo.rbac.filter.CorsFilter;
import cn.zeemoo.rbac.filter.XssShieldFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 过滤器配置
 *
 * @author zeemoo
 * @date 2018/7/29 15:40
 */
@Configuration
public class WebFilterConfig {

    @Autowired
    private XssShieldFilterCO xssShieldFilterCO;

    @Bean
    @Order(2)
    public XssShieldFilter xssShieldFilter() {
        XssShieldFilter xssShieldFilter = new XssShieldFilter();
        xssShieldFilter.setExcludeUrls(xssShieldFilterCO.getPaths());
        return xssShieldFilter;
    }

    @Bean
    @Order(1)
    public CorsFilter corsFilter() {
        return new CorsFilter();
    }
}
