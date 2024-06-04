package org.aspire.controller;

import org.aspire.TestDataUtils;
import org.aspire.data.Loan;
import org.aspire.dto.CreateLoanRequestDTO;
import org.aspire.dto.LoanDTO;
import org.aspire.dto.MakeRepaymentDTO;
import org.aspire.exception.ValidationException;
import org.aspire.handler.ILoanHandler;
import org.aspire.model.CreateLoanRequest;
import org.aspire.model.LoanRepaymentRequest;
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
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class ApplicantControllerTest {

    @Mock
    private ILoanHandler loanHandler;
    @Spy
    private RequestMetadata requestMetadata = TestDataUtils.createApplicantRequestMetadata();

    @InjectMocks
    private ApplicantController applicantController;


    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(ApplicantControllerTest.class);
    }

    @Test
    public void testGetLoanById_success() {
        Loan testLoan = TestDataUtils.createTestPartiallyPaidLoan();
        Mockito.when(loanHandler.getLoansForUser(TestDataUtils.TEST_APPLICANT_USER_ID)).thenReturn(Collections.singletonList(testLoan));

        LoanDTO loanDTO = applicantController.getLoans().get(0);

        Assertions.assertEquals(TestDataUtils.TEST_LOAN_ID, loanDTO.getLoanId());
        Assertions.assertEquals(testLoan.getApplicant(), loanDTO.getApplicant());
        Assertions.assertEquals(testLoan.getTerms(), loanDTO.getTerms());
        Assertions.assertEquals(testLoan.getAmount(), loanDTO.getAmount());
    }

    @Test
    public void createLoan_success() {
        CreateLoanRequestDTO createLoanRequestDTO = CreateLoanRequestDTO.builder()
                .amount(10000)
                .terms(2)
                .build();

        Mockito.when(loanHandler.createLoan(CreateLoanRequest.builder()
                        .applicant(TestDataUtils.TEST_APPLICANT_USER_ID)
                        .amount(10000)
                        .terms(2)
                .build())).thenReturn(TestDataUtils.createTestLoan());

        LoanDTO loanDTO = applicantController.createLoan(createLoanRequestDTO);
        LoanDTO expectedLoanDTO = ObjectConverterUtils.createLoanDTO(TestDataUtils.createTestLoan());

        Assertions.assertEquals(expectedLoanDTO.getLoanId(), loanDTO.getLoanId());
        Assertions.assertEquals(expectedLoanDTO.getApplicant(), loanDTO.getApplicant());
        Assertions.assertEquals(expectedLoanDTO.getStatus(), loanDTO.getStatus());
        Assertions.assertEquals(expectedLoanDTO.getAmount(), loanDTO.getAmount());
    }

    @ParameterizedTest
    @MethodSource("testCreateLoanArguments")
    public void createLoan_validationException(CreateLoanRequestDTO requestDTO, String message) {
        try {
            applicantController.createLoan(requestDTO);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }

    @Test
    public void makeRepayment_success() {
        MakeRepaymentDTO makeRepaymentDTO = MakeRepaymentDTO.builder()
                .amount(5000)
                .transactionRef("ref#1")
                .build();
        Loan testLoan = TestDataUtils.createTestPartiallyPaidLoan();

        Mockito.when(loanHandler.getLoanById(TestDataUtils.TEST_LOAN_ID)).thenReturn(testLoan);

        LoanRepaymentRequest loanRepaymentRequest = LoanRepaymentRequest.builder()
                .loan(testLoan)
                .amount(5000)
                .transactionRef("ref#1")
                .build();

        Mockito.doNothing().when(loanHandler).makeRepayment(loanRepaymentRequest);

        applicantController.makeRepayment(TestDataUtils.TEST_LOAN_ID, makeRepaymentDTO);

        Mockito.verify(loanHandler, Mockito.times(1)).getLoanById(TestDataUtils.TEST_LOAN_ID);
        Mockito.verify(loanHandler, Mockito.times(1)).makeRepayment(loanRepaymentRequest);
    }

    @ParameterizedTest
    @MethodSource("testMakeRepaymentArguments")
    public void makeRepayment_validationException(String loanId, MakeRepaymentDTO makeRepaymentDTO, Loan testLoan,
                                                  String message) {
        Mockito.lenient().when(loanHandler.getLoanById(TestDataUtils.TEST_LOAN_ID)).thenReturn(testLoan);
        try {
            applicantController.makeRepayment(loanId, makeRepaymentDTO);
            Assertions.fail();
        } catch (ValidationException | IllegalArgumentException e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }

    private static Stream<Arguments> testMakeRepaymentArguments() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(null, null, null, "request body cannot be null"))
                .add(Arguments.of("  ", MakeRepaymentDTO.builder().build(), null, "loan Id cannot be blank"))
                .add(Arguments.of(null, MakeRepaymentDTO.builder().build(), null, "loan Id cannot be blank"))
                .add(Arguments.of("random", MakeRepaymentDTO.builder().build(), null, "transaction ref cannot be blank"))
                .add(Arguments.of(
                        "random",
                        MakeRepaymentDTO.builder().transactionRef("random").build(),
                        null,
                        "no loan found for loanId: random"))
                .add(Arguments.of(
                        TestDataUtils.TEST_LOAN_ID,
                        MakeRepaymentDTO.builder().amount(20000).transactionRef("random").build(),
                        TestDataUtils.createTestLoan(),
                        "repayment amount cannot be less than 0.100000 and greater than 10000.000000. Current value: 20000.000000"))
                .add(Arguments.of(
                        TestDataUtils.TEST_LOAN_ID,
                        MakeRepaymentDTO.builder().amount(5000).transactionRef("random").build(),
                        TestDataUtils.createTestLoan(),
                        String.format("Loan %s is already pending", TestDataUtils.TEST_LOAN_ID)))
                .add(Arguments.of(
                        TestDataUtils.TEST_LOAN_ID,
                        MakeRepaymentDTO.builder().amount(5000).transactionRef("random").build(),
                        TestDataUtils.createTestPartiallyPaidLoan("random"),
                        "only applicant is allowed to make changes to the loan"
                ))
                .build();
    }

    private static Stream<Arguments> testCreateLoanArguments() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(null, "request body cannot be null"))
                .add(Arguments.of(CreateLoanRequestDTO.builder().amount(0).terms(2).build(),
                        "loan amount cannot be less than 1.000000. Current value: 0.000000"))
                .add(Arguments.of(CreateLoanRequestDTO.builder().amount(10).terms(0).build(),
                        "loan terms cannot be less than 1. Current value: 0"))
                .build();
    }
}
