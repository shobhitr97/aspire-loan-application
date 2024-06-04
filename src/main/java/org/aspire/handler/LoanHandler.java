package org.aspire.handler;

import org.aspire.constants.LoanConstants;
import org.aspire.dao.ILoanDao;
import org.aspire.dao.ITransactionDao;
import org.aspire.data.Loan;
import org.aspire.data.Repayment;
import org.aspire.data.Transaction;
import org.aspire.model.CreateLoanRequest;
import org.aspire.model.LoanRepaymentRequest;
import org.aspire.utils.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class LoanHandler implements ILoanHandler {

    private final ILoanDao loanDao;

    private final ITransactionDao transactionDao;

    @Autowired
    public LoanHandler(ILoanDao loanDao, ITransactionDao transactionDao) {
        this.loanDao = loanDao;
        this.transactionDao = transactionDao;
    }

    @Override
    public Loan getLoanById(String loanId) {
        return loanDao.getLoanById(loanId);
    }

    @Override
    public Loan createLoan(CreateLoanRequest createLoanRequest) {
        Loan loan = createLoanFromRequest(createLoanRequest);
        return loanDao.createLoan(loan, loan.getApplicant());
    }

    @Override
    public void approveLoan(Loan loan, boolean approved, String userId) {
        loan.setStatus(approved ? LoanConstants.APPROVED_LOAN_STATUS : LoanConstants.REJECTED_LOAN_STATUS);
        loanDao.updateLoan(loan, userId);
    }

    @Override
    public List<Loan> getLoansForUser(String userId) {
        return loanDao.getAllUserLoans(userId);
    }

    @Override
    public List<Loan> getLoansByStatus(String status) {
        return loanDao.getAllLoansWithStatus(status);
    }

    @Override
    public void makeRepayment(LoanRepaymentRequest loanRepaymentRequest) {
        Loan loan = loanRepaymentRequest.getLoan();
        String transactionRef = loanRepaymentRequest.getTransactionRef();
        double amount = loanRepaymentRequest.getAmount();

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionRef(transactionRef);
        transaction.setLoanId(loan.getLoanId());
        transaction.setAmount(amount);
        transaction.setUserId(loan.getApplicant());
        transactionDao.createTransaction(transaction, loan.getApplicant());

        double remainingMoney = amount;
        for (Repayment repayment : loan.getRepayments()) {
            if (LoanConstants.PENDING_REPAYMENT_STATUS.equals(repayment.getStatus())) {
                double pendingAmount = repayment.getPendingAmount();
                repayment.getTransactionIds().add(transaction.getTransactionId());

                if (Math.abs(remainingMoney - pendingAmount) <= 0.1d) {
                    repayment.setStatus(LoanConstants.PAID_REPAYMENT_STATUS);
                    repayment.setPendingAmount(0d);
                    repayment.setPaidOnMillis(System.currentTimeMillis());
                    remainingMoney = 0d;
                } else if (remainingMoney > pendingAmount) {
                    repayment.setStatus(LoanConstants.PAID_REPAYMENT_STATUS);
                    repayment.setPendingAmount(0d);
                    repayment.setPaidOnMillis(System.currentTimeMillis());
                    remainingMoney -= pendingAmount;
                } else {
                    repayment.setPendingAmount(pendingAmount - remainingMoney);
                    remainingMoney = 0d;
                }

                if (remainingMoney == 0d) {
                    break;
                }
            }
        }

        double finalLoanPendingAmount = GenericUtils.getPendingAmount(loan);
        if (finalLoanPendingAmount <= 0.1d) {
            loan.setStatus(LoanConstants.PAID_LOAN_STATUS);
        }

        loanDao.updateLoan(loan, loan.getApplicant());
    }

    private Loan createLoanFromRequest(CreateLoanRequest createLoanRequest) {
        List<Repayment> repayments = new ArrayList<>();
        long now = System.currentTimeMillis();
        double repaymentAmount = createLoanRequest.getAmount() / (double) createLoanRequest.getTerms();
        for (int i = 0; i < createLoanRequest.getTerms(); i++) {
            long repaymentTimeMillis = now + Duration.ofDays(7L * (i + 1)).toMillis();
            repayments.add(Repayment.builder()
                    .transactionIds(new ArrayList<>())
                    .amount(repaymentAmount)
                    .pendingAmount(repaymentAmount)
                    .status(LoanConstants.PENDING_REPAYMENT_STATUS)
                    .expectedByMillis(repaymentTimeMillis)
                    .build());
        }

        Loan loan = new Loan();
        loan.setTerms(createLoanRequest.getTerms());
        loan.setAmount(createLoanRequest.getAmount());
        loan.setApplicant(createLoanRequest.getApplicant());
        loan.setRepayments(repayments);
        loan.setStatus(LoanConstants.PENDING_LOAN_STATUS);
        return loan;
    }
}
