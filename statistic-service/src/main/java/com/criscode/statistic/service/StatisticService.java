package com.criscode.statistic.service;

import com.criscode.clients.order.OrderClient;
import com.criscode.clients.product.ProductClient;
import com.criscode.clients.review.ReviewClient;
import com.criscode.statistic.service.impl.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class StatisticService implements IStatisticsService {

    private final OrderClient orderClient;
    private final ProductClient productClient;
    private final ReviewClient reviewClient;

    @Override
    public List<Double> statisticRevenue(int year) {
        return orderClient.getStatisticRevenue(year);
    }


    @Override
    public Map<Object, Object> getTotal() {
        Map<Object, Object> response = new HashMap<>();
        response.put("totalSales", orderClient.getTotalSale());
        response.put("totalOrder", orderClient.getTotal());
        response.put("totalProduct", productClient.getTotalProduct());
        return response;
    }

    @Override
    public List<Long> statisticRating() {
        return reviewClient.statisticRating();
    }

}
