package com.atguigu.gulimall.product.service;

import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 商品三级分类
 *
 * @author zhx47
 * @email 2758887317@qq.com
 * @date 2023-07-12 00:55:40
 */
public interface CategoryService extends IService<CategoryEntity> {

    /**
     * 获取树形结构的分类列表
     *
     * @return 树形结构的分类列表
     */
    List<CategoryEntity> listWithTree();

    /**
     * 删除分类
     *
     * @param cIds 分类id列表
     */
    void removeMenuByIds(List<Long> cIds);
}

