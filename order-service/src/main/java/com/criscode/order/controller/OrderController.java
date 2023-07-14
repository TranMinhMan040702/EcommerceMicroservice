package com.criscode.order.controller;

import com.criscode.order.constants.ApplicationConstants;
import com.criscode.order.dto.OrderDto;
import com.criscode.order.service.IOrderService;
import com.criscode.order.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-service/order")
@CrossOrigin({ "https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/" })
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/")
    public void createOrder(@RequestBody OrderDto dto) {
        orderService.createOrder(dto);
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<?> getAllOrderByStatus(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(orderService.findAllOrdersByStatusWithPaginationAndSort(
                status, page, limit, sortBy, search));
    }

    @GetMapping("/admin/orders/top-5-latest")
    public ResponseEntity<?> getTop5OrderLatest(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy) {
        return ResponseEntity.ok(orderService.findAllOrdersByStatusWithPaginationAndSort(
                null, page, limit = 5, sortBy, null));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getOrderByStatus(
            @RequestParam("userId") Integer userId, @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.findOrderByStatus(userId, status));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrderByUser(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(orderService.findAllOrdersByUser(userId));
    }

    @PutMapping("/")
    public ResponseEntity<?> updateStatus(
            @RequestParam("orderId") Integer orderId, @RequestParam("status") String status) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(orderService.deleteOrder(id));
    }

    @GetMapping("/get-status/{orderId}")
    public String getStatusOrder(@PathVariable("orderId") Integer orderId) {
        return orderService.getStatusOrder(orderId);
    }

}
