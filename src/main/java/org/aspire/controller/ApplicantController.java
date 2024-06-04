package org.aspire.controller;

import org.aspire.constants.LoanConstants;
import org.aspire.data.Loan;
import org.aspire.data.User;
import org.aspire.dto.CreateLoanRequestDTO;
import org.aspire.dto.LoanDTO;
import org.aspire.dto.MakeRepaymentDTO;
import org.aspire.exception.ValidationException;
import org.aspire.handler.ILoanHandler;
import org.aspire.model.CreateLoanRequest;
import org.aspire.model.LoanRepaymentRequest;
import org.aspire.model.RequestMetadata;
import org.aspire.utils.GenericUtils;
import org.aspire.utils.ObjectConverterUtils;
import org.aspire.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController implements ApplicantInterface {

    @Autowired
    private ILoanHandler loanHandler;

    @Autowired
    private RequestMetadata requestMetadata;

    @Override
    @PostMapping("/loan")
    public LoanDTO createLoan(@RequestBody CreateLoanRequestDTO createLoanRequest) {
        ValidatorUtils.validateNotNull(createLoanRequest, "request body");
        ValidatorUtils.validateInRange(createLoanRequest.getAmount(), 1, "loan amount");
        ValidatorUtils.validateInRange(createLoanRequest.getTerms(), 1, "loan terms");

        User user = requestMetadata.getUser();
        Loan loan = loanHandler.createLoan(
                CreateLoanRequest.builder()
                        .terms(createLoanRequest.getTerms())
                        .amount(createLoanRequest.getAmount())
                        .applicant(user.getUserId())
                        .build());
        return ObjectConverterUtils.createLoanDTO(loan);
    }

    @Override
    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        String userId = requestMetadata.getUser().getUserId();
        List<Loan> loansForUser = loanHandler.getLoansForUser(userId);
        return loansForUser.stream().map(ObjectConverterUtils::createLoanDTO).toList();
    }

    @Override
    @PostMapping("/loan/{loanId}/repayment")
    public void makeRepayment(@PathVariable String loanId, @RequestBody MakeRepaymentDTO makeRepaymentDTO) {
        ValidatorUtils.validateNotNull(makeRepaymentDTO, "request body");
        ValidatorUtils.validateNotBlank(loanId, "loan Id");
        ValidatorUtils.validateNotBlank(makeRepaymentDTO.getTransactionRef(), "transaction ref");

        Loan loan = loanHandler.getLoanById(loanId);
        if (loan == null) {
            throw new IllegalArgumentException(String.format("no loan found for loanId: %s", loanId));
        }
        ValidatorUtils.validateInRange(makeRepaymentDTO.getAmount(), 0.1d, GenericUtils.getPendingAmount(loan), "repayment amount");
        if (!LoanConstants.APPROVED_LOAN_STATUS.equals(loan.getStatus())) {
            throw new IllegalArgumentException(String.format("Loan %s is already %s", loanId, loan.getStatus()));
        }
        String userId = requestMetadata.getUser().getUserId();
        if (!userId.equals(loan.getApplicant())) {
            throw new ValidationException("only applicant is allowed to make changes to the loan");
        }

        loanHandler.makeRepayment(LoanRepaymentRequest.builder()
                .transactionRef(makeRepaymentDTO.getTransactionRef())
                .amount(makeRepaymentDTO.getAmount())
                .loan(loan)
                .build());
    }
}
