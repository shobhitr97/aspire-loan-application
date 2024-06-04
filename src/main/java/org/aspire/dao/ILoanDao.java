package org.aspire.dao;


import org.aspire.data.Loan;

import java.util.List;

public interface ILoanDao {

    Loan createLoan(Loan loan, String userId);

    List<Loan> getAllUserLoans(String userId);

    List<Loan> getAllLoansWithStatus(String status);

    void updateLoan(Loan loan, String userId);

    Loan getLoanById(String loanId);
}
