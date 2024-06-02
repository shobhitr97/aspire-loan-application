package org.aspire.data;

import lombok.*;
import org.aspire.constants.MongoDbCollectionConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(MongoDbCollectionConstants.LOAN_COLLECTION)
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Loan extends BaseEntity {

    @Id
    private String loanId;
    private String applicant;
    private int amount;
    private int terms;
    private List<Repayment> repayments;

    public Loan() {
        super();
    }

    public Loan(String applicant, int amount, int terms, List<Repayment> repayments) {
        super();
        this.applicant = applicant;
        this.amount = amount;
        this.repayments = repayments;
        this.terms = terms;
    }
}
