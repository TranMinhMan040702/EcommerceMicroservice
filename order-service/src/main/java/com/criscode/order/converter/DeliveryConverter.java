package com.criscode.order.converter;

import com.criscode.order.dto.DeliveryDto;
import com.criscode.order.entity.Delivery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryConverter {

    public DeliveryDto mapToDto(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        BeanUtils.copyProperties(delivery, deliveryDto);
        return deliveryDto;
    }

    public List<DeliveryDto> mapToDto(List<Delivery> deliveries) {
        return deliveries.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Delivery mapToEntity(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        BeanUtils.copyProperties(deliveryDto, delivery);
        return delivery;
    }
}
