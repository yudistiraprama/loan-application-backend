package com.loan.loanapp.controller;

import com.loan.loanapp.dto.ApiResponse;
import com.loan.loanapp.dto.LoanRequest;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ApiResponse<Loan> applyLoan(@RequestBody LoanRequest request, Authentication authentication) {
        String email = authentication.getName();
        Loan loan = loanService.applyLoan(email, request);
        return new ApiResponse<>(true, "Loan submitted", loan);
    }

    @GetMapping
    public List<Loan> getMyLoans(Authentication authentication) {
        String email = authentication.getName();
        return loanService.getMyLoans(email);
    }

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

}
