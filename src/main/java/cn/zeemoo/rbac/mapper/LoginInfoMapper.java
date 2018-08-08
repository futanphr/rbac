package cn.zeemoo.rbac.mapper;

import cn.zeemoo.rbac.domain.LoginInfo;
import com.github.pagehelper.Page;

import java.util.List;

public interface LoginInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(LoginInfo record);

    LoginInfo selectByPrimaryKey(Long id);

    List<LoginInfo> selectAll();

    int updateByPrimaryKey(LoginInfo record);

    /**
     * 按用户名查询
     * @param username
     * @return
     */
    LoginInfo selectByUsername(String username);

    /**
     * 动态列表查询，查出除超级管理员以外的用户
     * @param username
     * @return
     */
    List<LoginInfo> selectAllByUsernameLikeAndIsAdminIsFalse(String username);

    /**
     * 查询超级管理员
     * @return
     */
    LoginInfo selectByIsAdminIsTrue();
}