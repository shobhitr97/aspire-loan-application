package org.aspire.controller;

import org.aspire.dto.CreateLoanRequestDTO;
import org.aspire.dto.LoanDTO;

public interface LoanInterface {

    LoanDTO createLoan(CreateLoanRequestDTO createLoanRequest);

    LoanDTO getLoanById(String loanId);
}
