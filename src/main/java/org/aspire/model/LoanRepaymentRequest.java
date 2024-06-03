package org.aspire.model;

import lombok.Builder;
import lombok.Getter;
import org.aspire.data.Loan;

@Builder
@Getter
public class LoanRepaymentRequest {

    private Loan loan;
    private int amount;
    private String transactionRef;
}
