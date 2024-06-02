package org.aspire.dao;


import org.aspire.data.Loan;

import java.util.List;

public interface LoanDao {

    void createLoan(Loan loan);

    List<Loan> getAllUserLoans(String userId);

    List<Loan> getAllLoansWithStatus(String status);

    void updateLoan(Loan loan);

    Loan getLoanById(String loanId);
}
