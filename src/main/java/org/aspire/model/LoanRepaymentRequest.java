package org.aspire.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.aspire.data.Loan;

@Builder
@Getter
@EqualsAndHashCode
public class LoanRepaymentRequest {

    private Loan loan;
    private double amount;
    private String transactionRef;
}
