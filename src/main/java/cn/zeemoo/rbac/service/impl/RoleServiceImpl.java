package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.Permission;
import cn.zeemoo.rbac.domain.Role;
import cn.zeemoo.rbac.domain.RolePermission;
import cn.zeemoo.rbac.enums.ResultEnum;
import cn.zeemoo.rbac.exception.ApiException;
import cn.zeemoo.rbac.form.admin.role.RoleListForm;
import cn.zeemoo.rbac.form.admin.role.RoleSaveForm;
import cn.zeemoo.rbac.form.admin.role.permisson.RolePermissionForm;
import cn.zeemoo.rbac.form.admin.role.permisson.RolePermissionListForm;
import cn.zeemoo.rbac.mapper.PermissionMapper;
import cn.zeemoo.rbac.mapper.RoleMapper;
import cn.zeemoo.rbac.mapper.RolePermissionMapper;
import cn.zeemoo.rbac.service.IRoleService;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import cn.zeemoo.rbac.vo.role.RoleVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色业务接口实现类
 *
 * @author zeemoo
 * @date 2018/7/9 21:51
 */
@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 查出所有的角色
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    /**
     * 角色权限回显
     *
     * @param form
     * @return
     */
    @Override
    public List<PermissionVO> rolePermissions(@Valid RolePermissionListForm form) {

        List<RolePermission> allByRoleSn = rolePermissionMapper.selectAllByRoleSn(form.getRoleSn());
        Set<String> rolePermissions = allByRoleSn.stream().map(rolePermission -> rolePermission.getPermissionExpr()).collect(Collectors.toSet());
        final List<Permission> all = permissionMapper.selectAll();
        return all.stream().map(permission -> {
            PermissionVO permissionVO = new PermissionVO();
            BeanUtils.copyProperties(permission,permissionVO);
            permissionVO.setRolePermissions(rolePermissions);
            return permissionVO;
        }).collect(Collectors.toList());
    }

    /**
     * 角色分配权限
     *
     * @param form
     */
    @Override
    public void assignPermissions(@Valid RolePermissionForm form) {
        String json = form.getExprs();
        Set<String> exprs = JSON.parseObject(json, new TypeReference<Set<String>>() {
        });
        //查出该角色所拥有的所有权限
        String roleSn = form.getRoleSn();
        List<RolePermission> rolePermissions = rolePermissionMapper.selectAllByRoleSn(roleSn);
        if(!rolePermissions.isEmpty()){
            rolePermissionMapper.deleteInBatch(rolePermissions);
        }
        List<RolePermission> collect = exprs.stream().map(s -> new RolePermission(roleSn, s)).collect(Collectors.toList());
        rolePermissionMapper.insertAll(collect);

    }

    /**
     * 删除一条数据
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
        //todo 删除对应权限关联和角色关联
    }

    /**
     * 返回角色列表
     *
     * @param form
     * @return
     */
    @Override
    public ApiResult<List<RoleVO>> list(@Valid RoleListForm form) {
        PageHelper.startPage(form.getPage(),form.getLimit(),true);
        Page<Role> all = (Page<Role>) roleMapper.selectAllByRoleNameLikeOrRoleSnLike(form.getKeyword());
        List<RoleVO> collect = all.getResult().stream().map(role -> {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ApiResult<>().success(collect, all.getTotal());
    }

    @Override
    public RoleVO save(@Valid RoleSaveForm form) {
        Role role = null;
        String roleName = form.getRoleName();
        String roleSn = form.getRoleSn();
        if (form.getId() == null) {
            role = roleMapper.selectByRoleName(roleName);
            if (role!=null) {
                throw new ApiException(ResultEnum.ROLE_NAME_EXIST);
            }
            Role byRoleSn = roleMapper.selectByRoleSn(roleSn);
            if (byRoleSn!=null) {
                throw new ApiException(ResultEnum.ROLE_SN_EXIST);
            }
            role = new Role();
            BeanUtils.copyProperties(form, role);
            role.setCreateTime(new Date());
            role.setModifyTime(new Date());
            roleMapper.insert(role);
        } else {
            Role byId = roleMapper.selectByPrimaryKey(form.getId());
            if (byId==null) {
                throw new ApiException(ResultEnum.ROLE_NOT_EXIST);
            }

            Role byRoleName = roleMapper.selectByRoleName(roleName);
            if (byRoleName!=null && !byRoleName.getId().equals(byId.getId())) {
                throw new ApiException(ResultEnum.ROLE_NAME_EXIST);
            }

            Role byRoleSn = roleMapper.selectByRoleSn(roleSn);
            if (byRoleSn!=null && !byRoleSn.getId().equals(byId.getId())) {
                throw new ApiException(ResultEnum.ROLE_SN_EXIST);
            }

            role = byId;
            BeanUtils.copyProperties(form, role);
            role.setModifyTime(new Date());
            roleMapper.updateByPrimaryKey(role);
        }
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);
        return vo;
    }
}
