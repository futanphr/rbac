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
import cn.zeemoo.rbac.repository.PermissionRepository;
import cn.zeemoo.rbac.repository.RolePermissionRepository;
import cn.zeemoo.rbac.repository.RoleRepository;
import cn.zeemoo.rbac.service.IRoleService;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import cn.zeemoo.rbac.vo.role.RoleVO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.api.internal.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    private RoleRepository roleRepository;

    @Autowired
    private RolePermissionRepository rolePermissionRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * 查出所有的角色
     *
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    /**
     * 角色权限回显
     *
     * @param form
     * @return
     */
    @Override
    public List<PermissionVO> rolePermissions(@Valid RolePermissionListForm form) {

        List<RolePermission> allByRoleSn = rolePermissionRepository.findAllByRoleSn(form.getRoleSn());
        Set<String> rolePermissions = allByRoleSn.stream().map(rolePermission -> rolePermission.getPermissionExpr()).collect(Collectors.toSet());
        final List<Permission> all = permissionRepository.findAll();
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
        List<RolePermission> rolePermissions = rolePermissionRepository.findAllByRoleSn(roleSn);
        rolePermissionRepository.deleteInBatch(rolePermissions);

        List<RolePermission> collect = exprs.stream().map(s -> new RolePermission(roleSn, s)).collect(Collectors.toList());
        rolePermissionRepository.saveAll(collect);

    }

    /**
     * 删除一条数据
     *
     * @param id
     */
    @Override
    public void delete(Long id) {
        roleRepository.deleteById(id);
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
        Pageable request = PageRequest.of(
                form.getCurrentPage(),
                form.getLimit(),
                Sort.Direction.DESC,
                "modifyTime", "createTime");
        Page<Role> all = roleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            if (StringUtils.areNotEmpty(form.getKeyword())) {
                String keyword = "%" + form.getKeyword() + "%";
                Predicate roleName = criteriaBuilder.like(root.get("roleName").as(String.class), keyword);
                Predicate roleSn = criteriaBuilder.like(root.get("roleSn").as(String.class), keyword);
                return criteriaBuilder.or(roleName, roleSn);
            }
            return null;
        }, request);
        List<RoleVO> collect = all.getContent().stream().map(role -> {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ApiResult<>().success(collect, all.getTotalElements());
    }

    @Override
    public RoleVO save(@Valid RoleSaveForm form) {
        Role role = null;
        String roleName = form.getRoleName();
        String roleSn = form.getRoleSn();
        if (form.getId() == null) {

            Optional<Role> byRoleName = roleRepository.findByRoleName(roleName);
            if (byRoleName.isPresent()) {
                throw new ApiException(ResultEnum.ROLE_NAME_EXIST);
            }
            Optional<Role> byRoleSn = roleRepository.findByRoleSn(roleSn);
            if (byRoleSn.isPresent()) {
                throw new ApiException(ResultEnum.ROLE_SN_EXIST);
            }
            role = new Role();
            BeanUtils.copyProperties(form, role);
            role.setCreateTime(new Date());
            role.setModifyTime(new Date());
        } else {
            Optional<Role> byId = roleRepository.findById(form.getId());
            if (!byId.isPresent()) {
                throw new ApiException(ResultEnum.ROLE_NOT_EXIST);
            }

            Optional<Role> byRoleName = roleRepository.findByRoleName(roleName);
            if (byRoleName.isPresent() && !byRoleName.get().getId().equals(byId.get().getId())) {
                throw new ApiException(ResultEnum.ROLE_NAME_EXIST);
            }

            Optional<Role> byRoleSn = roleRepository.findByRoleSn(roleSn);
            if (byRoleSn.isPresent() && !byRoleSn.get().getId().equals(byId.get().getId())) {
                throw new ApiException(ResultEnum.ROLE_SN_EXIST);
            }

            role = byId.get();
            BeanUtils.copyProperties(form, role);
            role.setModifyTime(new Date());
        }
        Role save = roleRepository.save(role);
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(save, vo);
        return vo;
    }
}
