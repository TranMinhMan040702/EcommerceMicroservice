package com.criscode.order.repository;

import com.criscode.order.entity.Order;
import com.criscode.order.entity.StatusOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>, JpaSpecificationExecutor<Order> {
    List<Order> findByUserId(Integer userId, Sort sort);

    List<Order> findByUserIdAndStatus(Integer userId, StatusOrder status, Sort sort);

    List<Order> findByStatus(StatusOrder status, Pageable pageable, Specification<Order> specification);
}
