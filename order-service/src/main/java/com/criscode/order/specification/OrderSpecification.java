package com.criscode.order.specification;

import com.criscode.order.entity.Order;
import com.criscode.order.entity.StatusOrder;
import com.criscode.order.utils.HandleStatusOrder;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> getSpecification(String status, String search) {
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (status != null && !status.isEmpty())
                predicates.add(
                        builder.equal(root.get("status"), HandleStatusOrder.handleStatus(status)));

            if (search != null && !search.isEmpty()) {
                predicates.add(builder.or(builder.like(root.get("phone"), "%" + search + "%"),
                        builder.like(root.get("address"), "%" + search + "%")));
            }

            return builder.and(predicates.toArray(new Predicate[0]));

        });
    }

    public static Specification<Order> statisticSpecification(Date startDate, Date endDate) {
        return ((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.and(builder.between(root.get("createdAt"), startDate, endDate),
                    builder.equal(root.get("status"), StatusOrder.DELIVERED)));

            return builder.and(predicates.toArray(new Predicate[0]));
        });
    }

}
