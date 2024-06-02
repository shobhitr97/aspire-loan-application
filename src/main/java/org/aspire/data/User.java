package org.aspire.data;

import lombok.Builder;
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
    private String userName;
    private String userType;

    public User() {
        super();
    }

    public User(String userName, String userType) {
        super();
        this.userName = userName;
        this.userType = userType;
    }

}