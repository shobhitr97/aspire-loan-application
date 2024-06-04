package org.aspire.controller;

import org.aspire.constants.LoanConstants;
import org.aspire.data.Loan;
import org.aspire.dto.ApproveLoanDTO;
import org.aspire.dto.LoanDTO;
import org.aspire.handler.ILoanHandler;
import org.aspire.model.RequestMetadata;
import org.aspire.utils.ObjectConverterUtils;
import org.aspire.utils.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController implements AdminInterface {

    @Autowired
    private ILoanHandler loanHandler;

    @Autowired
    private RequestMetadata requestMetadata;

    @Override
    @PostMapping("/loan/approve")
    public void approveLoan(@RequestBody ApproveLoanDTO approveLoanDTO) {
        ValidatorUtils.validateNotNull(approveLoanDTO, "request body");
        ValidatorUtils.validateNotBlank(approveLoanDTO.getLoanId(), "loan Id");

        Loan loan = loanHandler.getLoanById(approveLoanDTO.getLoanId());
        if (loan == null) {
            throw new IllegalArgumentException(String.format("no loan found for loanId: %s", approveLoanDTO.getLoanId()));
        }
        if (!LoanConstants.PENDING_LOAN_STATUS.equals(loan.getStatus())) {
            throw new IllegalArgumentException(String.format("Loan %s is already %s", approveLoanDTO.getLoanId(), loan.getStatus()));
        }
        loanHandler.approveLoan(loan, approveLoanDTO.isApproved(), requestMetadata.getUser().getUserId());
    }

    @Override
    @GetMapping("/loans/{status}")
    public List<LoanDTO> getLoansByStatus(@PathVariable String status) {
        ValidatorUtils.validateInValues(status, LoanConstants.ALLOWED_LOAN_STATUSES, "loan status");

        List<Loan> loans = loanHandler.getLoansByStatus(status);
        return loans.stream().map(ObjectConverterUtils::createLoanDTO).toList();
    }
}
