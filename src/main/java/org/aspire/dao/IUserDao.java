package org.aspire.dao;

import org.aspire.data.User;

public interface IUserDao {

    User createUser(User user);

    User getUserByCredentials(String credentials);

    User getUserById(String userId);

    User getUserByUsername(String username);
}
