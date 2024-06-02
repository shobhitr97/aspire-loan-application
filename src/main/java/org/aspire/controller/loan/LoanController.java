package org.aspire.controller.loan;

import org.aspire.data.Loan;
import org.aspire.handler.LoanHandler;
import org.aspire.dto.CreateLoanRequestDTO;
import org.aspire.dto.LoanDTO;
import org.aspire.model.CreateLoanRequest;
import org.aspire.utils.ObjectConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoanController implements LoanInterface {

    private final LoanHandler loanHandler;

    @Autowired
    public LoanController(LoanHandler loanHandler) {
        this.loanHandler = loanHandler;
    }

    @Override
    @PostMapping
    public LoanDTO createLoan(@RequestBody CreateLoanRequestDTO createLoanRequest) {
        String applicant = "Test User";
        Loan loan = loanHandler.createLoan(CreateLoanRequest.builder()
                .terms(createLoanRequest.getTerms())
                .amount(createLoanRequest.getAmount())
                .applicant(applicant)
                .build());
        return ObjectConverterUtils.createLoanDTO(loan);
    }

    @Override
    @GetMapping("/{loanId}")
    public LoanDTO getLoanById(@PathVariable("loanId") String loanId) {
        Loan loan = loanHandler.getLoanById(loanId);
        return ObjectConverterUtils.createLoanDTO(loan);
    }
}
