package com.loan.loanapp.dto;

public record ApiResponse<T>(
        boolean success,
        String message,
        T data
) {}
