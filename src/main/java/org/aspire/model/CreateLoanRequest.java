package org.aspire.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@Getter
@EqualsAndHashCode
public class CreateLoanRequest {

    private double amount;
    private int terms;
    private String applicant;
}
