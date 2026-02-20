package com.loan.loanapp.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {
    private BigDecimal amount;
    private Integer tenorMonth;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTenorMonth() {
        return tenorMonth;
    }

    public void setTenorMonth(Integer tenorMonth) {
        this.tenorMonth = tenorMonth;
    }
}
