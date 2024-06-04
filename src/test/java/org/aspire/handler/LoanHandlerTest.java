package org.aspire.handler;

import org.aspire.TestDataUtils;
import org.aspire.constants.LoanConstants;
import org.aspire.dao.LoanDao;
import org.aspire.dao.TransactionDao;
import org.aspire.data.Loan;
import org.aspire.model.CreateLoanRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class LoanHandlerTest {

    @Mock
    private LoanDao loanDao;

    @Mock
    private TransactionDao transactionDao;

    @InjectMocks
    private LoanHandler loanHandler;

    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(LoanHandlerTest.class);
    }

    @Test
    public void getLoanById_success() {
        Loan testLoan = TestDataUtils.createTestLoan();
        Mockito.when(loanDao.getLoanById(TestDataUtils.TEST_LOAN_ID)).thenReturn(testLoan);

        Loan returnedLoan = loanHandler.getLoanById(TestDataUtils.TEST_LOAN_ID);
        Assertions.assertEquals(testLoan, returnedLoan);
    }

    @Test
    public void createLoan_success() {
        Loan testLoan = TestDataUtils.createTestLoan();
        CreateLoanRequest createLoanRequest = CreateLoanRequest.builder()
                .terms(2)
                .applicant(TestDataUtils.TEST_APPLICANT_USER_ID)
                .amount(10000)
                .build();
        Mockito.doReturn(testLoan).when(loanDao).createLoan(Mockito.any(Loan.class), Mockito.anyString());
        Loan returnedLoan = loanHandler.createLoan(createLoanRequest);

        Assertions.assertEquals(testLoan, returnedLoan);
    }

    @Test
    public void approveLoan_success() {
        Loan testLoan = TestDataUtils.createTestLoan();
        Mockito.doNothing().when(loanDao).updateLoan(testLoan, "adminUser");
        loanHandler.approveLoan(testLoan, true, "adminUser");
        testLoan.setStatus(LoanConstants.APPROVED_LOAN_STATUS);
        Mockito.verify(loanDao, Mockito.times(1)).updateLoan(testLoan, "adminUser");
    }

    @Test
    public void getLoansForUser_success() {
        List<Loan> testList = new ArrayList<>();
        Mockito.doReturn(testList).when(loanDao).getAllUserLoans("randomId");
        List<Loan> loansForUser = loanHandler.getLoansForUser("randomId");
        Assertions.assertEquals(testList, loansForUser);
    }

    @Test
    public void getLoansByStatus_success() {
        List<Loan> testList = new ArrayList<>();
        Mockito.doReturn(testList).when(loanDao).getAllLoansWithStatus("randomId");
        List<Loan> loansForUser = loanHandler.getLoansByStatus("randomId");
        Assertions.assertEquals(testList, loansForUser);
    }
}
