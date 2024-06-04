package org.aspire.handler;

import org.aspire.data.User;
import org.aspire.model.RegisterUserRequest;

public interface IUserHandler {

    User createUser(RegisterUserRequest request);

    User getUserByCredentials(String credentials);

    User getUserByUsername(String username);
}
