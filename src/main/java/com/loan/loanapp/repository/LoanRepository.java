package com.loan.loanapp.repository;

import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanStatus;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByUserId(Long userID);

    Boolean existsByUserIdAndStatusIn(Long userId, List<LoanStatus> statuses);

    Loan findFirstByUserIdAndStatusInOrderByCreatedAtDesc(
            Long userId,
            List<LoanStatus> statuses
    );
}
