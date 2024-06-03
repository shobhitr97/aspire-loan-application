package org.aspire.controller;

import org.aspire.dto.CreateLoanRequestDTO;
import org.aspire.dto.LoanDTO;
import org.aspire.dto.MakeRepaymentDTO;

import java.util.List;

public interface ApplicantInterface {

    LoanDTO createLoan(CreateLoanRequestDTO createLoanRequest);

    List<LoanDTO> getLoans();

    void makeRepayment(String loanId, MakeRepaymentDTO makeRepaymentDTO);
}
