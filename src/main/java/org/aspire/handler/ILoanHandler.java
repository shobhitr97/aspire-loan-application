package org.aspire.handler;

import org.aspire.data.Loan;
import org.aspire.model.CreateLoanRequest;
import org.aspire.model.LoanRepaymentRequest;

import java.util.List;

public interface ILoanHandler {

    Loan getLoanById(String loanId);

    Loan createLoan(CreateLoanRequest createLoanRequest);

    void approveLoan(Loan loan, boolean approved);

    List<Loan> getLoansForUser(String userId);

    List<Loan> getLoansByStatus(String status);

    void makeRepayment(LoanRepaymentRequest loanRepaymentRequest);
}
