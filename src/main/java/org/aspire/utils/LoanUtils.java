package org.aspire.utils;

import org.aspire.constants.LoanConstants;
import org.aspire.data.Loan;
import org.aspire.data.Repayment;

public class LoanUtils {

    public static int getPendingAmount(Loan loan) {
        return loan.getRepayments()
                .stream()
                .filter(repayment -> LoanConstants.PENDING_REPAYMENT_STATUS.equals(repayment.getStatus()))
                .map(Repayment::getPendingAmount)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
