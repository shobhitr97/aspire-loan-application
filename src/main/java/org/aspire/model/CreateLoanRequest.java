package org.aspire.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateLoanRequest {

    private int amount;
    private int terms;
    private String applicant;
}
