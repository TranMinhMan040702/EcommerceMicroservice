package com.criscode.statistic.controller;

import com.criscode.statistic.service.impl.IStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin({"https://thunderous-basbousa-75b1ca.netlify.app/", "http://localhost:3000/"})
@RestController
@RequestMapping("/api/v1/statistic-service/")
@RequiredArgsConstructor
public class StatisticController {

    private final IStatisticsService statisticsService;

    @GetMapping("admin/statistic/chartSales")
    public ResponseEntity<?> statisticRevenue(@RequestParam("year") int year) {
        return ResponseEntity.ok(statisticsService.statisticRevenue(year));
    }

    @GetMapping("admin/statistic/total")
    public ResponseEntity<?> getTotal() {
        return ResponseEntity.ok(statisticsService.getTotal());
    }

    @GetMapping("admin/statistic/chartRating")
    public ResponseEntity<?> statisticRating() {
        return ResponseEntity.ok(statisticsService.statisticRating());
    }

}
