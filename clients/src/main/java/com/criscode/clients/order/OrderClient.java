package com.criscode.clients.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("order")
public interface OrderClient {
    @GetMapping("/api/v1/order-service/order/get-status/{orderId}")
    String getStatusOrder(@PathVariable("orderId") Integer orderId);

    @GetMapping("/api/v1/order-service/order/statistic/revenue")
    List<Double> getStatisticRevenue(@RequestParam("year") int year);

    @GetMapping("/api/v1/order-service/order/statistic/get-total")
    long getTotal();

    @GetMapping("/api/v1/order-service/order/statistic/get-totalsale")
    Double getTotalSale();
}
