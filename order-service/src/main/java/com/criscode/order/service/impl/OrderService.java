package com.criscode.order.service.impl;

import com.criscode.clients.cart.CartClient;
import com.criscode.clients.notification.NotificationClient;
import com.criscode.clients.notification.dto.NotificationDto;
import com.criscode.clients.product.ProductClient;
import com.criscode.clients.user.UserClient;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.clients.websocket.NotificationSocketClient;
import com.criscode.exceptionutils.InvalidDataException;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.order.constants.ApplicationConstants;
import com.criscode.order.converter.OrderConverter;
import com.criscode.order.dto.OrderDto;
import com.criscode.order.entity.Order;
import com.criscode.order.repository.OrderRepository;
import com.criscode.order.service.IOrderService;
import com.criscode.order.specification.OrderSpecification;
import com.criscode.order.utils.CreateNotification;
import com.criscode.order.utils.HandleStatusOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final ProductClient productClient;
    private final CartClient cartClient;
    private final UserClient userClient;
    private final NotificationClient notificationClient;
    private final NotificationSocketClient notificationSocketClient;
    
    @Override
    public List<OrderDto> findAllOrdersByStatusWithPaginationAndSort(
            String status, Integer page, Integer limit, String sortBy, String search) {

        List<OrderDto> orderDtos = new ArrayList<>();

        PageRequest paging = PageRequest.of(page, limit, Sort.by(sortBy).descending());
        Specification<Order> specification = OrderSpecification.getSpecification(status, search);

        Page<Order> orders = orderRepository.findAll(specification, paging);

        for (Order order : orders) {
            orderDtos.add(orderConverter.mapToDto(order));
        }

        return orderDtos;
    }

    @Override
    public List<OrderDto> findAllOrdersByUser(Integer userId) {
        // todo: check order with order of user current
        UserExistResponse userExistResponse = userClient.existed(userId);
        if (!userExistResponse.existed()) {
            throw new NotFoundException("User does not exist with id: " + userId);
        }
        List<Order> orders = orderRepository.findByUserId(userId, Sort.by("createdAt").descending());

        return orders.stream().map(orderConverter::mapToDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto findOrderById(Integer orderId) {
        // todo: check order with order of user current
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        return orderConverter.mapToDto(order);
    }

    @Override
    public List<OrderDto> findOrderByStatus(Integer userId, String status) {
        // todo: check order with order of user current
        // todo: check user id with user current
        List<Order> orders = orderRepository.findByUserIdAndStatus(
                userId, HandleStatusOrder.handleStatus(status), Sort.by("createdAt").descending());
        return orders.stream().map(orderConverter::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void createOrder(OrderDto orderDto) {
        // todo: check order with order of user current
        // todo: check quantity product
        orderDto.getOrderItemDtos().stream().forEach(
                orderItemDto -> {
                    if (orderItemDto.getCount() > productClient.checkQuantityOfProduct(orderItemDto.getProductId())) {
                        throw new InvalidDataException("Quantity of product is not enough with product id: "
                                + orderItemDto.getProductId());
                    }
                }
        );
        Order order = orderRepository.save(orderConverter.mapToEntity(orderDto));
        // todo: update quantity and sold of product
        productClient.updateQuantityAndSoldProduct(orderDto.getOrderItemDtos());
        // todo: clear cart
        cartClient.clearedCart(cartClient.findCartByUser(orderDto.getUserId()).getId());
    }

    @Override
    public List<OrderDto> updateStatus(Integer orderId, String status) {
        // todo: check order with order of user current
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        if (status.equals(ApplicationConstants.DELIVERED)) {
            sendNotification(orderId, order.getUserId());
        }
        order.setStatus(HandleStatusOrder.handleStatus(status));
        orderRepository.save(order);
        return findAllOrdersByUser(order.getUserId());
    }

    private void sendNotification(Integer orderId, Integer userId) {
        NotificationDto notificationDto = CreateNotification.delivered(orderId, userId);
        // todo: push rabbitmq
        notificationClient.sendNotification(notificationDto);
        // todo: send to user
        notificationSocketClient.processMessage(notificationDto, userId);
    }

    @Override
    public List<OrderDto> deleteOrder(Integer orderId) {
        // todo: check order with order of user current
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        orderRepository.delete(order);
        return findAllOrdersByUser(order.getUserId());
    }

    @Override
    public String getStatusOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        return order.getStatus().toString();
    }

    @Override
    public long totalOrder() {
        return orderRepository.count();
    }

    @Override
    public Double totalSales() {
        return calulatorRevenue(orderRepository.findAll());
    }

    @Override
    public List<Double> statisticRevenue(int year) {
        List<Double> listRevenue = new ArrayList<>();

        for (int i = 0; i < 12; i++) {
            Specification<Order> specification = OrderSpecification
                    .statisticSpecification(getStartDate(i, year), getEndDate(i, year));
            List<Order> orders = orderRepository.findAll(specification);
            listRevenue.add(calulatorRevenue(orders));
        }

        return listRevenue;
    }

    private Date getStartDate(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    private Date getEndDate(int month, int year) {
        YearMonth yearMonthObject = YearMonth.of(year, month + 1);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
        calendar.set(Calendar.YEAR, year);
        return calendar.getTime();
    }

    private Double calulatorRevenue(List<Order> orders) {
        Double result = 0.0;
        for (Order order : orders) {
            result += order.getAmountFromUser();
        }
        return result;
    }

}
