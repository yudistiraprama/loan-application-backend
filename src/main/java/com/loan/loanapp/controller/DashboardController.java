package com.loan.loanapp.controller;

import com.loan.loanapp.dto.ApiResponse;
import com.loan.loanapp.dto.DashboardResponse;
import com.loan.loanapp.service.LoanService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final LoanService loanService;

    public DashboardController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ApiResponse<DashboardResponse> getDashboard(Authentication auth) {
        DashboardResponse response = loanService.getDashboard(auth.getName());
        return new ApiResponse<>(true, "Dashboard data", response);
    }
}