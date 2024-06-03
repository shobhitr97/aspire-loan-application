package org.aspire.utils;


import org.aspire.data.Loan;
import org.aspire.data.Repayment;
import org.aspire.dto.LoanDTO;
import org.aspire.dto.RepaymentDTO;

public class ObjectConverterUtils {

    public static LoanDTO createLoanDTO(Loan loan) {
        return LoanDTO.builder()
                .loanId(loan.getLoanId())
                .amount(loan.getAmount())
                .terms(loan.getTerms())
                .applicant(loan.getApplicant())
                .repayments(loan.getRepayments().stream().map(ObjectConverterUtils::createRepaymentDTO).toList())
                .build();
    }

    public static RepaymentDTO createRepaymentDTO(Repayment repayment) {
        return RepaymentDTO.builder()
                .amount(repayment.getAmount())
                .pendingAmount(repayment.getPendingAmount())
                .expectedByMillis(repayment.getExpectedByMillis())
                .paidOnMillis(repayment.getPaidOnMillis())
                .status(repayment.getStatus())
                .build();
    }
}
