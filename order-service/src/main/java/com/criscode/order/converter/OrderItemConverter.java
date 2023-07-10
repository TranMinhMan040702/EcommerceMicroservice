package com.criscode.order.converter;

import com.criscode.clients.order.dto.OrderItemDto;
import com.criscode.clients.product.ProductClient;
import com.criscode.order.entity.Order;
import com.criscode.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    private final ProductClient productClient;

    public OrderItem mapToEntity(OrderItemDto orderItemDto, Order order) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemDto, orderItem);
        orderItem.setOrder(order);
        return orderItem;
    }

    public List<OrderItem> mapToEntity(List<OrderItemDto> orderItemDtos, Order order) {
        return orderItemDtos.stream()
                .map(orderItemDto -> mapToEntity(orderItemDto, order))
                .collect(Collectors.toList());
    }

    public OrderItemDto mapToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        BeanUtils.copyProperties(orderItem, orderItemDto);
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        orderItemDto.setProductDto(productClient.getProductById(orderItemDto.getProductId()));
        // todo: handle Review
        return orderItemDto;
    }

    public List<OrderItemDto> mapToDto(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::mapToDto).collect(Collectors.toList());
    }

}
