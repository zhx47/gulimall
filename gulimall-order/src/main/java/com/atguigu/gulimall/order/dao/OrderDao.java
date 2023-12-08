package com.atguigu.gulimall.order.dao;

import com.atguigu.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author zhx47
 * @email 2758887317@qq.com
 * @date 2023-07-13 00:40:44
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
