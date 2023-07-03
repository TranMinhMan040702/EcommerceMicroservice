package com.criscode.order.controller;

import com.criscode.exceptionutils.NotFoundException;
import com.criscode.order.dto.DeliveryDto;
import com.criscode.order.repository.DeliveryRepository;
import com.criscode.order.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryRepository deliveryRepository;

    @GetMapping("deliveries")
    public ResponseEntity<?> findAllDelivery() {
        return ResponseEntity.ok(deliveryService.findAll());
    }

    @PostMapping("/admin/deliveries")
    public ResponseEntity<?> createDelivery(@RequestBody @Valid DeliveryDto deliveryDto){
        return ResponseEntity.ok(deliveryService.save(deliveryDto));
    }

    @PutMapping("/admin/deliveries")
    public ResponseEntity<?> updateDelivery(@RequestBody @Valid DeliveryDto deliveryDto){
        deliveryRepository.findById(deliveryDto.getId()).orElseThrow(
                () -> new NotFoundException("Delivery not exist with id: " + deliveryDto.getId())
        );
        return ResponseEntity.ok(deliveryService.save(deliveryDto));
    }

}