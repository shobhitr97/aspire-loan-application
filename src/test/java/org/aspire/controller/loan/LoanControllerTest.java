package org.aspire.controller.loan;

import org.aspire.data.Loan;
import org.aspire.dto.LoanDTO;
import org.aspire.handler.LoanHandler;
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

@ExtendWith(MockitoExtension.class)
public class LoanControllerTest {

    @Mock
    private LoanHandler loanHandler;
    @InjectMocks
    private LoanController loanController;

    private static final String TEST_LOAN_ID = "testLoan-1";

    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(LoanControllerTest.class);
    }

    @Test
    public void testGetLoanById() {
        Loan testLoan = new Loan();
        testLoan.setLoanId(TEST_LOAN_ID);
        testLoan.setApplicant("testUser");
        testLoan.setTerms(1);
        testLoan.setRepayments(new ArrayList<>());
        testLoan.setAmount(100);

        Mockito.when(loanHandler.getLoanById(TEST_LOAN_ID)).thenReturn(testLoan);

        LoanDTO loanDTO = loanController.getLoanById(TEST_LOAN_ID);

        Assertions.assertEquals(TEST_LOAN_ID, loanDTO.getLoanId());
        Assertions.assertEquals(testLoan.getApplicant(), loanDTO.getApplicant());
        Assertions.assertEquals(testLoan.getTerms(), loanDTO.getTerms());
        Assertions.assertEquals(testLoan.getAmount(), loanDTO.getAmount());
        Assertions.assertTrue(loanDTO.getRepayments().isEmpty());
    }
}
