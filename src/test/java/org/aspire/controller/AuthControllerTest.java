package org.aspire.controller;

import org.aspire.TestDataUtils;
import org.aspire.constants.LoanConstants;
import org.aspire.data.User;
import org.aspire.dto.RegisterUserDTO;
import org.aspire.exception.ValidationException;
import org.aspire.handler.UserHandler;
import org.aspire.model.RegisterUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;


@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserHandler userHandler;

    @InjectMocks
    private AuthController authController;

    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(AuthControllerTest.class);
    }

    @Test
    public void registerUser_success() {
        User adminUser = TestDataUtils.createTestAdminUser();
        RegisterUserDTO registerUserDTO = RegisterUserDTO.builder()
                .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                .username(TestDataUtils.TEST_ADMIN_USERNAME)
                .password(TestDataUtils.TEST_PASSWORD)
                .type(LoanConstants.ADMIN_USER_TYPE)
                .build();


        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .name(registerUserDTO.getName())
                .password(registerUserDTO.getPassword())
                .type(registerUserDTO.getType())
                .username(registerUserDTO.getUsername())
                .build();

        Mockito.when(userHandler.createUser(registerUserRequest)).thenReturn(adminUser);

        authController.registerUser(registerUserDTO);

        Mockito.verify(userHandler, Mockito.times(1)).createUser(registerUserRequest);
    }

    @ParameterizedTest
    @MethodSource("registerUserArguments")
    public void registerUser_validationException(RegisterUserDTO request, String message) {
        Mockito.lenient()
                .when(userHandler.getUserByUsername(TestDataUtils.TEST_ADMIN_USERNAME))
                .thenReturn(TestDataUtils.createTestAdminUser());
        try {
            authController.registerUser(request);
            Assertions.fail();
        } catch (ValidationException e) {
            Assertions.assertEquals(message, e.getMessage());
        }
    }

    private static Stream<Arguments> registerUserArguments() {
        return Stream.<Arguments>builder()
                .add(Arguments.of(null, "request body cannot be null"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "user full name cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name("  ")
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "user full name cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "username cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username("   ")
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "username cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "user password cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password("   ")
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "user password cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .build(), "user type cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type("   ")
                        .build(), "user type cannot be blank"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type("random")
                        .build(), "user type is random. Allowed values : [applicant,admin]"))
                .add(Arguments.of(RegisterUserDTO.builder()
                        .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                        .username(TestDataUtils.TEST_ADMIN_USERNAME)
                        .password(TestDataUtils.TEST_PASSWORD)
                        .type(LoanConstants.ADMIN_USER_TYPE)
                        .build(), "Username not available"))
                .build();
    }
}
