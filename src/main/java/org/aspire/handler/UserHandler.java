package org.aspire.handler;

import org.aspire.dao.IUserDao;
import org.aspire.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserHandler implements IUserHandler {

    private final IUserDao userDao;

    @Autowired
    public UserHandler(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(User user) {
        this.userDao.createUser(user);
        return this.userDao.getUserById(user.getUserId());
    }

    @Override
    public User getUserByCredentials(String credentials) {
        return this.userDao.getUserByCredentials(credentials);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }
}
