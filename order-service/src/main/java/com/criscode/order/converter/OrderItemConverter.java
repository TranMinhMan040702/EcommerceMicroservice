package com.criscode.order.converter;

import com.criscode.clients.order.dto.OrderItemDto;
import com.criscode.order.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemConverter {

    public OrderItem mapToEntity(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        BeanUtils.copyProperties(orderItemDto, orderItem);
        return orderItem;
    }

    public List<OrderItem> mapToEntity(List<OrderItemDto> orderItemDtos) {
        return orderItemDtos.stream().map(this::mapToEntity).collect(Collectors.toList());
    }

    public OrderItemDto mapToDto(OrderItem orderItem) {
        OrderItemDto orderItemDto = new OrderItemDto();
        BeanUtils.copyProperties(orderItem, orderItemDto);
        orderItemDto.setOrderId(orderItem.getOrder().getId());
        // todo: handle Review
        return orderItemDto;
    }

    public List<OrderItemDto> mapToDto(List<OrderItem> orderItems) {
        return orderItems.stream().map(this::mapToDto).collect(Collectors.toList());
    }

}
