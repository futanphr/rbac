package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.LoginInfo;
import cn.zeemoo.rbac.domain.LoginInfoRole;
import cn.zeemoo.rbac.domain.Role;
import cn.zeemoo.rbac.domain.RolePermission;
import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.form.admin.role.UserRoleForm;
import cn.zeemoo.rbac.form.admin.role.UserRoleListForm;
import cn.zeemoo.rbac.form.admin.user.ResetPasswordForm;
import cn.zeemoo.rbac.form.admin.user.UpdateUserInfoForm;
import cn.zeemoo.rbac.form.admin.user.UserInfoSaveForm;
import cn.zeemoo.rbac.form.admin.user.UserListForm;
import cn.zeemoo.rbac.mapper.LoginInfoMapper;
import cn.zeemoo.rbac.mapper.LoginInfoRoleMapper;
import cn.zeemoo.rbac.mapper.RolePermissionMapper;
import cn.zeemoo.rbac.service.ILoginInfoService;
import cn.zeemoo.rbac.service.IRoleService;
import cn.zeemoo.rbac.utils.PasswordUtils;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserInfoVO;
import cn.zeemoo.rbac.vo.user.role.UserRolesVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录信息业务类
 *
 * @author zeemoo
 * @date 2018/7/7 0:17
 */
@Service
public class LoginInfoServiceImpl implements ILoginInfoService {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private LoginInfoRoleMapper loginInfoRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private IRoleService roleService;

    /**
     * 保存或修改
     *
     * @param loginInfo
     * @return
     */
    @Override
    public int save(LoginInfo loginInfo) {
        if (loginInfo.getId() == null) {
            loginInfo.setCreateTime(new Date());
            loginInfo.setModifyTime(new Date());
            return loginInfoMapper.insert(loginInfo);
        } else {
            loginInfo.setModifyTime(new Date());
            return loginInfoMapper.updateByPrimaryKey(loginInfo);
        }
    }

