package org.aspire.controller;

import org.aspire.TestDataUtils;
import org.aspire.data.Loan;
import org.aspire.dto.ApproveLoanDTO;
import org.aspire.dto.LoanDTO;
import org.aspire.dto.MakeRepaymentDTO;
import org.aspire.exception.ValidationException;
import org.aspire.handler.LoanHandler;
import org.aspire.model.RequestMetadata;
import org.aspire.utils.ObjectConverterUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private LoanHandler loanHandler;

    @Spy
    private RequestMetadata requestMetadata = TestDataUtils.createApplicantRequestMetadata();

    @InjectMocks
    private AdminController adminController;

    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(AdminControllerTest.class);
    }

    @Test
    public void approveLoan_success() {
        Loan testLoan = TestDataUtils.createTestLoan();
        String loanId = testLoan.getLoanId();
        Mockito.when(loanHandler.getLoanById(loanId)).thenReturn(testLoan);
        Mockito.doNothing().when(loanHandler).approveLoan(testLoan, true, TestDataUtils.TEST_APPLICANT_USER_ID);
        adminController.approveLoan(ApproveLoanDTO.builder()
                .approved(true)
                .loanId(loanId)
                .build());
        Mockito.verify(loanHandler, Mockito.times(1)).getLoanById(loanId);
        Mockito.verify(loanHandler, Mockito.times(1)).approveLoan(testLoan, true,
                TestDataUtils.TEST_APPLICANT_USER_ID);
    }

    @ParameterizedTest
    @MethodSource("testApproveLoanArguments")
    public void approveLoan_validationException(Loan loan, ApproveLoanDTO approveLoanDTO, String message) {
        Mockito.lenient().when(loanHandler.getLoanById(TestDataUtils.TEST_LOAN_ID)).thenReturn(loan);
        try {
            adminController.approveLoan(approveLoanDTO);
            Assertions.fail();
        } catch (ValidationException | IllegalArgumentException e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void getLoansByStatus_success() {
        List<Loan> loans = Collections.singletonList(TestDataUtils.createTestLoan());
        Mockito.when(loanHandler.getLoansByStatus("pending")).thenReturn(loans);
        List<LoanDTO> loansByStatus = adminController.getLoansByStatus("pending");
        List<LoanDTO> loanDTOS = loans.stream().map(ObjectConverterUtils::createLoanDTO).toList();
        Assertions.assertEquals(loanDTOS, loansByStatus);
    }

    @ParameterizedTest
    @MethodSource("testGetLoansByStatusArguments")
    public void getLoansByStatus_validationException(String status, String message) {
        try {
            adminController.getLoansByStatus(status);
            Assertions.fail();
        } catch (ValidationException | IllegalArgumentException e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }

    private static Stream<Arguments> testGetLoansByStatusArguments() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(null, "loan status cannot be blank"))
                .add(Arguments.of("  ", "loan status cannot be blank"))
                .add(Arguments.of("random", "loan status is random. Allowed values : [pending,approved,rejected]"))
                .build();
    }

    private static Stream<Arguments> testApproveLoanArguments() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(null, null, "request body cannot be null"))
                .add(Arguments.of(null, ApproveLoanDTO.builder().build(), "loan Id cannot be blank"))
                .add(Arguments.of(
                        null,
                        ApproveLoanDTO.builder().loanId("random").build(),
                        "no loan found for loanId: random"))
                .add(Arguments.of(
                        TestDataUtils.createTestPartiallyPaidLoan(),
                        ApproveLoanDTO.builder().loanId(TestDataUtils.TEST_LOAN_ID).build(),
                        String.format("Loan %s is already approved", TestDataUtils.TEST_LOAN_ID)))
                .build();
    }
}
