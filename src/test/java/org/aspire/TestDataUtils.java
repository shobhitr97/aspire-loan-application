package org.aspire;

import org.aspire.constants.LoanConstants;
import org.aspire.data.Loan;
import org.aspire.data.Repayment;
import org.aspire.data.User;
import org.aspire.model.EntityAction;
import org.aspire.model.RequestMetadata;
import org.aspire.utils.GenericUtils;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestDataUtils {

    public static final String TEST_REQUEST_ID = "testRequest0128731";
    public static final String TEST_APPLICANT_USER_ID = "applicantUserId1232334";
    public static final String TEST_ADMIN_USER_ID = "adminUserId1232334";
    public static final String TEST_APPLICANT_USERNAME = "testApplicant";
    public static final String TEST_ADMIN_USERNAME = "testAdmin";
    public static final String TEST_PASSWORD = "password";
    public static final String TEST_APPLICANT_FULL_NAME = "Applicant";
    public static final String TEST_ADMIN_FULL_NAME = "Admin";
    public static final String TEST_LOAN_ID = "testLoanId12414";

    public static RequestMetadata createAdminRequestMetadata() {
        return createRequestMetadata(createTestAdminUser());
    }

    public static RequestMetadata createApplicantRequestMetadata() {
        return createRequestMetadata(createTestApplicantUser());
    }

    public static RequestMetadata createRequestMetadata(User user) {
        RequestMetadata requestMetadata = new RequestMetadata();
        requestMetadata.setRequestId(TEST_REQUEST_ID);
        requestMetadata.setUser(user);
        return requestMetadata;
    }

    public static User createTestApplicantUser() {
        User user = new User();
        user.setFullName(TEST_APPLICANT_FULL_NAME);
        user.setUserId(TEST_APPLICANT_USER_ID);
        user.setUsername(TEST_APPLICANT_USERNAME);
        user.setEncodedCredentials(HttpHeaders.encodeBasicAuth(TEST_APPLICANT_USERNAME, TEST_PASSWORD, StandardCharsets.UTF_8));
        user.setUserType(LoanConstants.APPLICANT_USER_TYPE);
        return user;
    }

    public static User createTestAdminUser() {
        User user = new User();
        user.setFullName(TEST_ADMIN_FULL_NAME);
        user.setUserId(TEST_ADMIN_USER_ID);
        user.setUsername(TEST_ADMIN_USERNAME);
        user.setEncodedCredentials(HttpHeaders.encodeBasicAuth(TEST_ADMIN_USERNAME, TEST_PASSWORD, StandardCharsets.UTF_8));
        user.setUserType(LoanConstants.ADMIN_USER_TYPE);
        return user;
    }

    public static Loan createTestLoan() {
        Loan loan = new Loan();
        loan.setLoanId(TEST_LOAN_ID);
        loan.setStatus(LoanConstants.PENDING_LOAN_STATUS);
        loan.setAmount(10000);
        loan.setApplicant(TEST_APPLICANT_USER_ID);
        loan.setTerms(2);
        long now = System.currentTimeMillis();
        List<Repayment> repayments = new ArrayList<>();
        repayments.add(Repayment.builder()
                .status(LoanConstants.PENDING_LOAN_STATUS)
                .expectedByMillis(now + Duration.ofDays(15).toMillis())
                .amount(5000)
                .pendingAmount(5000)
                .transactionIds(new ArrayList<>())
                .build());
        repayments.add(Repayment.builder()
                .status(LoanConstants.PENDING_LOAN_STATUS)
                .expectedByMillis(now + Duration.ofDays(30).toMillis())
                .amount(5000)
                .pendingAmount(5000)
                .transactionIds(new ArrayList<>())
                .build());
        loan.setRepayments(repayments);
        GenericUtils.updateBaseEntity(EntityAction.INSERT, loan, TEST_APPLICANT_USER_ID);
        return loan;
    }

    public static Loan createTestPartiallyPaidLoan() {
        return createTestPartiallyPaidLoan(TEST_APPLICANT_USER_ID);
    }

    public static Loan createTestPartiallyPaidLoan(String userId) {
        Loan loan = new Loan();
        loan.setLoanId(TEST_LOAN_ID);
        loan.setStatus(LoanConstants.APPROVED_LOAN_STATUS);
        loan.setAmount(10000);
        loan.setApplicant(userId);
        loan.setTerms(2);
        long now = System.currentTimeMillis();
        List<Repayment> repayments = new ArrayList<>();
        repayments.add(Repayment.builder()
                .status(LoanConstants.PAID_LOAN_STATUS)
                .expectedByMillis(now + Duration.ofDays(15).toMillis())
                .amount(5000)
                .pendingAmount(0)
                .transactionIds(new ArrayList<>() {{ add("7610b8276348723841471284"); }})
                .build());
        repayments.add(Repayment.builder()
                .status(LoanConstants.PENDING_LOAN_STATUS)
                .expectedByMillis(now + Duration.ofDays(30).toMillis())
                .amount(5000)
                .pendingAmount(5000)
                .transactionIds(new ArrayList<>())
                .build());
        loan.setRepayments(repayments);
        GenericUtils.updateBaseEntity(EntityAction.INSERT, loan, userId);
        return loan;
    }
}
