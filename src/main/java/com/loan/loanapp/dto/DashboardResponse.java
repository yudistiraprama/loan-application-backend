package com.loan.loanapp.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DashboardResponse(
        String loanStatus,
        BigDecimal remainingBalance,
        BigDecimal monthlyInstallment,
        Long totalUnpaidInstallment,
        Long totalPaidInstallment,
        BigDecimal totalPaidAmount,
        Double progressPercentage,
        LocalDate nextDueDate,
        BigDecimal nextInstallmentAmount,
        Long daysUntilDue,
        Boolean overdue,
        String rejectionReason
) {}