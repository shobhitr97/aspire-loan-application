package org.aspire.constants;

import java.util.ArrayList;
import java.util.List;

public class LoanConstants {

    public static final String PENDING_LOAN_STATUS = "pending";
    public static final String APPROVED_LOAN_STATUS = "approved";
    public static final String REJECTED_LOAN_STATUS = "rejected";
    public static final String PAID_LOAN_STATUS = "paid";

    public static final String APPLICANT_USER_TYPE = "applicant";
    public static final String ADMIN_USER_TYPE = "admin";

    public static final String PENDING_REPAYMENT_STATUS = "pending";
    public static final String PAID_REPAYMENT_STATUS = "paid";

    public static final List<String> ALLOWED_USER_TYPES = new ArrayList<>() {{
        add(APPLICANT_USER_TYPE);
        add(ADMIN_USER_TYPE);
    }};

    public static final List<String> ALLOWED_LOAN_STATUSES = new ArrayList<>() {{
        add(PENDING_LOAN_STATUS);
        add(APPROVED_LOAN_STATUS);
        add(REJECTED_LOAN_STATUS);
    }};
}
