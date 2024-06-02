package org.aspire.handler;

import org.aspire.data.Loan;
import org.aspire.model.CreateLoanRequest;

public interface LoanHandler {

    Loan getLoanById(String loanId);

    Loan createLoan(CreateLoanRequest createLoanRequest);
}