    /**
     * 修改密码
     *
     * @param form
     */
    @Override
    public void resetPassword(@Valid ResetPasswordForm form) {
        Long id = UserContext.getUserInfo().getId();
        LoginInfo loginInfo = loginInfoMapper.selectByPrimaryKey(id);
        if (loginInfo == null) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST);
        }
        if (!passwordUtils.isContainLetterAndNumAndSymbols(form.getNewPassword())) {
            throw new ApiException(ResultEnum.PASSWORD_TOO_WEEK);
        }
        if (!passwordUtils.encode(form.getOldPassword()).equalsIgnoreCase(loginInfo.getPassword())) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST.PASSWORD_ERR);
        }
        loginInfo.setPassword(passwordUtils.encode(form.getNewPassword()));
        this.save(loginInfo);
    }

    /**
     * 修改用户信息
     *
     * @param form
     */
    @Override
    public void updateUserInfo(@Valid UpdateUserInfoForm form) {
        LoginInfo loginInfo = loginInfoMapper.selectByPrimaryKey(UserContext.getUserInfo().getId());
        loginInfo.setPhone(form.getPhone());
        this.save(loginInfo);
        UserContext.setUserInfo(loginInfo);
    }

    @Override
    public List<UserRolesVO> userRoles(@Valid UserRoleListForm form) {
        List<LoginInfoRole> allByLoginInfoId = loginInfoRoleMapper.selectAllByLoginInfoId(form.getUserId());
        Set<String> userRoles = allByLoginInfoId.stream().map(loginInfoRole -> loginInfoRole.getRoleSn()).collect(Collectors.toSet());
        List<Role> roles = roleService.findAll();
        List<UserRolesVO> collect = roles.stream().map(role -> {
            UserRolesVO vo = new UserRolesVO();
            BeanUtils.copyProperties(role, vo);
            vo.setUserRoles(userRoles);
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }

    /**
     * 分配角色
     *
     * @param form
     */
    @Override
    public void assignRole(@Valid UserRoleForm form) {
        Long userId = form.getUserId();
        String json = form.getRoleSns();
        Set<String> roleSns = JSON.parseObject(json, new TypeReference<Set<String>>() {
        });
        List<LoginInfoRole> allByLoginInfoId = loginInfoRoleMapper.selectAllByLoginInfoId(form.getUserId());
        if(allByLoginInfoId.size()>0){
            loginInfoRoleMapper.deleteInBatch(allByLoginInfoId);
        }
        List<LoginInfoRole> collect = roleSns.stream().map(s -> new LoginInfoRole(userId, s)).collect(Collectors.toList());
        loginInfoRoleMapper.insertAll(collect);

    }

    /**
     * 增加用户登录错误次数，防止暴力破解
     *
     * @param username
     */
    @Override
    public void addErrTimes(String username) {
        LoginInfo loginInfo = loginInfoMapper.selectByUsername(username);
        if (loginInfo != null) {
            if (loginInfo.getErrorTimes() > 3) {
                loginInfo.setIsBanned(LoginInfo.BAN);
            }
            loginInfo.setErrorTimes(loginInfo.getErrorTimes() + 1);
            loginInfoMapper.updateByPrimaryKey(loginInfo);
        }
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void login(String username, String password) {
        LoginInfo loginInfo = loginInfoMapper.selectByUsername(username);
        //用户名用户是否存在
        if (loginInfo == null) {
            throw new ApiException(ResultEnum.LOGIN_ERR);
        }
        //是否被禁用
        if (loginInfo.getIsBanned().equals(LoginInfo.BAN)) {
            throw new ApiException(ResultEnum.USER_IS_BANNED);
        }
        //密码是否正确
        if (!passwordUtils.encode(password).equalsIgnoreCase(loginInfo.getPassword())) {
            throw new ApiException(ResultEnum.LOGIN_ERR);
        }
        //保存用户信息
        UserContext.setUserInfo(loginInfo);

        //更新登录用户的数据库信息
        loginInfo.setLastLoginTime(new Date());
        loginInfo.setErrorTimes(0);
        this.save(loginInfo);

        //查询所有的角色，找出权限去重合集，保存到session
        List<LoginInfoRole> allByLoginInfoId = loginInfoRoleMapper.selectAllByLoginInfoId(loginInfo.getId());
        List<String> collect = allByLoginInfoId.stream().map(loginInfoRole -> loginInfoRole.getRoleSn()).collect(Collectors.toList());
        if(!collect.isEmpty()){
            List<RolePermission> rolePermissions = rolePermissionMapper.selectAllByRoleSnIn(collect);
            Set<String> permissionExprs = rolePermissions.stream().map(rolePermission -> rolePermission.getPermissionExpr()).collect(Collectors.toSet());
            UserContext.setPermissions(permissionExprs);
        }else {
            UserContext.setPermissions(Collections.EMPTY_SET);
        }
    }

    /**
     * 设置新密码
     *
     * @param id
     * @param password
     */
    @Override
    public void setPassword(Long id, String password) {
        LoginInfo loginInfo = loginInfoMapper.selectByPrimaryKey(id);
        if (loginInfo == null) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST);
        }
        if (password == null || password.length() < 6 || password.length() > 16) {
            throw new ApiException(ResultEnum.PASSWORD_LENTH_INVALID);
        }
        if (!passwordUtils.isContainLetterAndNumAndSymbols(password)) {
            throw new ApiException(ResultEnum.PASSWORD_TOO_WEEK);
        }
        loginInfo.setPassword(passwordUtils.encode(password));
        this.save(loginInfo);
    }

    /**
     * 删除一个用户
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST);
        }
        LoginInfo loginInfo = loginInfoMapper.selectByPrimaryKey(id);
        if (loginInfo != null) {
            if (loginInfo.getIsAdmin()) {
                throw new ApiException(ResultEnum.AMINISTRATOR_ERR);
            }
        }
        loginInfoMapper.deleteByPrimaryKey(id);
        //todo 删除用户相关的数据
    }

    /**
     * 禁用或启用
     *
     * @param id
     * @return
     */
    @Override
    public boolean ban(Long id) {
        LoginInfo loginInfo = loginInfoMapper.selectByPrimaryKey(id);
        if (loginInfo != null) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST);
        }
        if (loginInfo.getIsAdmin()) {
            throw new ApiException(ResultEnum.AMINISTRATOR_ERR);
        }
        Boolean isBaned = !loginInfo.getIsBanned();
        loginInfo.setIsBanned(isBaned);
        loginInfo.setModifyTime(new Date());
        this.save(loginInfo);
        return isBaned;
    }

    /**
     * 新增或编辑
     *
     * @param form
     * @return
     */
    @Override
    public UserInfoVO save(UserInfoSaveForm form) {
        LoginInfo loginInfo = null;
        if (form.getId() == null) {
            String username = form.getUsername();
            loginInfo = loginInfoMapper.selectByUsername(username);
            if (loginInfo == null) {
                throw new ApiException(ResultEnum.USERNAME_ALREADY_BIND);
            }
            loginInfo = new LoginInfo();
            BeanUtils.copyProperties(form, loginInfo);
            loginInfo.setCreateTime(new Date());
            loginInfo.setModifyTime(new Date());
            loginInfo.setIsBanned(form.getIsBanned());
            loginInfo.setIsAdmin(false);
        } else {
            loginInfo = loginInfoMapper.selectByPrimaryKey(form.getId());
            if (loginInfo != null) {
                throw new ApiException(ResultEnum.USER_NOT_EXIST);
            }
            BeanUtils.copyProperties(form, loginInfo);
            loginInfo.setModifyTime(new Date());
            loginInfo.setIsBanned(form.getIsBanned());
        }
        this.save(loginInfo);
        UserInfoVO vo = new UserInfoVO();
        BeanUtils.copyProperties(loginInfo, vo);
        return vo;
    }

    /**
     * 查询用户列表
     *
     * @param form
     * @return
     */
    @Override
    public ApiResult<List<UserInfoVO>> list(UserListForm form) {
        PageHelper.startPage(form.getPage(),form.getLimit(),true);
        Page<LoginInfo> all = (Page<LoginInfo>) loginInfoMapper.selectAllByUsernameLikeAndIsAdminIsFalse(form.getUsername());
        List<UserInfoVO> collect = all.getResult().stream().map(loginInfo -> {
            UserInfoVO vo = new UserInfoVO();
            BeanUtils.copyProperties(loginInfo, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ApiResult<>().success(collect, all.getTotal());
    }
}
