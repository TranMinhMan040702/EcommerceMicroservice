package com.criscode.order.controller;

import com.criscode.order.constants.ApplicationConstants;
import com.criscode.order.dto.OrderDto;
import com.criscode.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDto dto) {
        return ResponseEntity.ok(orderService.createOrder(dto));
    }

    @GetMapping("admin/order")
    public ResponseEntity<?> getAllOrderByStatus(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(orderService.findAllOrdersByStatusWithPaginationAndSort(
                status, page, limit, sortBy, search));
    }

    @GetMapping("admin/order/top-5-latest")
    public ResponseEntity<?> getTop5OrderLatest(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy) {
        return ResponseEntity.ok(orderService.findAllOrdersByStatusWithPaginationAndSort(
                null, page, limit = 5, sortBy, null));

    }

    @GetMapping("/order/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/order/user")
    public ResponseEntity<?> getOrderByStatus(
            @RequestParam("userId") Integer userId, @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.findOrderByStatus(userId, status));
    }

    @GetMapping("/order/user/{userId}")
    public ResponseEntity<?> getOrderByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(orderService.findAllOrdersByUser(userId));
    }

    @PutMapping("/order")
    public ResponseEntity<?> updateStatus(
            @RequestParam("orderId") Integer orderId, @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

}
