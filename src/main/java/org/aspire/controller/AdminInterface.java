package org.aspire.controller;

import org.aspire.dto.ApproveLoanDTO;
import org.aspire.dto.LoanDTO;

import java.util.List;

public interface AdminInterface {

    void approveLoan(ApproveLoanDTO approveLoanDTO);

    List<LoanDTO> getLoansByStatus(String status);
}
