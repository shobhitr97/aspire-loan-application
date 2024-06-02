package org.aspire.handler.impl;

import org.aspire.dao.LoanDao;
import org.aspire.data.Loan;
import org.aspire.data.Repayment;
import org.aspire.handler.LoanHandler;
import org.aspire.model.CreateLoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanHandlerImpl implements LoanHandler {

    private final LoanDao loanDao;

    @Autowired
    public LoanHandlerImpl(LoanDao loanDao) {
        this.loanDao = loanDao;
    }

    @Override
    public Loan getLoanById(String loanId) {
        return loanDao.getLoanById(loanId);
    }

    @Override
    public Loan createLoan(CreateLoanRequest createLoanRequest) {
        Loan loan = createLoanFromRequest(createLoanRequest);
        loanDao.createLoan(loan);
        return loanDao.getLoanById(loan.getLoanId());
    }

    private Loan createLoanFromRequest(CreateLoanRequest createLoanRequest) {
        List<Repayment> repayments = new ArrayList<>();
        // TODO: Correct this once tested
        repayments.add(Repayment.builder()
                        .amount(createLoanRequest.getAmount())
                .build());
        Loan loan = new Loan();
        loan.setTerms(createLoanRequest.getTerms());
        loan.setAmount(createLoanRequest.getAmount());
        loan.setApplicant(createLoanRequest.getApplicant());
        loan.setRepayments(repayments);
        return loan;
    }
}
