package com.criscode.order.converter;

import com.criscode.order.dto.OrderDto;
import com.criscode.order.entity.Delivery;
import com.criscode.order.entity.Order;
import com.criscode.order.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final DeliveryConverter deliveryConverter;
    private final OrderItemConverter orderItemConverter;

    public Order mapToEntity(OrderDto orderDto) {
        Order order = new Order();
        BeanUtils.copyProperties(orderDto, order);
        order.setDelivery(deliveryConverter.mapToEntity(orderDto.getDeliveryDto()));
        order.setOrderItems(orderItemConverter.mapToEntity(orderDto.getOrderItemDtos()));
        return order;
    }

    public OrderDto mapToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        BeanUtils.copyProperties(order, orderDto);
        orderDto.setDeliveryDto(deliveryConverter.mapToDto(order.getDelivery()));
        orderDto.setOrderItemDtos(orderItemConverter.mapToDto(order.getOrderItems()));
        return orderDto;
    }
}
