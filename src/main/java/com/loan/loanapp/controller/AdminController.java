package com.loan.loanapp.controller;

import com.loan.loanapp.dto.ApiResponse;
import com.loan.loanapp.dto.CreateAdminRequest;
import com.loan.loanapp.dto.RejectLoanRequest;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.service.AuthService;
import com.loan.loanapp.service.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final LoanService loanService;
    private final AuthService authService;

    @PutMapping("/approve/{id}")
    public ApiResponse<Loan> approve(@PathVariable Long id) {
        Loan loan = loanService.approveLoan(id);
        return new ApiResponse<>(true, "Loan approved", loan);
    }

    @PutMapping("/reject/{id}")
    public ApiResponse<Loan> reject(@PathVariable Long id) {
        Loan loan = loanService.rejectLoan(id);
        return new ApiResponse<>(true, "Loan rejected", loan);
    }

    @PutMapping("/reject/{id}")
    public ApiResponse<Loan> reject(
            @PathVariable Long id,
            @RequestBody RejectLoanRequest request
    ) {
        Loan loan = loanService.rejectLoan(id, request.reason());

        return new ApiResponse<>(true, "Loan rejected", loan);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> createAdmin(
            @Valid @RequestBody CreateAdminRequest request
    ) {
        return new ApiResponse<>(
                true,
                authService.createAdmin(request),
                null
        );
    }
}
