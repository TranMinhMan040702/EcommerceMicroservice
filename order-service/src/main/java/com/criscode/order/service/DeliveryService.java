package com.criscode.order.service;

import com.criscode.exceptionutils.NotFoundException;
import com.criscode.order.converter.DeliveryConverter;
import com.criscode.order.dto.DeliveryDto;
import com.criscode.order.entity.Delivery;
import com.criscode.order.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryConverter deliveryConverter;

    public DeliveryDto save(DeliveryDto deliveryDto) {
        return deliveryConverter.mapToDto(deliveryRepository.save(deliveryConverter.mapToEntity(deliveryDto)));
    }

    public List<DeliveryDto> findAll() {
        return deliveryConverter.mapToDto(deliveryRepository.findAll());
    }

    // todo: delete one, all
    // todo: delete soft
}
