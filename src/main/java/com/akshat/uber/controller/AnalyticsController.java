package com.akshat.uber.controller;

import com.akshat.uber.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class AnalyticsController {
    private final AnalyticsService analytics;

    public AnalyticsController(AnalyticsService a) { this.analytics = a; }

    @GetMapping("/driver/{driver}/earnings")
    public Double earnings(@PathVariable String driver) {
        return analytics.totalEarnings(driver);
    }
}
