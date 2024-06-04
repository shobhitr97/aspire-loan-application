package org.aspire.dao;

import org.aspire.dao.repository.LoanRepository;
import org.aspire.data.Loan;
import org.aspire.model.EntityAction;
import org.aspire.utils.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanDao implements ILoanDao {


    private final LoanRepository repo;

    @Autowired
    public LoanDao(LoanRepository repo) {
        this.repo = repo;
    }


    @Override
    public Loan createLoan(Loan loan, String userId) {
        GenericUtils.updateBaseEntity(EntityAction.INSERT, loan, userId);
        return this.repo.insert(loan);
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
    public void updateLoan(Loan loan, String userId) {
        GenericUtils.updateBaseEntity(EntityAction.UPDATE, loan, userId);
        this.repo.save(loan);
    }

    @Override
    public Loan getLoanById(String loanId) {
        return this.repo.getLoanById(loanId);
    }
}
