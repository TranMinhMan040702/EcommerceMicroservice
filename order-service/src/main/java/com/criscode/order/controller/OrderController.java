package com.criscode.order.controller;

import com.criscode.order.constants.ApplicationConstants;
import com.criscode.order.dto.OrderDto;
import com.criscode.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-service/order")
@CrossOrigin({"https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/"})
@RequiredArgsConstructor
public class OrderController {

    private final IOrderService orderService;

    @PostMapping
    public void createOrder(@RequestBody OrderDto dto) {
        orderService.createOrder(dto);
    }

    @GetMapping("/admin/orders")
    public ResponseEntity<?> getAllOrderByStatus(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy,
            @RequestParam(required = false) String search,
            @RequestHeader("X-Roles") String roles) {
        if (roles == null || !roles.contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
        return ResponseEntity.ok(orderService.findAllOrdersByStatusWithPaginationAndSort(
                status, page, limit, sortBy, search));
    }

    @GetMapping("/admin/orders/top-5-latest")
    public ResponseEntity<?> getTop5OrderLatest(
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SIZE_PAGE, required = false) Integer limit,
            @RequestParam(defaultValue = ApplicationConstants.DEFAULT_LIMIT_SORT_BY, required = false) String sortBy,
            @RequestHeader("X-Roles") String roles) {
        if (roles == null || !roles.contains("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied.");
        }
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

    @PutMapping
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

    @GetMapping("/statistic/revenue")
    public List<Double> getStatisticRevenue(@RequestParam("year") int year) {
        return orderService.statisticRevenue(year);
    }

    @GetMapping("/statistic/get-total")
    public long getTotal() {
        return orderService.totalOrder();
    }

    @GetMapping("/statistic/get-totalsale")
    public Double getTotalSale() {
        return orderService.totalSales();
    }

}
