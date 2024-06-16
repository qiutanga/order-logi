package com.example.orderlogistics.service;

import cn.hutool.core.util.IdUtil;
import com.example.orderlogistics.pojo.Logistics;
import com.example.orderlogistics.pojo.Order;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.internal.operation.OrderBy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class OrderService {
    @Resource
    private MongoTemplate mongoTemplate;


    //添加订单到mongodb
    public void addOrder(Order order) {
        //生成订单编号
        order.setId(IdUtil.getSnowflake(1,1).nextIdStr());
        //设置订单状态
        order.setStatus("已下单");
        //设置下单时间
        order.setOrderTime(new Date());
        //设置发货时间
        order.setShipTime(new Date());
        //添加订单到mongodb
        mongoTemplate.insert(order,"order");
    }


    public void updateOrderAndAddLogistics(Logistics logistics) {
        //获取操作名称
        String status = logistics.getOperation();
        //设置操作时间
        logistics.setOperationTime(new Date());
        //初始化Query对象，根据订单查询
        Query query = new Query(Criteria.where("_id").is(logistics.getOrderId()));
        //初始化Update对象
        Update update =new Update();
        //更新订单状态
        update.set("status",status);
        //追加物流信息
        update.push("logistics",logistics);
        //更新订单信息
        mongoTemplate.upsert(query,update,Order.class,"order");
    }

    //通过订单编号进行查询
    public Order selectOrderByID(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        return mongoTemplate.findOne(query,Order.class,"order");

    }
    //查询所有订单
    public Map<String,Object> selectOrderList() {
        List<Order> list = mongoTemplate.findAll(Order.class,"order");
        Map<String,Object> result = new HashMap<>();
        if (list ==null || list.isEmpty()) {
            result.put("code","400");
            result.put("message","没有相关订单信息");
        } else {
            result.put("code","0");
            result.put("count","list.size");
            result.put("data",list);
        }

        return result;

    }
    //删除订单
    public boolean deleteOrderById(String id) {
        Query query = new Query(Criteria.where("_id").is(id));
        DeleteResult result = mongoTemplate.remove(query,Order.class,"order");

        return result.getDeletedCount() > 0 ? true : false;
    }

    public void updateOrder(Order order) {
        Query query = new Query(Criteria.where("_id").is(order.getId()));
        Update update = new Update();
        // 根据需要更新订单的字段
        update.set("shipper", order.getShipper());
        update.set("shipperAddress", order.getShipperAddress());
        update.set("shipperPhone", order.getShipperPhone());
        update.set("receiver", order.getReceiver());
        update.set("receiverAddress", order.getReceiverAddress());
        update.set("receiverPhone", order.getReceiverPhone());

        // 添加其他需要更新的字段
        mongoTemplate.updateFirst(query, update, Order.class, "order");
    }

    //修改
  
}
