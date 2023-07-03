package com.criscode.clients.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("order")
public interface OrderClient {
    @GetMapping("/api/v1/order/get-status/{orderId}")
    String getStatusOrder(@PathVariable("orderId") Integer orderId);
}
