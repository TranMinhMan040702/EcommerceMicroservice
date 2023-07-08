package com.criscode.order.service.impl;

import com.criscode.order.converter.DeliveryConverter;
import com.criscode.order.dto.DeliveryDto;
import com.criscode.order.repository.DeliveryRepository;
import com.criscode.order.service.IDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DeliveryService implements IDeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryConverter deliveryConverter;

    @Override
    public DeliveryDto save(DeliveryDto deliveryDto) {
        return deliveryConverter.mapToDto(deliveryRepository.save(deliveryConverter.mapToEntity(deliveryDto)));
    }

    @Override
    public List<DeliveryDto> findAll() {
        return deliveryConverter.mapToDto(deliveryRepository.findAll());
    }

    // todo: delete one, all
    // todo: delete soft
}
