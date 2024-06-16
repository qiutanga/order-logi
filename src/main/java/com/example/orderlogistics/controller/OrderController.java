package com.example.orderlogistics.controller;

import com.example.orderlogistics.pojo.Logistics;
import com.example.orderlogistics.pojo.Order;
import com.example.orderlogistics.service.OrderService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("order")

public class OrderController {
    @Resource
    private OrderService orderService;
    //添加订单到mongodb
    @PostMapping("add")
    public String addOrder(Order order){
        orderService.addOrder(order);
        return "订单添加成功";
    }

//更新订单信息
    @PostMapping("update")
    public String updateOrderAndAddLogistics(Logistics logistics){
        orderService.updateOrderAndAddLogistics(logistics);
        return "物流添加成功";
    }

    @GetMapping("{id}")
    public Order selectOrderByID(@PathVariable String id) {

        return orderService.selectOrderByID(id);
    }

    @GetMapping ("list")
    public Map<String, Object> selectOrderList(){

        return orderService.selectOrderList();
    }
//根据订单编号删除订单
    @PostMapping("delete")
    public String deleteById(String id){
        orderService.deleteOrderById(id);
        return "删除成功";
    }
    //修改
   @PostMapping("save")
    public String updateOrder(@RequestBody Order order){
      orderService.updateOrder(order);
      return "订单修改成功";
   }
}