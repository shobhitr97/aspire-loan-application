package org.aspire.utils;

import org.aspire.constants.LoanConstants;
import org.aspire.data.BaseEntity;
import org.aspire.data.Loan;
import org.aspire.data.Repayment;
import org.aspire.model.EntityAction;

public class GenericUtils {

    public static String getCredentialsFromHeader(String header) {
        return header.split(" ")[1];
    }

    public static double getPendingAmount(Loan loan) {
        return loan.getRepayments()
                .stream()
                .filter(repayment -> LoanConstants.PENDING_REPAYMENT_STATUS.equals(repayment.getStatus()))
                .map(Repayment::getPendingAmount)
                .reduce(Double::sum)
                .orElse(0d);
    }

    public static void updateBaseEntity(EntityAction action, BaseEntity entity, String userId) {
        long now = System.currentTimeMillis();
        entity.setUpdatedTimeMillis(now);
        entity.setLastUpdatedBy(userId);
        switch (action) {
            case DELETE -> {
                entity.setDeleted(true);
            }
            case INSERT -> {
                entity.setCreatedTimeMillis(now);
            }
        }
    }
}
