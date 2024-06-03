package org.aspire.handler;

import org.aspire.data.User;

public interface IUserHandler {

    User createUser(User user);

    User getUserByCredentials(String credentials);

    User getUserByUsername(String username);
}
