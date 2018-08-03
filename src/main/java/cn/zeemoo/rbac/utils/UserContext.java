package cn.zeemoo.rbac.utils;

import cn.zeemoo.rbac.domain.LoginInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * 用户词典
 *
 * @author zeemoo
 * @date 2018/7/12 0:36
 */
public class UserContext {

    private final static String USER_IN_SESSION = "token_user";
    private final static String PERMS_IN_SESSION = "perms_user";

    /**
     * 保存登录用户的信息
     *
     * @param loginInfo
     */
    public static void setUserInfo(LoginInfo loginInfo) {
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(loginInfo, userInfo);
        userInfo.setLoginJustNow(true);
        getSession().setAttribute(USER_IN_SESSION, userInfo);
    }

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    public static UserInfo getUserInfo() {
        return (UserInfo) getSession().getAttribute(USER_IN_SESSION);
    }

    /**
     * 保存权限信息
     *
     * @param permissionExprs
     */
    public static void setPermissions(Set<String> permissionExprs) {
        getSession().setAttribute(PERMS_IN_SESSION, permissionExprs);
    }

    /**
     * 获取用户所拥有的权限
     *
     * @return
     */
    public static Set<String> getPermissions() {
        return (Set<String>) getSession().getAttribute(PERMS_IN_SESSION);
    }

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request;
    }

    /**
     * 获取上下文路径
     *
     * @return
     */
    public static String getContextPath() {
        return getRequest().getContextPath();
    }

    /**
     * 设置用户信息
     * @param userInfo
     */
    public static void setUserInfo(UserInfo userInfo) {
        getSession().setAttribute(USER_IN_SESSION,userInfo);
    }
}
