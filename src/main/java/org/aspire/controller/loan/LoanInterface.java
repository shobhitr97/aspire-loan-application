package org.aspire.controller.loan;

import org.aspire.dto.CreateLoanRequestDTO;
import org.aspire.dto.LoanDTO;

public interface LoanInterface {

    LoanDTO createLoan(CreateLoanRequestDTO createLoanRequest);

    LoanDTO getLoanById(String loanId);
}
