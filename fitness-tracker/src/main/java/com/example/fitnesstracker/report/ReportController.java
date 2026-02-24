package com.example.fitnesstracker.report;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService service;

    @GetMapping
    public List<WeeklyReportDto> getReport(
            @RequestParam int year,
            @RequestParam int month
    ) {
        return service.getMonthlyReport(year, month);
    }
}
