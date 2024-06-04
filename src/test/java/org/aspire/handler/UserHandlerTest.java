package org.aspire.handler;

import org.aspire.TestDataUtils;
import org.aspire.constants.LoanConstants;
import org.aspire.dao.UserDao;
import org.aspire.data.User;
import org.aspire.model.RegisterUserRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.nio.charset.StandardCharsets;


@ExtendWith(MockitoExtension.class)
public class UserHandlerTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserHandler userHandler;

    @BeforeAll
    public static void initMocks() {
        MockitoAnnotations.openMocks(UserHandlerTest.class);
    }

    @Test
    public void createUser_success() {
        RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .name(TestDataUtils.TEST_ADMIN_FULL_NAME)
                .type(LoanConstants.ADMIN_USER_TYPE)
                .username(TestDataUtils.TEST_ADMIN_USERNAME)
                .password(TestDataUtils.TEST_PASSWORD)
                .build();

        User adminUser = TestDataUtils.createTestAdminUser();

        User user = new User();
        user.setUserType(LoanConstants.ADMIN_USER_TYPE);
        user.setUsername(TestDataUtils.TEST_ADMIN_USERNAME);
        user.setFullName(TestDataUtils.TEST_ADMIN_FULL_NAME);
        user.setEncodedCredentials(HttpHeaders.encodeBasicAuth(TestDataUtils.TEST_ADMIN_USERNAME,
                TestDataUtils.TEST_PASSWORD, StandardCharsets.UTF_8));

        Mockito.doReturn(adminUser).when(userDao).createUser(user);

        User returnedUser = userHandler.createUser(registerUserRequest);

        Assertions.assertEquals(adminUser, returnedUser);
    }

    @Test
    public void getUserByCredential_success() {
        User user = new User();
        Mockito.doReturn(user).when(userDao).getUserByCredentials("randomId");
        User testUser = userHandler.getUserByCredentials("randomId");
        Assertions.assertEquals(user, testUser);
    }

    @Test
    public void getUserByUsername_success() {
        User user = new User();
        Mockito.doReturn(user).when(userDao).getUserByUsername("randomId");
        User testUser = userHandler.getUserByUsername("randomId");
        Assertions.assertEquals(user, testUser);
    }
}
