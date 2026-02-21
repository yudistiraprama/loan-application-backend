package com.loan.loanapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "loan_payments")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

    private LocalDate dueDate;

    private BigDecimal amount;

    private String status;

}
