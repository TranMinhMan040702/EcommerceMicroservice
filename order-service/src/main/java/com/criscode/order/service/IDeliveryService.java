package com.criscode.order.service;

import com.criscode.order.dto.DeliveryDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IDeliveryService {
    DeliveryDto save(DeliveryDto deliveryDto);

    List<DeliveryDto> findAll();
}
