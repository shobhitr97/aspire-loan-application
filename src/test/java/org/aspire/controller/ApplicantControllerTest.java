package org.aspire.controller;

import org.aspire.data.Loan;
import org.aspire.dto.LoanDTO;
import org.aspire.handler.ILoanHandler;
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
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class ApplicantControllerTest {

    @Mock
    private ILoanHandler loanHandler;
    @InjectMocks
    private ApplicantController applicantController;

    private static final String TEST_LOAN_ID = "testLoan-1";

    private static final String TEST_USER_ID = "testUser-1";

    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(ApplicantControllerTest.class);
    }

    @Test
    public void testGetLoanById() {
        Loan testLoan = new Loan();
        testLoan.setLoanId(TEST_LOAN_ID);
        testLoan.setApplicant("testUser");
        testLoan.setTerms(1);
        testLoan.setRepayments(new ArrayList<>());
        testLoan.setAmount(100);

        Mockito.when(loanHandler.getLoansForUser(TEST_USER_ID)).thenReturn(Collections.singletonList(testLoan));

        LoanDTO loanDTO = applicantController.getLoans().get(0);

        Assertions.assertEquals(TEST_LOAN_ID, loanDTO.getLoanId());
        Assertions.assertEquals(testLoan.getApplicant(), loanDTO.getApplicant());
        Assertions.assertEquals(testLoan.getTerms(), loanDTO.getTerms());
        Assertions.assertEquals(testLoan.getAmount(), loanDTO.getAmount());
        Assertions.assertTrue(loanDTO.getRepayments().isEmpty());
    }
}
