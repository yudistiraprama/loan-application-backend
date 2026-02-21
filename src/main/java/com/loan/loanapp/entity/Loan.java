package com.loan.loanapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "loans")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private BigDecimal amount;
    private Integer tenorMonth;
    private BigDecimal interestRate;
    private BigDecimal monthlyInstallment;
    private BigDecimal remainingBalance;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    @Column(length = 500)
    private String rejectionReason;

    private LocalDateTime createdAt = LocalDateTime.now();
}
