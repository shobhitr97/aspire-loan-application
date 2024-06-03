package org.aspire.dao.repository;

import org.aspire.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    @Query("{ 'encodedCredentials': ?0 }")
    User findByCredentials(String credentials);

    @Query("{ 'username': ?0 }")
    User findByUsername(String username);
}
