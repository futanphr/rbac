package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.Permission;
import cn.zeemoo.rbac.form.admin.permission.PermissionListForm;
import cn.zeemoo.rbac.mapper.PermissionMapper;
import cn.zeemoo.rbac.service.IPermissionService;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限业务接口实现类
 *
 * @author zeemoo
 * @date 2018/7/10 23:51
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 查询权限列表
     *
     * @param form
     * @return
     */
    @Override
    public ApiResult<List<PermissionVO>> list(PermissionListForm form) {
        PageHelper.startPage(form.getPage(),form.getLimit(),true);
        Page<Permission> all = (Page<Permission>) permissionMapper.selectAllByName(form.getKeyword());
        List<PermissionVO> collect = all.getResult().stream().map(permission -> {
            PermissionVO vo = new PermissionVO();
            BeanUtils.copyProperties(permission, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ApiResult<>().success(collect,all.getTotal());
    }

    /**
     * 查出所有的权限
     *
     * @return
     */
    @Override
    public List<String> findAllExpr() {
        return permissionMapper.selectAll().stream()
                .map(permission -> permission.getExpr())
                .collect(Collectors.toList());
    }

    /**
     * 去重并保存
     *
     * @param permissions
     */
    @Override
    public void saveAll(List<Permission> permissions) {
        List<String> allExpr = findAllExpr();
        List<Permission> collect = permissions.stream()
                .filter(permission -> !allExpr.contains(permission.getExpr()))
                .collect(Collectors.toList());
        if(!collect.isEmpty()){
            permissionMapper.insertAll(collect);
        }
    }
}
