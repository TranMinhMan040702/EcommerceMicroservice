package com.criscode.order.converter;

import com.criscode.order.dto.DeliveryDto;
import com.criscode.order.entity.Delivery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeliveryConverter {

    public DeliveryDto map(Delivery delivery) {
        DeliveryDto deliveryDto = new DeliveryDto();
        BeanUtils.copyProperties(delivery, deliveryDto);
        return deliveryDto;
    }

    public List<DeliveryDto> map(List<Delivery> deliveries) {
        return deliveries.stream().map(this::map).collect(Collectors.toList());
    }

    public Delivery map(DeliveryDto deliveryDto) {
        Delivery delivery = new Delivery();
        BeanUtils.copyProperties(deliveryDto, delivery);
        return delivery;
    }
}
