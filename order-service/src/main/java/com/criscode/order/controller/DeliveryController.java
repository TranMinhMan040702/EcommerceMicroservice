package com.criscode.order.controller;

import com.criscode.exceptionutils.NotFoundException;
import com.criscode.order.dto.DeliveryDto;
import com.criscode.order.repository.DeliveryRepository;
import com.criscode.order.service.IDeliveryService;
import com.criscode.order.service.impl.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/order-service")
@CrossOrigin({ "https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/" })
@RequiredArgsConstructor
public class DeliveryController {

    private final IDeliveryService deliveryService;
    private final DeliveryRepository deliveryRepository;

    @GetMapping("/deliveries/get-all")
    public ResponseEntity<?> findAllDelivery() {
        return ResponseEntity.ok(deliveryService.findAll());
    }

    @PostMapping("/admin/deliveries")
    public ResponseEntity<?> createDelivery(
            @RequestBody @Valid DeliveryDto deliveryDto,
            @RequestHeader("X-Roles") String roles){
        if (roles == null || !roles.contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
        return ResponseEntity.ok(deliveryService.save(deliveryDto));
    }

    @PutMapping("/admin/deliveries")
    public ResponseEntity<?> updateDelivery(
            @RequestBody @Valid DeliveryDto deliveryDto,
            @RequestHeader("X-Roles") String roles){
        if (roles == null || !roles.contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
        deliveryRepository.findById(deliveryDto.getId()).orElseThrow(
                () -> new NotFoundException("Delivery not exist with id: " + deliveryDto.getId())
        );
        return ResponseEntity.ok(deliveryService.save(deliveryDto));
    }

}
