package cn.zeemoo.rbac.filter;

import cn.zeemoo.rbac.filter.domain.MyHttpServletRequest;
import lombok.Data;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * XSS攻击过滤器
 *
 * @author zhang.shushan
 * @date 2018/5/15
 */
@Slf4j
@Setter
public class XssShieldFilter implements Filter {

    private List<String> excludeUrls = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("【XSS防御过滤器】初始化启动");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String servletPath = request.getServletPath();
        if(!excludeUrls.contains(servletPath)){
            request = new MyHttpServletRequest(request);
            chain.doFilter(request, res);
            return;
        }
        chain.doFilter(req,res);
    }

    @Override
    public void destroy() {
        log.info("【XSS防御过滤器】关闭");
    }
}
