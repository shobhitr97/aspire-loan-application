package org.aspire.data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.aspire.constants.MongoDbCollectionConstants;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(MongoDbCollectionConstants.TRANSACTION_COLLECTION)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Transaction extends BaseEntity {

    private String transactionId;
    private String transactionRef;
    private double amount;
    private String userId;
    private String loanId;
}
