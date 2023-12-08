package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public List<CategoryEntity> listWithTree() {
        List<CategoryEntity> list = this.list(null);
        Map<Long, List<CategoryEntity>> groupedByPid = list.stream()
                .collect(Collectors.groupingBy(CategoryEntity::getParentCid));
        return buildTreeRecursively(groupedByPid, 0L);
    }

    @Override
    public void removeMenuByIds(List<Long> cIds) {
        baseMapper.deleteBatchIds(cIds);
    }

    /**
     * 递归构建树形结构
     *
     * @param groupedByPid 分组后的分类列表
     * @param parentId     父分类id
     * @return 树形结构的分类列表
     */
    private static List<CategoryEntity> buildTreeRecursively(Map<Long, List<CategoryEntity>> groupedByPid, Long parentId) {
        List<CategoryEntity> children = groupedByPid.getOrDefault(parentId, Collections.emptyList());

        if (!children.isEmpty()) {
            children.sort(Comparator.comparingLong(o -> o.getSort() != null ? o.getSort() : o.getCatId()));
            children.forEach(child -> {
                List<CategoryEntity> childTree = buildTreeRecursively(groupedByPid, child.getCatId());
                if (!childTree.isEmpty()) {
                    child.setChildren(childTree);
                }
            });
        }
        return children;
    }
}