package org.aspire.dao;

import org.aspire.dao.repository.UserRepository;
import org.aspire.data.User;
import org.aspire.model.EntityAction;
import org.aspire.utils.GenericUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDao implements IUserDao {

    private final UserRepository repo;

    @Autowired
    public UserDao(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User createUser(User user) {
        GenericUtils.updateBaseEntity(EntityAction.INSERT, user, null);
        return this.repo.insert(user);
    }

    @Override
    public User getUserByCredentials(String credentials) {
        return this.repo.findByCredentials(credentials);
    }

    @Override
    public User getUserById(String userId) {
        return this.repo.findById(userId).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return this.repo.findByUsername(username);
    }
}
