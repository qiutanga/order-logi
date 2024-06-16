package com.example.orderlogistics.pojo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//订单
public class Order implements Serializable {
    private String id; //订单编号
    private String status;//订单状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;//下单时间
    private String shipper;//发货人
    private String shipperAddress;//发货人地址
    private String shipperPhone;//发货人电话
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shipTime;//发货时间
    private String receiver;//收货人
    private String receiverAddress;//收货人地址
    private String receiverPhone;//收货人电话
    private List<Logistics> logistics;//物流信息
    private Object field1;
    private  Object field2;


    public Object getField1() {
        return field1;
    }

    public void setField1(Object field1) {
        this.field1 = field1;
    }

    public Object getField2() {
        return field2;
    }

    public void setField2(Object field2) {
        this.field2 = field2;
    }
}
