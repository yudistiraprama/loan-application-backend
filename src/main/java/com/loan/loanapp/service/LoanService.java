package com.loan.loanapp.service;

import com.loan.loanapp.dto.DashboardResponse;
import com.loan.loanapp.dto.LoanRequest;
import com.loan.loanapp.entity.Loan;
import com.loan.loanapp.entity.LoanPayment;
import com.loan.loanapp.entity.LoanStatus;
import com.loan.loanapp.entity.User;
import com.loan.loanapp.repository.LoanPaymentRepository;
import com.loan.loanapp.repository.LoanRepository;
import com.loan.loanapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final LoanPaymentRepository loanPaymentRepository;

    private final EmailService emailService;
    private final SmsService smsService;

    public Loan applyLoan(String email, LoanRequest request) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        if (request.getAmount().compareTo(BigDecimal.valueOf(12000000)) > 0) {
            throw new RuntimeException("Maximum loan is 12,000,000");
        }

        if (request.getTenorMonth() > 12) {
            throw new RuntimeException("Maximum tenor is 12 months");
        }

        boolean hasActiveLoan = loanRepository.existsByUserIdAndStatusIn(
                user.getId(),
                List.of(LoanStatus.PENDING, LoanStatus.APPROVED)
        );

        if (hasActiveLoan) {
            throw new RuntimeException("You still have active loan");
        }

        BigDecimal interestRate = BigDecimal.valueOf(0.02);

        BigDecimal interest = request.getAmount()
                .multiply(interestRate)
                .multiply(BigDecimal.valueOf(request.getTenorMonth()));

        BigDecimal total = request.getAmount().add(interest);

        BigDecimal monthlyInstallment = total.divide(BigDecimal.valueOf(request.getTenorMonth()), 2, BigDecimal.ROUND_HALF_UP);

        Loan loan = Loan.builder()
                .user(user)
                .amount(request.getAmount())
                .tenorMonth(request.getTenorMonth())
                .interestRate(interestRate)
                .monthlyInstallment(monthlyInstallment)
                .remainingBalance(total)
                .status(LoanStatus.PENDING)
                .build();

        return loanRepository.save(loan);
    }

    public List<Loan> getMyLoans(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        return loanRepository.findByUserId(user.getId());
    }

    public Loan rejectLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus(LoanStatus.REJECTED);

        return loanRepository.save(loan);
    }

    @Transactional
    public Loan approveLoan(Long loanId) {

        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

        loan.setStatus(LoanStatus.APPROVED);
        loanRepository.save(loan);

        for (int i = 1; i <= loan.getTenorMonth(); i++) {

            LoanPayment payment = LoanPayment.builder()
                    .loan(loan)
                    .dueDate(LocalDate.now().plusMonths(i))
                    .amount(loan.getMonthlyInstallment())
                    .status("UNPAID")
                    .build();

            loanPaymentRepository.save(payment);
        }

        emailService.sendLoanApprovedEmail(
                loan.getUser().getEmail(),
                loan.getAmount().toString()
        );

        smsService.sendSms(
                loan.getUser().getPhone(),
                "Your loan of " + loan.getAmount() + " has been approved."
        );

        return loan;
    }

    public DashboardResponse getDashboard(String email) {

        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

        Loan loan = loanRepository.findFirstByUserIdAndStatusInOrderByCreatedAtDesc(
                user.getId(),
                List.of(LoanStatus.PENDING, LoanStatus.APPROVED, LoanStatus.REJECTED)
        );

        if (loan == null) {
            return new DashboardResponse(
                    "NO_ACTIVE_LOAN",
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    0L,
                    0L,
                    BigDecimal.ZERO,
                    0.0,
                    null,
                    null,
                    null,
                    false,
                    null
            );
        }

        Long unpaidCount = loanPaymentRepository.countByLoanIdAndStatus(loan.getId(), "UNPAID");

        Long paidCount = loanPaymentRepository.countByLoanIdAndStatus(loan.getId(), "PAID");

        BigDecimal totalPaidAmount = loanPaymentRepository.sumPaidAmount(loan.getId());

        LoanPayment nextPayment = loanPaymentRepository.findFirstByLoanIdAndStatusOrderByDueDateAsc(
                        loan.getId(),
                        "UNPAID"
                );

        Double progressPercentage = 0.0;
        if (loan.getTenorMonth() != null && loan.getTenorMonth() > 0) {
            progressPercentage = (paidCount.doubleValue() / loan.getTenorMonth()) * 100;
        }

        Boolean overdue = false;
        Long daysUntilDue = null;
        BigDecimal nextInstallmentAmount = null;

        if (nextPayment != null) {
            nextInstallmentAmount = nextPayment.getAmount();
            daysUntilDue = java.time.temporal.ChronoUnit.DAYS.between(
                            LocalDate.now(),
                            nextPayment.getDueDate()
                    );

            overdue = daysUntilDue < 0;
        }

        return new DashboardResponse(
                loan.getStatus().name(),
                loan.getRemainingBalance(),
                loan.getMonthlyInstallment(),
                unpaidCount,
                paidCount,
                totalPaidAmount,
                progressPercentage,
                nextPayment != null ? nextPayment.getDueDate() : null,
                nextInstallmentAmount,
                daysUntilDue,
                overdue,
                loan.getRejectionReason()
        );
    }

    @Transactional
    public Loan rejectLoan(Long loanId, String reason) {

        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

        if (loan.getStatus() == LoanStatus.APPROVED) {
            throw new RuntimeException("Approved loan cannot be rejected");
        }

        loan.setStatus(LoanStatus.REJECTED);
        loan.setRejectionReason(reason);

        loanRepository.save(loan);

        emailService.sendLoanApprovedEmail(
                loan.getUser().getEmail(),
                "Your loan was rejected. Reason: " + reason
        );

        smsService.sendSms(
                loan.getUser().getPhone(),
                "Loan rejected: " + reason
        );

        return loan;
    }
}
