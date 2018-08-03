package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.LoginInfo;
import cn.zeemoo.rbac.domain.LoginInfoRole;
import cn.zeemoo.rbac.domain.Role;
import cn.zeemoo.rbac.domain.RolePermission;
import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.form.admin.role.UserRoleListForm;
import cn.zeemoo.rbac.form.admin.user.ResetPasswordForm;
import cn.zeemoo.rbac.form.admin.user.UpdateUserInfoForm;
import cn.zeemoo.rbac.form.admin.user.UserInfoSaveForm;
import cn.zeemoo.rbac.form.admin.user.UserListForm;
import cn.zeemoo.rbac.repository.LoginInfoRepository;
import cn.zeemoo.rbac.repository.LoginInfoRoleRepository;
import cn.zeemoo.rbac.repository.RolePermissionRepository;
import cn.zeemoo.rbac.service.ILoginInfoService;
import cn.zeemoo.rbac.service.IRoleService;
import cn.zeemoo.rbac.utils.PasswordUtils;
import cn.zeemoo.rbac.utils.UserContext;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.user.UserInfoVO;
import cn.zeemoo.rbac.form.admin.role.UserRoleForm;
import cn.zeemoo.rbac.vo.user.role.UserRolesVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private LoginInfoRepository repository;

    @Autowired
    private PasswordUtils passwordUtils;

    @Autowired
    private LoginInfoRoleRepository loginInfoRoleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private IRoleService roleService;

    /**
     * 修改密码
     *
     * @param form
     */
    @Override
    public void resetPassword(@Valid ResetPasswordForm form) {
        Long id = UserContext.getUserInfo().getId();
        Optional<LoginInfo> byId = repository.findById(id);
        LoginInfo loginInfo = byId.get();
        if (!passwordUtils.isContainLetterAndNumAndSymbols(form.getNewPassword())) {
            throw new ApiException(ResultEnum.PASSWORD_TOO_WEEK);
        }
        if (!passwordUtils.encode(form.getOldPassword()).equalsIgnoreCase(loginInfo.getPassword())) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST.PASSWORD_ERR);
        }
        loginInfo.setPassword(passwordUtils.encode(form.getNewPassword()));
        repository.save(loginInfo);
    }

    /**
     * 修改用户信息
     *
     * @param form
     */
    @Override
    public void updateUserInfo(@Valid UpdateUserInfoForm form) {
        Optional<LoginInfo> byId = repository.findById(UserContext.getUserInfo().getId());
        LoginInfo loginInfo = byId.get();
        loginInfo.setPhone(form.getPhone());
        repository.save(loginInfo);
        UserContext.setUserInfo(loginInfo);
    }

    @Override
    public List<UserRolesVO> userRoles(@Valid UserRoleListForm form) {
        List<LoginInfoRole> allByLoginInfoId = loginInfoRoleRepository.findAllByLoginInfoId(form.getUserId());
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
        List<LoginInfoRole> allByLoginInfoId = loginInfoRoleRepository.findAllByLoginInfoId(userId);
        loginInfoRoleRepository.deleteInBatch(allByLoginInfoId);
        List<LoginInfoRole> collect = roleSns.stream().map(s -> new LoginInfoRole(userId, s)).collect(Collectors.toList());
        loginInfoRoleRepository.saveAll(collect);

    }

    /**
     * 增加用户登录错误次数，防止暴力破解
     *
     * @param username
     */
    @Override
    public void addErrTimes(String username) {
        Optional<LoginInfo> byUsername = repository.findByUsername(username);
        if (byUsername.isPresent()) {
            LoginInfo loginInfo = byUsername.get();
            if(loginInfo.getErrorTimes()>3){
                loginInfo.setIsBanned(LoginInfo.BAN);
            }
            loginInfo.setErrorTimes(loginInfo.getErrorTimes() + 1);
            repository.save(loginInfo);
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
        Optional<LoginInfo> byUsernameAndPassword = repository.findByUsername(username);
        //用户名用户是否存在
        if (!byUsernameAndPassword.isPresent()) {
            throw new ApiException(ResultEnum.LOGIN_ERR);
        }
        LoginInfo loginInfo = byUsernameAndPassword.get();
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
        repository.save(loginInfo);

        //查询所有的角色，找出权限去重合集，保存到session
        List<LoginInfoRole> allByLoginInfoId = loginInfoRoleRepository.findAllByLoginInfoId(loginInfo.getId());
        List<String> collect = allByLoginInfoId.stream().map(loginInfoRole -> loginInfoRole.getRoleSn()).collect(Collectors.toList());
        List<RolePermission> rolePermissions = rolePermissionRepository.findAll((root, query, criteriaBuilder) -> {
            if (!collect.isEmpty()) {
                CriteriaBuilder.In<String> roleSn = criteriaBuilder.in(root.get("roleSn").as(String.class));
                collect.forEach(s->{
                    roleSn.value(s);
                });
                return roleSn;
            }
            return null;
        });
        Set<String> permissionExprs = rolePermissions.stream().map(rolePermission -> rolePermission.getPermissionExpr()).collect(Collectors.toSet());
        UserContext.setPermissions(permissionExprs);
    }

    /**
     * 设置新密码
     *
     * @param id
     * @param password
     */
    @Override
    public void setPassword(Long id, String password) {
        Optional<LoginInfo> byId = repository.findById(id);
        if (!byId.isPresent()) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST);
        }
        if (password == null || password.length() < 6 || password.length() > 16) {
            throw new ApiException(ResultEnum.PASSWORD_LENTH_INVALID);
        }
        if (!passwordUtils.isContainLetterAndNumAndSymbols(password)) {
            throw new ApiException(ResultEnum.PASSWORD_TOO_WEEK);
        }
        LoginInfo loginInfo = byId.get();
        loginInfo.setPassword(passwordUtils.encode(password));
        repository.save(loginInfo);
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
        Optional<LoginInfo> byId = repository.findById(id);
        if (byId.isPresent()) {
            LoginInfo loginInfo = byId.get();
            if (loginInfo.getIsAdmin()) {
                throw new ApiException(ResultEnum.AMINISTRATOR_ERR);
            }
        }
        repository.deleteById(id);
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
        Optional<LoginInfo> byId = repository.findById(id);
        if (!byId.isPresent()) {
            throw new ApiException(ResultEnum.USER_NOT_EXIST);
        }
        LoginInfo loginInfo = byId.get();
        if (loginInfo.getIsAdmin()) {
            throw new ApiException(ResultEnum.AMINISTRATOR_ERR);
        }
        Boolean isBaned = !loginInfo.getIsBanned();
        loginInfo.setIsBanned(isBaned);
        loginInfo.setModifyTime(new Date());
        repository.save(loginInfo);
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
            Optional<LoginInfo> byUsername = repository.findByUsername(username);
            if (byUsername.isPresent()) {
                throw new ApiException(ResultEnum.USERNAME_ALREADY_BIND);
            }
            loginInfo = new LoginInfo();
            BeanUtils.copyProperties(form, loginInfo);
            loginInfo.setCreateTime(new Date());
            loginInfo.setModifyTime(new Date());
            loginInfo.setIsBanned(form.getIsBanned());
            loginInfo.setIsAdmin(false);
        } else {
            Optional<LoginInfo> byId = repository.findById(form.getId());
            if (!byId.isPresent()) {
                throw new ApiException(ResultEnum.USER_NOT_EXIST);
            }
            loginInfo = byId.get();
            BeanUtils.copyProperties(form, loginInfo);
            loginInfo.setModifyTime(new Date());
            loginInfo.setIsBanned(form.getIsBanned());
        }
        repository.save(loginInfo);
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
        PageRequest request = PageRequest.of(form.getCurrentPage(), form.getLimit(), Sort.Direction.DESC, "modifyTime", "createTime");
        Page<LoginInfo> all = repository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            Predicate isAdmin = criteriaBuilder.isFalse(root.get("isAdmin").as(Boolean.class));
            if (!StringUtils.isEmpty(form.getUsername())) {
                Predicate username = criteriaBuilder.like(root.get("username").as(String.class), "%".concat(form.getUsername()).concat("%"));
                isAdmin = criteriaBuilder.and(username, isAdmin);
            }
            return isAdmin;
        }, request);
        List<UserInfoVO> collect = all.getContent().stream().map(loginInfo -> {
            UserInfoVO vo = new UserInfoVO();
            BeanUtils.copyProperties(loginInfo, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ApiResult<>().success(collect, all.getTotalElements());
    }
}
