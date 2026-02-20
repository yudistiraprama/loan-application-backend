package com.loan.loanapp.repository;

import com.loan.loanapp.entity.LoanPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface LoanPaymentRepository extends JpaRepository<LoanPayment, Long> {

    List<LoanPayment> findByLoanId(Long loanId);

    Long countByLoanIdAndStatus(Long loanId, String status);

    LoanPayment findFirstByLoanIdAndStatusOrderByDueDateAsc(Long loanId, String status);

    @Query("SELECT COALESCE(SUM(lp.amount),0) FROM LoanPayment lp WHERE lp.loan.id = :loanId AND lp.status = 'PAID'")
    BigDecimal sumPaidAmount(Long loanId);
}
