package com.criscode.statistic.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface IStatisticsService {


    List<Double> statisticRevenue(int year);

    Map<Object, Object> getTotal();

    List<Long> statisticRating();
}
