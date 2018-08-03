package cn.zeemoo.rbac.service.impl;

import cn.zeemoo.rbac.domain.Permission;
import cn.zeemoo.rbac.form.admin.permission.PermissionListForm;
import cn.zeemoo.rbac.repository.PermissionRepository;
import cn.zeemoo.rbac.service.IPermissionService;
import cn.zeemoo.rbac.vo.ApiResult;
import cn.zeemoo.rbac.vo.permission.PermissionVO;
import com.alipay.api.internal.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
    private PermissionRepository repository;

    /**
     * 查询权限列表
     *
     * @param form
     * @return
     */
    @Override
    public ApiResult<List<PermissionVO>> list(PermissionListForm form) {
        Pageable request = PageRequest.of(form.getCurrentPage(),form.getLimit(), Sort.Direction.ASC,"expr","name");
        Page<Permission> all = repository.findAll((Root<Permission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            if (StringUtils.areNotEmpty(form.getKeyword())) {
                return criteriaBuilder.like(root.get("name").as(String.class), "%" + form.getKeyword() + "%");
            }
            return null;
        }, request);
        List<PermissionVO> collect = all.getContent().stream().map(permission -> {
            PermissionVO vo = new PermissionVO();
            BeanUtils.copyProperties(permission, vo);
            return vo;
        }).collect(Collectors.toList());
        return new ApiResult<>().success(collect,all.getTotalElements());
    }

    /**
     * 查出所有的权限
     *
     * @return
     */
    @Override
    public List<String> findAllExpr() {
        return repository.findAll().stream()
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
        repository.saveAll(collect);
    }
}
