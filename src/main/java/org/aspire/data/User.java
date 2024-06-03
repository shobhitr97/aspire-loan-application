package org.aspire.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.aspire.constants.MongoDbCollectionConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoDbCollectionConstants.USER_COLLECTION)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Id
    private String userId;
    private String username;
    private String fullName;
    private String userType;
    private String encodedCredentials;

    public User() {
        super();
    }

    public User(String username, String fullName, String userType, String encodedCredentials) {
        super();
        this.username = username;
        this.userType = userType;
        this.encodedCredentials = encodedCredentials;
        this.fullName = fullName;
    }

}