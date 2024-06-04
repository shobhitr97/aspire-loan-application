package org.aspire.handler;

import org.aspire.dao.IUserDao;
import org.aspire.data.User;
import org.aspire.model.RegisterUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class UserHandler implements IUserHandler {

    private final IUserDao userDao;

    @Autowired
    public UserHandler(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User createUser(RegisterUserRequest request) {
        User user = new User();
        user.setUserType(request.getType());
        user.setUsername(request.getUsername());
        user.setFullName(request.getName());
        user.setEncodedCredentials(HttpHeaders.encodeBasicAuth(request.getUsername(),
                request.getPassword(), StandardCharsets.UTF_8));
        return this.userDao.createUser(user);
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
