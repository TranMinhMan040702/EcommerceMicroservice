package com.criscode.order.service;

import com.criscode.clients.cart.CartClient;
import com.criscode.clients.product.ProductClient;
import com.criscode.clients.user.UserClient;
import com.criscode.clients.user.dto.UserExistResponse;
import com.criscode.exceptionutils.InvalidDataException;
import com.criscode.exceptionutils.NotFoundException;
import com.criscode.order.converter.OrderConverter;
import com.criscode.order.dto.OrderDto;
import com.criscode.order.entity.Order;
import com.criscode.order.repository.OrderRepository;
import com.criscode.order.specification.OrderSpecification;
import com.criscode.order.utils.HandleStatusOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final ProductClient productClient;
    private final CartClient cartClient;
    private final UserClient userClient;

    /**
     * @param status
     * @param page
     * @param limit
     * @param sortBy
     * @param search
     * @return
     */
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

    /**
     * @param userId
     * @return
     */
    public List<OrderDto> findAllOrdersByUser(Integer userId) {
        // todo: check order with order of user current
        UserExistResponse userExistResponse = userClient.existed(userId);
        if (!userExistResponse.existed()) {
            throw new NotFoundException("User does not exist with id: " + userId);
        }
        List<Order> orders = orderRepository.findByUserId(userId, Sort.by("createdAt").descending());

        return orders.stream().map(orderConverter::mapToDto).collect(Collectors.toList());
    }

    /**
     * @param orderId
     * @return
     */
    public OrderDto findOrderById(Integer orderId) {
        // todo: check order with order of user current
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        return orderConverter.mapToDto(order);
    }

    /**
     * @param userId
     * @param status
     * @return
     */
    public List<OrderDto> findOrderByStatus(Integer userId, String status) {
        // todo: check order with order of user current
        // todo: check user id with user current
        List<Order> orders = orderRepository.findByUserIdAndStatus(
                userId, HandleStatusOrder.handleStatus(status), Sort.by("createdAt").descending());
        return orders.stream().map(orderConverter::mapToDto).collect(Collectors.toList());
    }

    /**
     * @param orderDto
     * @return
     */
    public OrderDto createOrder(OrderDto orderDto) {
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
        return orderConverter.mapToDto(order);
    }


    /**
     * @param orderId
     * @param status
     * @return
     */
    public List<OrderDto> updateStatus(Integer orderId, String status) {
        // todo: check order with order of user current
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        order.setStatus(HandleStatusOrder.handleStatus(status));
        orderRepository.save(order);
        return findAllOrdersByUser(order.getId());
    }

    /**
     * @param orderId
     * @return
     */
    public List<OrderDto> deleteOrder(Integer orderId) {
        // todo: check order with order of user current
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new NotFoundException("Order does not exist with id: " + orderId)
        );
        orderRepository.delete(order);
        return findAllOrdersByUser(order.getUserId());
    }

}
