package org.aspire.dao.impl;

import org.aspire.dao.LoanDao;
import org.aspire.dao.repository.LoanRepository;
import org.aspire.data.Loan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanDaoImpl implements LoanDao {


    private final LoanRepository repo;

    @Autowired
    public LoanDaoImpl(LoanRepository repo) {
        this.repo = repo;
    }


    @Override
    public void createLoan(Loan loan) {
        this.repo.insert(loan);
    }

    @Override
    public List<Loan> getAllUserLoans(String userId) {
        return this.repo.getLoansByApplicantName(userId);
    }

    @Override
    public List<Loan> getAllLoansWithStatus(String status) {
        return this.repo.findLoansByStatus(status);
    }

    @Override
    public void updateLoan(Loan loan) {
        this.repo.save(loan);
    }

    @Override
    public Loan getLoanById(String loanId) {
        return this.repo.getLoanById(loanId);
    }
}
