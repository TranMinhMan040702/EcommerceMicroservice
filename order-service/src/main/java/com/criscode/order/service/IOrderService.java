package com.criscode.order.service;

import com.criscode.order.dto.OrderDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    List<OrderDto> findAllOrdersByStatusWithPaginationAndSort(
            String status, Integer page, Integer limit, String sortBy, String search);

    List<OrderDto> findAllOrdersByUser(Integer userId);

    OrderDto findOrderById(Integer orderId);

    List<OrderDto> findOrderByStatus(Integer userId, String status);

    void createOrder(OrderDto orderDto);

    List<OrderDto> updateStatus(Integer orderId, String status);

    List<OrderDto> deleteOrder(Integer orderId);

    String getStatusOrder(Integer orderId);

    long totalOrder();

    Double totalSales();

    List<Double> statisticRevenue(int year);
}
